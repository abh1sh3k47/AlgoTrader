package com.algoTrader.workers;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.algoTrader.commons.CommonUtils;
import com.algoTrader.commons.Settings;
import com.algoTrader.jobRunners.BidAskDownloaderPocJob;
import com.algoTrader.parsingUtils.ShareKhanParserUtil;
import com.algoTrader.types.shareKhan.ShareKhanBidAskLists;
import com.algoTrader.types.shareKhan.ShareKhanParserFacade;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class SharekhanBidAskDownloaderWorker implements Callable<ShareKhanBidAskLists>
{
	private static Logger logger= Logger.getLogger(SharekhanBidAskDownloaderWorker.class);
	private static final ShareKhanWorker worker;
	private static final ShareKhanParserFacade shareKahnParserFacade;

	static
	{
		worker = ShareKhanWorker.getInstance();
		shareKahnParserFacade = new ShareKhanParserFacade(worker);
	}

	private String scrip;

	public SharekhanBidAskDownloaderWorker(String scrip)
	{
		this.scrip = scrip;
	}

	@Override
	public ShareKhanBidAskLists call() throws Exception 
	{
		logger.debug("SharekhanBidAskDownloaderWorker call - "+scrip);
		String dom = "";
		try 
		{
			ShareKhanBidAskLists skBaList = shareKahnParserFacade.getBidAskListsForScrip(scrip);
			skBaList.setScrip(scrip);
			return skBaList;
		} 
		catch (Exception e) 
		{
			logger.error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally
		{
			logger.debug("SharekhanBidAskDownloaderWorker call RETURN - "+scrip);
		}
	}

	public String getScrip() {
		return scrip;
	}

	public void setScrip(String scrip) {
		this.scrip = scrip;
	}
}
