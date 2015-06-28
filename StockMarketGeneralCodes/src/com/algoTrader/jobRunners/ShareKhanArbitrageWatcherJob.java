package com.algoTrader.jobRunners;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import com.algoTrader.commons.CommonUtils;
import com.algoTrader.commons.Settings;
import com.algoTrader.types.shareKhan.ShareKhanArbitrageWatcher;
import com.algoTrader.workers.ShareKhanWorker;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class ShareKhanArbitrageWatcherJob 
{
	private static Logger logger= Logger.getLogger(SharekhanOrderReportFileUpdaterJob.class);
	private static Settings settings;

	static
	{
		settings = Settings.getSettings();
	}

	public static void main(String[] args) throws Exception 
	{
		logger.debug("Started executing ShareKhanArbitrageWatcherJob");
		ShareKhanWorker worker = ShareKhanWorker.getInstance();

		if(args.length<1)
		{
			logger.error("Insufficient arguements provided for ShareKhanArbitrageWatcherJob");
			System.err.println("Insufficient arguements provided for ShareKhanArbitrageWatcherJob");
			System.out.println("Usage : ShareKhanArbitrageWatcherJob <SLEEP DURATION IN MILIS> <SCRIP1> <SCRIP2> ...");
			System.out.println("eg : ShareKhanArbitrageWatcherJob 10000 WIPRO ONGC");
			return;
		}
		int sleepDuration = Integer.parseInt(args[0]);

		String dataFolder = settings.getDataFolder();
		if(!dataFolder.endsWith("/")&&!dataFolder.endsWith("\\"))
		{
			dataFolder += "/";
		}
		File dataFolderFile = new File(dataFolder);
		if(!dataFolderFile.exists())
		{
			logger.error("Provided data folder path doesnt exist - "+dataFolder);
			System.out.println("Provided data folder path doesnt exist! exiting.");
			return;
		}

		for(int i=1;i<args.length;i++)
		{
			String scrip = args[i].trim();
			File file = new File(dataFolder+scrip+".txt");
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			Thread t = new ShareKhanArbitrageWatcher(worker,args[i].trim(),sleepDuration,fileOutputStream);
			t.start();
		}
	}

}
