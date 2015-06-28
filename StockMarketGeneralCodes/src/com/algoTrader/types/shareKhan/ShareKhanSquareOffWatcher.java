package com.algoTrader.types.shareKhan;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.algoTrader.parsingUtils.ShareKhanParserUtil;
import com.algoTrader.types.Bet;
import com.algoTrader.types.Bet.BetType;
import com.algoTrader.types.BidAskLists;
import com.algoTrader.workers.ShareKhanWorker;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class ShareKhanSquareOffWatcher extends Thread 
{
	private static Logger logger= Logger.getLogger(ShareKhanSquareOffWatcher.class);

	private ShareKhanParserFacade shareKahnParserFacade;

	private String scrip;
	private int holdVolume;
	private double holdPrice;
	private long sleepDuration;

	static final Object consoleLock = new Object();

	public ShareKhanSquareOffWatcher(ShareKhanWorker worker,String scrip, int holdVolume, double holdPrice)
	{
		this(worker,scrip,holdVolume,holdPrice,0);
	}

	public ShareKhanSquareOffWatcher(ShareKhanWorker worker,String scrip, int holdVolume, double holdPrice, long sleepDuration)
	{
		shareKahnParserFacade = new ShareKhanParserFacade(worker);
		this.scrip = scrip;
		this.holdVolume = holdVolume;
		this.holdPrice = holdPrice;
		this.sleepDuration = sleepDuration;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			try 
			{
				ShareKhanBidAskLists bidAskList = shareKahnParserFacade.getBidAskListsForScrip(scrip);

				BidAskLists bseBidAskList = bidAskList.getBseList();
				BidAskLists nseBidAskList = bidAskList.getBseList();

				synchronized(consoleLock)
				{
					System.out.println("-------------------------------------------------");
					System.out.println("Scrip - "+scrip+"  HoldPrice - "+holdPrice+"   HoldVolume - "+holdVolume);
					System.out.println("-------------------------------------------------");
					System.out.println("BSE -"); 
					System.out.println("Timestamp - "+bidAskList.getBseTimeStamp());
					System.out.println("Last Traded Price:Quantity - "+bidAskList.getBseLtp()+":"+bidAskList.getBseLtq());
					Bet executableBseBet = getExecutableBet(bseBidAskList);
					printExecutaleBet(executableBseBet);

					System.out.println("NSE - ");
					System.out.println("Timestamp - "+bidAskList.getBseTimeStamp());
					System.out.println("Last Traded Price:Quantity - "+bidAskList.getNseLtp()+":"+bidAskList.getNseLtq());
					Bet executableNseBet = getExecutableBet(nseBidAskList);
					printExecutaleBet(executableNseBet);

					System.out.println("-------------------------------------------------");
					System.out.println();
				}
				if(sleepDuration > 0) Thread.sleep(sleepDuration);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	private Bet getExecutableBet(BidAskLists bidAskList)
	{
		double lastCountedBidPrice = 0d;
		int countedVolume = 0;
		double countedPrice = 0d;

		Iterator<Bet> bidsIterator = bidAskList.getBidsIterator();
		while(bidsIterator.hasNext())
		{
			Bet bid = bidsIterator.next();
			int bidVolume = bid.getQuantity();

			if(bidVolume <= 0) continue;

			double bidPrice = bid.getPrice();
			int remainingBidVolume = holdVolume - countedVolume;
			int bidSpan = remainingBidVolume - bidVolume;

			if (bidSpan <= 0)
			{
				lastCountedBidPrice = bidPrice;
				countedVolume = holdVolume;
				countedPrice += bidPrice * remainingBidVolume;
				break;
			}
			else
			{
				lastCountedBidPrice = bid.getPrice();
				countedVolume += bidVolume;
				countedPrice += bidPrice * bidVolume;
			}
		}


		Bet executableBet = null;
		if(countedVolume >0 )
		{
			double averagePrice = new Double(countedPrice) / countedVolume;
			executableBet = new Bet(BetType.ASK,1,countedVolume,lastCountedBidPrice,averagePrice);
		}
		return executableBet;
	}

	private void printExecutaleBet(Bet executableBet)
	{
		if(executableBet == null )
		{
			System.out.println("Invalid/No bids.");
			return;
		}

		synchronized(consoleLock)
		{
			System.out.println("Immediate profit -: "+(executableBet.getAveragePrice()*executableBet.getQuantity()-holdPrice*holdVolume));
			System.out.println("Executable average price - "+executableBet.getAveragePrice());
			System.out.println("Executable quantity - "+executableBet.getQuantity());
			System.out.println("Limit price - "+executableBet.getPrice());
			System.out.println("Total order value - "+executableBet.getAveragePrice()*executableBet.getQuantity());
		}
	}

	public static void main(String... args)
	{
		ShareKhanSquareOffWatcher watcher = new ShareKhanSquareOffWatcher(ShareKhanWorker.getInstance(), "ONGC", 100, 302.10d, 5000);
		/*BidAskLists l = new BidAskLists();
		l.addBid(new Bet(BetType.BID, 2, 50, 10));
		l.addBid(new Bet(BetType.BID, 2, 40, 9));
		l.addBid(new Bet(BetType.BID, 0, 15, 8));
		l.addBid(new Bet(BetType.BID, 0, 90, 7));
		l.addBid(new Bet(BetType.BID, 0, 0, 0d));*/

		watcher.start();


		/*Bet executableBet = watcher.getExecutableBet(null);
		watcher.printExecutaleBet(executableBet);


		ShareKhanWorker worker = ShareKhanWorker.getInstance();
		ShareKhanSquareOffWatcher watcher = new ShareKhanSquareOffWatcher(worker,"RELIANCE",100,0);
		watcher.start();*/
	}
}
