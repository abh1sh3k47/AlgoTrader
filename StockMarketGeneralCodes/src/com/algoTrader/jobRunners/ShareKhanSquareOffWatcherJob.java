package com.algoTrader.jobRunners;

import org.apache.log4j.Logger;

import com.algoTrader.types.shareKhan.ShareKhanSquareOffWatcher;
import com.algoTrader.workers.ShareKhanWorker;

public class ShareKhanSquareOffWatcherJob 
{

	private static Logger logger= Logger.getLogger(ShareKhanSquareOffWatcherJob.class);
	
	public static void main(String[] args) 
	{
		if(args.length<1)
		{
			logger.error("Insufficient arguements provided for ShareKhanSquareOffWatcherJob");
			System.err.println("Insufficient arguements provided for ShareKhanSquareOffWatcherJob");
			System.out.println("Usage : ShareKhanSquareOffWatcherJob <SLEEP DURATION IN MILIS> <SCRIP1>:<HOLD_PRICE1>:<HOLD_VOLUME1> <SCRIP2>:<HOLD_PRICE2>:<HOLD_VOLUME1> ...");
			System.out.println("eg : ShareKhanSquareOffWatcherJob 10000 WIPRO:12.5:15 ONGC:101.4:100");
			return;
		}
		
		int sleepDuration = Integer.parseInt(args[0]);
		
		for(int i=1;i<args.length;i++)
		{
			String scripPrice = args[i].trim();
			String [] vals = scripPrice.split(":");
			String scrip = vals[0];
			String holdPriceString = vals[1];
			String holdVolumeString = vals[2];
			
			double holdPrice = Double.parseDouble(holdPriceString);
			int holdVolume = Integer.parseInt(holdVolumeString);
			
			ShareKhanWorker worker = ShareKhanWorker.getInstance();
			ShareKhanSquareOffWatcher watcher = new ShareKhanSquareOffWatcher(worker,scrip,holdVolume,holdPrice,sleepDuration);
			watcher.start();
		}
	}

}
