package com.algoTrader.workers;

import static com.algoTrader.commons.BrokerageConstants.*;
import static com.algoTrader.commons.GlobalConstants.ORDERID_DELIM;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.algoTrader.brokerWebSessions.ShareKhanWebBrokerSession;
import com.algoTrader.commons.CommonUtils;
import com.algoTrader.commons.Settings;
import com.algoTrader.parsingUtils.HtmlParsingUtils;

public class ShareKhanWorker 
{
	private static Logger logger= Logger.getLogger(ShareKhanWorker.class);
	private ShareKhanWebBrokerSession brokerSession;
	private HttpClient httpClient;
	
	//Making this singleton for now.
	private static ShareKhanWorker worker;
	
	public static synchronized ShareKhanWorker getInstance()
	{
	  	if(worker == null)
	  	{
	  		Settings settings = Settings.getSettings(); 
	  		worker = new ShareKhanWorker( settings.getShareKhanUserName(),
					  settings.getShareKhanPassword(),
					  settings.getShareKhanTradingPassword(), CommonUtils.getHttpClient());
	  	}
	  	return worker;
	}
	
	private ShareKhanWorker(String username, String password, String tradingPassword, HttpClient httpClient)
	{
		brokerSession = new ShareKhanWebBrokerSession(username, password, tradingPassword);
		this.httpClient = httpClient;
	}
	
	public synchronized void login() throws Exception 
	{
		logger.debug("CALL login");
		
		if(brokerSession.isLoggedIn())
		{
			logger.debug("Already Logged in, returning");
			return;
		}
		
		HttpPost httpPost = new HttpPost(SHAREKHAN_LOGIN_URL);

		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("execute","weblogin"));
		nvps.add(new BasicNameValuePair("collabration","WEB"));
		nvps.add(new BasicNameValuePair("caller","")); //https://newtrade.sharekhan.com/rmmweb/rcs.sk
		nvps.add(new BasicNameValuePair("loginid",brokerSession.getUserName()));
		nvps.add(new BasicNameValuePair("brpwd",brokerSession.getPassword()));
		nvps.add(new BasicNameValuePair("pwdtype","pwdtr"));
		nvps.add(new BasicNameValuePair("trpwd",brokerSession.getTradingPassword()));
		nvps.add(new BasicNameValuePair("Login.x","36"));
		nvps.add(new BasicNameValuePair("Login.y","15"));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		CommonUtils.addDefaultHeaders(httpPost);
		try
		{
			HttpResponse response = httpClient.execute(httpPost,brokerSession.getLocalContext());
			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode!=200)
			{
				logger.error(statusLine);
			}
			else
			{
				brokerSession.setLoggedIn(true);
				logger.debug(statusLine);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		//printStream(entity.getContent());
		//printToFile(EntityUtils.toString(entity));
		logger.debug("RETURN login");
	}

	public String getOrderReportDom() throws Exception
	{
		logger.debug("CALL getOrderReportDom()");
		if(!brokerSession.isLoggedIn())
		{
			login();
		}
		
		HttpGet httpGet = new HttpGet(SHAREKHAN_EQUITYORDERS_URL);
		CommonUtils.addDefaultHeaders(httpGet);
		String reportDom = null;
		try
		{
			HttpResponse response = httpClient.execute(httpGet,brokerSession.getLocalContext());
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode==200)
			{
				HttpEntity entity = response.getEntity();
				reportDom = EntityUtils.toString(entity);
				//printToFile(reportDom);
				logger.debug(statusLine);
			}
			else
			{
				logger.error(statusLine);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}

		logger.info("RETURN getOrderReportDom()");
		return reportDom;
	}

	public String getOrderDetailsDom(String orderId) throws Exception
	{
		if(!brokerSession.isLoggedIn())
		{
			login();
		}
		
		String url = SHAREKHAN_ORDER_DETAILS_URL;
		url = url.replace(ORDERID_DELIM, orderId);
		HttpGet httpGet = new HttpGet(url);
		CommonUtils.addDefaultHeaders(httpGet);

		HttpResponse response = httpClient.execute(httpGet,brokerSession.getLocalContext());
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if(statusCode!=200)
		{
			logger.error(statusLine);
		}
		else
		{
			logger.debug(statusLine);
		}

		HttpEntity entity = response.getEntity();
		String reportDetailsDom = EntityUtils.toString(entity);
		//printToFile(reportDom);
		logger.debug("downloaded order report details -reportId='"+orderId+"'");
		return reportDetailsDom;
	}
	
	public String getBidAskListDom(String scrip) throws Exception
	{
		if(!brokerSession.isLoggedIn())
		{
			login();
		}
				
		HttpPost httpPost = new HttpPost(SHAREKHAN_BID_ASK_LIST_URL);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("e","91"));
		nvps.add(new BasicNameValuePair("s",scrip));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		
		HttpResponse response = httpClient.execute(httpPost, brokerSession.getLocalContext());
		HttpEntity entity = response.getEntity();
		String dom = EntityUtils.toString(entity);//HtmlParsingUtils.getStringFromIs(entity.getContent());
		
		return dom;
	}
	
	public static void main(String ... args)
	{
		
	}
}
