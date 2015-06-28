package com.algoTrader.jobRunners;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.algoTrader.commons.CommonUtils;
import com.algoTrader.commons.Settings;
import com.algoTrader.parsingUtils.ShareKhanParserUtil;
import com.algoTrader.types.Bet;
import com.algoTrader.types.BidAskLists;
import com.algoTrader.types.shareKhan.ShareKhanBidAskLists;
import com.algoTrader.workers.ShareKhanWorker;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class BidAskDownloaderPocJob 
{
	private static Logger logger= Logger.getLogger(BidAskDownloaderPocJob.class);
	private static final ShareKhanWorker worker;

	static
	{
		worker = ShareKhanWorker.getInstance();
	}

	public static void main(String[] args) throws Exception 
	{
		logger.debug("Started executing BidAskDownloaderPocJob");

		if(worker == null)
		{
			logger.error("Worker object is null exiting.");
			return;
		}

		worker.login();
		for(String s : args)
		{
			Thread t = new BidAskDownloaderWorkerThread(s.trim());
			t.start();
		}
	}

	public static synchronized void printBidAskListToConsole(ShareKhanBidAskLists skBaList)
	{
		BidAskLists bseLists = skBaList.getBseList();
		Iterator<Bet> it = bseLists.getBidsIterator();
		System.out.println("BID-ASK List for scrip - "+skBaList.getScrip());
		System.out.println("BSE TimeStamp - "+skBaList.getBseTimeStamp());
		System.out.println("BSE Bids");
		while(it.hasNext())
		{
			Bet bet = it.next();
			System.out.println(bet.getOrderCount()+" "+bet.getQuantity()+" "+bet.getPrice());
		}

		it = bseLists.getAsksIterator();
		System.out.println("BSE Asks");
		while(it.hasNext())
		{
			Bet bet = it.next();
			System.out.println(bet.getOrderCount()+" "+bet.getQuantity()+" "+bet.getPrice());
		}

		System.out.println("NSE TimeStamp - "+skBaList.getNseTimeStamp());
		BidAskLists nseLists = skBaList.getNseList();
		it = nseLists.getBidsIterator();
		System.out.println("NSE Bids");
		while(it.hasNext())
		{
			Bet bet = it.next();
			System.out.println(bet.getOrderCount()+" "+bet.getQuantity()+" "+bet.getPrice());
		}

		it = nseLists.getAsksIterator();
		System.out.println("NSE Asks");
		while(it.hasNext())
		{
			Bet bet = it.next();
			System.out.println(bet.getOrderCount()+" "+bet.getQuantity()+" "+bet.getPrice());
		}
	}

	public static class BidAskDownloaderWorkerThread extends Thread
	{
		String scrip;
		BidAskDownloaderWorkerThread(String scrip)
		{
			this.scrip = scrip;
		}

		public void run()
		{
			logger.debug("Executing BidAskDownloaderPocJob for scrip-"+scrip);

			while(true)
			{
				String dom = "";
				try 
				{
					dom = worker.getBidAskListDom(scrip);
					ShareKhanBidAskLists skBaList = ShareKhanParserUtil.getBidAskLists(dom);
					skBaList.setScrip(scrip);
					printBidAskListToConsole(skBaList);
					Thread.sleep(10000);
				} 
				catch (Exception e) 
				{
					logger.error(e);
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			//logger.debug("FINISHED Executing BidAskDownloaderPocJob for scrip-"+scrip);
		}
	}
}
