package com.algoTrader.types.shareKhan;

import java.util.Date;

import com.algoTrader.types.IOrder;
import com.algoTrader.types.StockMarket;

public class SharekhanOrder implements IOrder
{
	private String scrip = "";
	private BuySell	buySell = BuySell.BUY;
	private double price = 107.65d;
	private int quantity = 1;
	private OrderType orderType = OrderType.Limit;
	private Validity orderValidity	= Validity.IOC;
	private StockMarket exchangecode = StockMarket.BSE;

	private int disclosedorderqty = 0;
	private double tprice = 0.0d;
	private Validity stoplossValidity	= Validity.GFD;
	private String bktlossstoploss = "0";
	private String stoplossvaliditydate	= "20/07/2015";
	private String bktlossprice	= "0";
	private long dpclient	= 21657328;

	//Not important args
	private String comment	= "ajay";
	private String execute	= "neworder";
	private String requestcode	= "NEW";
	private String afterhour = "N";
	private String minlot = "0";

	public static void main(String[] args) 
	{
		//BuySell b = BuySell.SHORTSELL;
		//System.out.println(b);
		
		Date d;
		
		
		OrderType ot = OrderType.Limit;
		System.out.println(ot);
	}
	
	public static enum Validity
	{
		IOC,GFD,NONE;
	}
	
	public static enum OrderType
	{
		Limit,Market;
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
