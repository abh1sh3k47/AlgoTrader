package com.algoTrader.commons;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class BrokerageConstants 
{	
	public static final String SHAREKHAN_LOGIN_URL_KEY = "shareKhan.Login.URI";
	public static final String SHAREKHAN_EQUITY_ORDERS_URL_KEY = "shareKhan.equityOrders.report.URL";
	public static final String SHAREKHAN_ORDER_DETAILS_URL_KEY = "shareKhan.orderDetails.URL";
	public static final String SHAREKHAN_BID_ASK_LIST_URL_KEY = "shareKhan.bidAskListUrl";
	public static final String SHAREKHAN_ORDER_TABLE_URL_KEY = "shareKhan.orderTableUrl";
	public static final String SHAREKHAN_ORDER_EXECUTE__URL_KEY =	"shareKhan.orderExecuteUrl";
	public static final String SHAREKHAN_ORDER_CONFIRMATION_URL_KEY = "shareKhan.orderConfirmationUrl";

	//Fields Read From globalSettings.properties
	public static final String SHAREKHAN_LOGIN_URL;
	public static final String SHAREKHAN_EQUITYORDERS_URL;
	public static final String SHAREKHAN_ORDER_DETAILS_URL;
	public static final String SHAREKHAN_BID_ASK_LIST_URL;
	public static final String SHAREKHAN_ORDER_TABLE_URL;
	public static final String SHAREKHAN_ORDER_EXECUTE_URL;
	public static final String SHAREKHAN_ORDER_CONFIRMATION_URL;

	private static Properties globalConstants = new Properties();

	static
	{
		String loginUrl = "";
		String equityOrdersUrl = "";
		String orderDetailsUrl = "";
		String bidAskListUrl = "";
		String orderTableUrl = "";
		String orderExecuteUrl = "";
		String orderConfirmationUrl = "";
		
		try 
		{
			globalConstants.load(new FileInputStream(Settings.GLOBAL_PROPERTIES_FILENAME));
			loginUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_LOGIN_URL_KEY);
			equityOrdersUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_EQUITY_ORDERS_URL_KEY);
			orderDetailsUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_ORDER_DETAILS_URL_KEY);
			bidAskListUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_BID_ASK_LIST_URL_KEY);
			
			orderTableUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_ORDER_TABLE_URL_KEY);
			orderExecuteUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_ORDER_EXECUTE__URL_KEY);
			orderConfirmationUrl = globalConstants.getProperty(BrokerageConstants.SHAREKHAN_ORDER_CONFIRMATION_URL_KEY);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		SHAREKHAN_LOGIN_URL = loginUrl;
		SHAREKHAN_EQUITYORDERS_URL = equityOrdersUrl;
		SHAREKHAN_ORDER_DETAILS_URL = orderDetailsUrl;
		SHAREKHAN_BID_ASK_LIST_URL = bidAskListUrl;
		SHAREKHAN_ORDER_TABLE_URL = orderTableUrl;
		SHAREKHAN_ORDER_EXECUTE_URL = orderExecuteUrl;
		SHAREKHAN_ORDER_CONFIRMATION_URL = orderConfirmationUrl;
		
	}
}
