package com.algoTrader.types.shareKhan;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.algoTrader.parsingUtils.ShareKhanParserUtil;
import com.algoTrader.workers.ShareKhanWorker;

public class ShareKhanParserFacade 
{
	private static Logger logger= Logger.getLogger(ShareKhanParserFacade.class);
	
	ShareKhanWorker worker;
	
	public ShareKhanParserFacade(ShareKhanWorker worker)
	{
		this.worker = worker;
	}
	
	public ShareKhanBidAskLists getBidAskListsForScrip(String scrip) throws Exception 
	{
		logger.debug("CALL getBidAskListsForScrip( scrip = "+scrip+")");
		String dom = worker.getBidAskListDom(scrip);
		ShareKhanBidAskLists bidAskLists = ShareKhanParserUtil.getBidAskLists(dom);
		
		logger.debug("RETURN getBidAskListsForScrip "+scrip);
		return bidAskLists;
	}
	
	public static void main(String [] args) throws Exception
	{
		ShareKhanParserFacade facade = new ShareKhanParserFacade(ShareKhanWorker.getInstance());
		//ShareKhanBidAskLists bidAskList = facade.getBidAskListsForScrip("ONGC");
		//System.out.println(bidAskList.getNseTimeStamp() + bidAskList.getBseTimeStamp());
		
		String dom = facade.worker.getBidAskListDom("ONGC");
		printDomToFile(dom, "c:\\ONGC_BID_ASK_DOM.txt");
		
		ShareKhanBidAskLists bidAskList = ShareKhanParserUtil.getBidAskLists(dom);
	}
	
	public static void printDomToFile(String dom,String file) throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(file);
		pw.print(dom);
		pw.flush();
		pw.close();
	}
}
