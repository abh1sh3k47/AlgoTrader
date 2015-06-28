package com.algoTrader.jobRunners;

import static com.algoTrader.commons.GlobalConstants.NO_RECORDS_AVAILABLE_REGEX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.algoTrader.commons.CommonUtils;
import com.algoTrader.commons.Settings;
import com.algoTrader.types.shareKhan.ShareKhanOrderReportEntry;
import com.algoTrader.workers.ShareKhanWorker;
import com.algoTrader.workers.SharekhanOrderReportUpdater;

public class SharekhanOrderReportFileUpdaterJob 
{
	private static Logger logger= Logger.getLogger(SharekhanOrderReportFileUpdaterJob.class);
	private static Settings settings;
	private static Pattern noRecordsPattern = Pattern.compile(NO_RECORDS_AVAILABLE_REGEX);
	
	static
	{
			settings = Settings.getSettings();
		
	}

	public static void main(String[] args) throws Exception 
	{
		logger.debug("Started executing SharekhanReportUpdaterJob");
		ShareKhanWorker worker = ShareKhanWorker.getInstance();

		List<ShareKhanOrderReportEntry> reportEntries = new LinkedList<ShareKhanOrderReportEntry>();
		String reportDom = worker.getOrderReportDom();
		
		if(reportDom == null)
		{
			logger.error("Couldnt download the report.");
			return;
		}
		
		List<String> trs;
		try
		{
			String tBody = getTBody(reportDom);
			trs = getTrs(tBody);
		}
		catch(Exception e)
		{
			System.err.println("Something went wrong while parsing the report. please check the logs");
			logger.error(e);
			return;
		}

		if(trs.size()==0) 
		{
			System.out.println("Nothing to write!! exiting");
			logger.debug("There was nothing to write. returning");
			return;
		}
		int count = 0;
		for(String tr:trs)
		{
			ShareKhanOrderReportEntry entry = new ShareKhanOrderReportEntry();
			List<String> tds = getParsedTds(tr);
			while(tds.size()>17)
			{
				tds.remove(0);
			}
			for(int i=0;i<tds.size();i++)
			{
				entry.set(i,tds.get(i));
			}
			reportEntries.add(entry);
			count++;
		}

		String reportFilePath = settings.getShareKhanReportFilePath();
		SharekhanOrderReportUpdater reportUpdater = new SharekhanOrderReportUpdater(reportFilePath);
		reportUpdater.appendToOrderReportFile(reportEntries);
		System.out.println("Wrote "+count+" entries to file: '"+settings.getShareKhanReportFilePath()+"'");
		logger.debug("Wrote "+count+" entries to file: '"+settings.getShareKhanReportFilePath()+"'");

		logger.info("Creating report details files in Path:"+settings.getShareKhanOrderDetailsFolderPath());
		for(ShareKhanOrderReportEntry e : reportEntries)
		{
			String orderId = e.getOrderID();
			String orderDetailsDom = worker.getOrderDetailsDom(orderId);
			writeOrderDetailsToAFile(orderId, orderDetailsDom);
		}
		System.out.println("Finished creating report details files.");
		logger.debug("Finished executing SharekhanReportUpdaterJob");
	}

	public static void writeOrderDetailsToAFile(String orderId, String orderDetailsDom) throws FileNotFoundException
	{
		String folderPath = settings.getShareKhanOrderDetailsFolderPath();
		File folder = new File(folderPath);
		if(!folder.isDirectory())
		{
			logger.error("The folder path specified is either not a folder or it doesn't exist. \n Path - "+folderPath);
			return;
		}

		if(!folderPath.endsWith("/")&&!folderPath.endsWith("\\"))
		{
			folderPath += "/";
		}

		String filePath = folderPath + orderId+".html";
		File orderDetailFile = new File(filePath);

		if(orderDetailFile.exists())
		{
			logger.warn("Tried to create a file :"+filePath+" but the file already exists.");
			return;
		}

		PrintWriter pw = null;
		try
		{
			pw = new PrintWriter(orderDetailFile);
			pw.println(orderDetailsDom);
		}
		catch(IOException ex)
		{
			logger.error(ex);
		}
		finally
		{
			if(pw!=null)
			{
				pw.flush();
				pw.close();
			}
		}

		logger.debug("created a order details file - Path- "+filePath);
	}

	public static String getTBody(String reportDom)
	{
		if(reportDom == null)
		{
			logger.warn("getTBody(String) called with null");
			return null;
		}
		int tStart = reportDom.indexOf("<tbody>");
		if(tStart<0) return "";
		tStart+="<tbody>".length();
		int tEnd = reportDom.indexOf("</tbody>" , tStart);
		String tBody = reportDom.substring(tStart,tEnd);
		return tBody;
	}

	public static List<String> getTrs(String tBody)
	{
		if(tBody==null)
		{
			logger.warn("getTrs(String) called with null");
		}
		int tStart = 0;
		List<String> trs = new LinkedList<String>();
		while(true)
		{
			int trIndex = tBody.indexOf("<tr",tStart);
			int trEndIndex = 0;
			if(trIndex>=0)
			{
				trIndex = tBody.indexOf(">",trIndex)+1;
				trEndIndex = tBody.indexOf("</tr>",trIndex);
				String tr = tBody.substring(trIndex,trEndIndex);
				if(noRecordsPattern.matcher(tr).find())
				{
					break;
				}
				trs.add(tr);
			}
			else
			{
				break;
			}
			tStart = trEndIndex;
		}
		return trs;
	}

	public static List<String> getParsedTds(String tr)
	{
		List<String> tds = getTds(tr);
		List<String> newTds = new LinkedList<String>();
		for(String s:tds)
		{
			int aIndex = s.indexOf("<a");
			if(aIndex>=0)
			{
				int aEndIndex = s.indexOf(">",aIndex);
				int aTagEndIndex = s.indexOf("</a>",aEndIndex);
				s = s.substring(aEndIndex+1,aTagEndIndex);
			}
			newTds.add(s.trim().replace("&nbsp;", " "));
		}
		return newTds;
	}

	public static List<String> getTds(String tr)
	{
		int tStart = 0;
		List<String> tds = new LinkedList<String>();
		while(true)
		{
			int trIndex = tr.indexOf("<td",tStart);
			int trEndIndex = 0;
			if(trIndex>=0)
			{
				trIndex = tr.indexOf(">",trIndex)+1;
				trEndIndex = tr.indexOf("</td>",trIndex);
				String td = tr.substring(trIndex,trEndIndex);
				tds.add(td);
			}
			else
			{
				break;
			}
			tStart = trEndIndex;
		}
		return tds;
	}
}
