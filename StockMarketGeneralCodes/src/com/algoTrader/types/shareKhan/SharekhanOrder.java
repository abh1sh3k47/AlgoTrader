package com.algoTrader.types.shareKhan;

import com.algoTrader.types.IOrder;
import com.algoTrader.types.StockMarket;

public class SharekhanOrder implements IOrder
{
	private String scrip = "";
	private BuySell	buysell = null;
	private String price = "107.65";
	private int quantity = 1;
	private String ordertype = "Limit";
	private String validity	= "IOC";
	private StockMarket exchangecode = StockMarket.BSE;

	private String disclosedorderqty = "0";
	private String tprice = "0.0";
	private String stoplossvalidity	= "GFD";
	private String bktlossstoploss = "0";
	private String stoplossvaliditydate	= "20/07/2015";
	private String bktlossprice	= "0";
	private String dpclient	= "21657328";

	//Not important args
	private String comment	= "ajay";
	private String execute	= "neworder";
	private String requestcode	= "NEW";
	private String afterhour = "N";
	private String minlot = "0";

	public static void main(String[] args) 
	{
		BuySell b = BuySell.SHORTSELL;
		System.out.println(b);
	}
	
	public static enum BuySell 
	{ 
		BUY("B"),SELL("S"),SHORTSELL("SS");
		
		private String stringRepr = "";
		
		BuySell(String s)
		{
			stringRepr = s;
		}
		
		@Override
		public String toString() 
		{
			return stringRepr;
		}
	}
}
