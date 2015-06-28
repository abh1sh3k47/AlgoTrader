package com.algoTrader.jobRunners;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.algoTrader.types.Bet;
import com.algoTrader.types.BidAskLists;
import com.algoTrader.types.shareKhan.ShareKhanBidAskLists;
import com.algoTrader.workers.SharekhanBidAskDownloaderWorker;

public class GetQuoteJob 
{
	private static Logger logger = Logger.getLogger(GetQuoteJob.class);
	private static ExecutorService executor;
	private static LinkedHashMap<Future<ShareKhanBidAskLists>,Boolean> runingThreadsMap;

	static 
	{
		executor = new ThreadPoolExecutor(5, 10, Long.MAX_VALUE,
				TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(100));
		runingThreadsMap = new LinkedHashMap<Future<ShareKhanBidAskLists>,Boolean>();
	}

	public static void main(String[] args) throws Exception 
	{
		logger.debug("Started executing GetQuoteJob");

		if (args.length < 1) {
			logger.error("Insufficient arguements provided for GetQuoteJob");
			System.err.println("Insufficient arguements provided for GetQuoteJob");
			System.out.println("Usage : GetQuoteJob <SCRIP1> <SCRIP2> ...");
			System.out.println("eg : GetQuoteJob WIPRO ONGC");
			return;
		}

		for (int i = 0; i < args.length; i++) 
		{
			String scrip = args[i].trim();
			SharekhanBidAskDownloaderWorker worker = new SharekhanBidAskDownloaderWorker(scrip);
			Future<ShareKhanBidAskLists> future = executor.submit(worker);
			runingThreadsMap.put(future,Boolean.FALSE);
		}

		boolean done = false;

		while (!done) 
		{
			done = true; 
			Iterator<Entry<Future<ShareKhanBidAskLists>,Boolean>> iterator = runingThreadsMap.entrySet().iterator();
			while(iterator.hasNext())
			{
				Entry<Future<ShareKhanBidAskLists>,Boolean> baListEntry = iterator.next();
				if(baListEntry.getKey().isDone()&&!baListEntry.getValue())
				{
					printBidAskListToConsole(baListEntry.getKey().get());
					System.out.println();
					System.out.println();
					runingThreadsMap.put(baListEntry.getKey(),Boolean.TRUE);
				}
				done = done && baListEntry.getKey().isDone();
			}
			Thread.sleep(100);
		}
		executor.shutdownNow();
	}

	public static synchronized void printBidAskListToConsole(ShareKhanBidAskLists skBaList)
	{
		System.out.println("BSE Quote - "+skBaList.getBseLtp()+ " Volume - "+skBaList.getBseLtq());
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

		System.out.println("NSE Quote - "+skBaList.getNseLtp()+ " Volume - "+skBaList.getNseLtq());
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

}
