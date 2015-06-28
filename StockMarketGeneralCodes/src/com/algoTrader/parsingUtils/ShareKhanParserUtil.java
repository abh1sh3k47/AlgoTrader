package com.algoTrader.parsingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.algoTrader.types.Bet;
import com.algoTrader.types.Bet.BetType;
import com.algoTrader.types.BidAskLists;
import com.algoTrader.types.shareKhan.ShareKhanBidAskLists;
import com.algoTrader.workers.ShareKhanWorker;

public class ShareKhanParserUtil extends HtmlParsingUtils 
{
	private static Logger logger = Logger.getLogger(ShareKhanParserUtil.class);

	public static ShareKhanBidAskLists getBidAskLists(InputStream is)
			throws IOException 
			{
		return getBidAskLists(getStringFromIs(is));
	}

	public static ShareKhanBidAskLists getBidAskLists(String dom) 
	{
		logger.debug("CALL getBidAskLists");
		ShareKhanBidAskLists shareKhanBidAskList = new ShareKhanBidAskLists();
		List<String> t1s = getTagBodiesFromAString(dom, "t1");

		for (int i = 0; i < t1s.size(); i++) 
		{
			// System.out.println(t1s.get(i));
			Map<String, String> tagValueMap = getAllTagValues(t1s.get(i));

			/*
			 * for(Map.Entry<String, String> e:tagValueMap.entrySet()) {
			 * System.out.println(e.getKey()+" "+e.getValue()); }
			 */
			
			String market = tagValueMap.get("t15").trim();
			String scrip = tagValueMap.get("t2").trim();
			String timeStamp = tagValueMap.get("t14").trim();
			
			Double ltp = Double.parseDouble(tagValueMap.get("t3").trim());
			Integer ltq = Integer.parseInt(tagValueMap.get("t6").trim());
			
			BidAskLists bidAskList = new BidAskLists();
			
			for (int j = 0; j < 5; j++) 
			{
				String orderCountS = tagValueMap.get("t2"+(j+5));
				String quantityS = tagValueMap.get("t3"+j);
				String priceS = tagValueMap.get("t3"+(j+5));

				Integer orderCount = Integer.parseInt(orderCountS.trim());
				Integer quantity = Integer.parseInt(quantityS.trim());
				Double price = Double.parseDouble(priceS.trim());

				Bet bid = new Bet(BetType.BID, orderCount, quantity, price);
				
				orderCountS = tagValueMap.get("t4"+j);
				quantityS = tagValueMap.get("t4"+(j+5));
				priceS = tagValueMap.get("t5"+j);
				
				orderCount = Integer.parseInt(orderCountS.trim());
				quantity = Integer.parseInt(quantityS.trim());
				price = Double.parseDouble(priceS.trim());
				
				Bet ask = new Bet(BetType.ASK, orderCount, quantity, price);
				
				bidAskList.addBid(bid);
				bidAskList.addAsk(ask);
			}

			if(market.equals("BSE"))
			{
				shareKhanBidAskList.setBseList(bidAskList);
				shareKhanBidAskList.setBseTimeStamp(timeStamp);
				shareKhanBidAskList.setBseLtp(ltp);
				shareKhanBidAskList.setBseLtq(ltq);
				
			}
			else if(market.equals("NSE"))
			{
				shareKhanBidAskList.setNseList(bidAskList);
				shareKhanBidAskList.setNseTimeStamp(timeStamp);
				shareKhanBidAskList.setNseLtp(ltp);
				shareKhanBidAskList.setNseLtq(ltq);
			}
		}

		logger.debug("RETURN getBidAskLists");
		return shareKhanBidAskList;
	}

	public static void printShareKhanBidAskLists(ShareKhanBidAskLists bidAskLists) throws Exception 
	{
		BidAskLists bseLists = bidAskLists.getBseList();
		Iterator<Bet> it = bseLists.getBidsIterator();
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
		
		BidAskLists nseLists = bidAskLists.getNseList();
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
