package com.algoTrader.workers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import com.algoTrader.brokerWebSessions.ShareKhanWebBrokerSession;
import com.algoTrader.commons.Settings;
import com.algoTrader.types.shareKhan.ShareKhanOrderReportEntry;

import static com.algoTrader.commons.Settings.*;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class SharekhanOrderReportUpdater 
{
	final String orderReportFilePath;
	final PrintWriter pw;
	public static final String[] headers = {"Order ID",
											"Exch",
											"ScripCode",
											"Seg.",
											"B/S",
											"Order DateTime",
											"Order Status",
											"Order Qty",
											"Dis. Qty",
											"Order Price",
											"Exec Qty",
											"Exec Price",
											"LTP",
											"Trig Price",
											"Validity",
											"Validity Date",
											"Rms Code"};
			
	public SharekhanOrderReportUpdater(String orderReportFilePath) throws IOException
	{
		this.orderReportFilePath = orderReportFilePath;
		File reportFile = new File(this.orderReportFilePath);
		boolean fileExists = reportFile.exists();
		pw = new PrintWriter(new FileWriter(reportFile,true));
		if(!fileExists)
		{
			writeHeaders();
		}
	}
	
	public void appendToOrderReportFile(ShareKhanOrderReportEntry reportEntry)
	{
		String csvString = reportEntry.toCsvString();
		pw.println(csvString);
		pw.flush();
	}
	
	public void appendToOrderReportFile(Collection<ShareKhanOrderReportEntry> reportEntries)
	{
		for(ShareKhanOrderReportEntry re:reportEntries)
		{
			String csvString = re.toCsvString();
			pw.println(csvString);
			pw.flush();
		}
	}
	
	public void writeHeaders()
	{
		String headerCsv = "";
		for(int i=0;i<headers.length;i++)
		{
			headerCsv += ((i!=0)?",":"")+headers[i];
		}
		pw.println(headerCsv);
		pw.flush();
	}
}
