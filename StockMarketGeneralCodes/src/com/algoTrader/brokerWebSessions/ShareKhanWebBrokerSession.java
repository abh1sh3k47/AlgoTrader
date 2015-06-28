package com.algoTrader.brokerWebSessions;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class ShareKhanWebBrokerSession
{
	private HttpContext localContext;
	private boolean loggedIn=false;

	private final String userName;
	private final String password;
	private final String tradingPassword;
	
	private long lastQueryTimestamp = -1;

	public ShareKhanWebBrokerSession(String userName, String password,String tradingPassword)
	{
		this.userName = userName;
		this.password = password;
		this.tradingPassword = tradingPassword;
		localContext = new BasicHttpContext();
	}
	
	public void updateLastQueryTimestamp()
	{
		lastQueryTimestamp = System.currentTimeMillis();
	}
	
	public HttpContext getLocalContext() {
		return localContext;
	}

	public void setLocalContext(HttpContext localContext) {
		this.localContext = localContext;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getTradingPassword() {
		return tradingPassword;
	}

	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}

