package com.algoTrader.types.shareKhan;

import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.algoTrader.types.Bet;
import com.algoTrader.types.BidAskLists;
import com.algoTrader.workers.ShareKhanWorker;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class ShareKhanArbitrageWatcher extends Thread 
{
	private static Logger logger= Logger.getLogger(ShareKhanArbitrageWatcher.class);
	private String scrip;
	private ShareKhanParserFacade shareKahnParserFacade;
	private long sleepIntervalDuration;
	private static Object consoleLock = new Object();
	private PrintStream printStream;

	public ShareKhanArbitrageWatcher(ShareKhanWorker worker, String scrip, int sleepIntervalDuration, OutputStream outputStream)
	{
		this.scrip = scrip;
		shareKahnParserFacade = new ShareKhanParserFacade(worker);
		this.sleepIntervalDuration = sleepIntervalDuration;
		this.printStream = new PrintStream(outputStream);
	}

	public void run()
	{
		logger.debug("Executing ShareKhan Arbitrage Watcher Job");
		while(true)
		{
			try 
			{
				ShareKhanBidAskLists bidAskLists = shareKahnParserFacade.getBidAskListsForScrip(scrip);
				BidAskLists bseLists = bidAskLists.getBseList();
				BidAskLists nseLists = bidAskLists.getNseList();

				Bet lowestNseAsk = nseLists.getAsksIterator().next();
				Bet highestBseBid = bseLists.getBidsIterator().next();

				Bet lowestBseAsk = bseLists.getAsksIterator().next();
				Bet highestNseBid = nseLists.getBidsIterator().next();

				synchronized(consoleLock)
				{
					System.out.println("Scrip - "+scrip);
					System.out.println("Nse Ask - "+lowestNseAsk.getPrice());
					System.out.println("Bse Bid - "+highestBseBid.getPrice());
					System.out.println("Bse Ask - "+lowestBseAsk.getPrice());
					System.out.println("Nse Bid - "+highestNseBid.getPrice());
				}

				double nseBuyBseSellProfit =  highestBseBid.getPrice() - lowestNseAsk.getPrice();
				double bseBuyNseSellProfit = highestNseBid.getPrice() - lowestBseAsk.getPrice();
				if(nseBuyBseSellProfit>0d || bseBuyNseSellProfit>0d)
				{
					logger.info("Spotte arbitrage for scrip-"+scrip);
					logger.info("NSE Ask-"+lowestNseAsk.getPrice());
					logger.info("BSE Bid-"+highestBseBid.getPrice());
					logger.info("BSE Ask-"+lowestBseAsk.getPrice());
					logger.info("NSE Bid-"+highestNseBid.getPrice());
					System.out.println("!!SPOTTED ARBITRAGE OPPORTUNITY!!");

					printStream.println("!!SPOTTED ARBITRAGE OPPORTUNITY!!");
					printStream.println("SCrip - "+scrip);
					printStream.println("Nse Timestamp - "+bidAskLists.getNseTimeStamp());
					printStream.println("Bse Timestamp - "+bidAskLists.getBseTimeStamp());
					printStream.println("lowest NSE Ask-"+lowestNseAsk.getPrice());
					printStream.println("highest BSE Bid-"+highestBseBid.getPrice());
					printStream.println("lowest BSE Ask-"+lowestBseAsk.getPrice());
					printStream.println("highest NSE Bid-"+highestNseBid.getPrice());
					printStream.println("NSE Buy BSE Sell Profit - "+nseBuyBseSellProfit);
					printStream.println("BSE Buy NSE Sell Profit - "+bseBuyNseSellProfit);
					printStream.println();
				}

				Thread.sleep(sleepIntervalDuration);
			} 
			catch (Exception e) 
			{
				logger.error(e);
				throw new RuntimeException(e);
			}
		}
	}

}
