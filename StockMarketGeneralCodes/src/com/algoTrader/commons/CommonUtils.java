package com.algoTrader.commons;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class CommonUtils 
{
	static private Header[] defaultHeaders;
	static
	{
		defaultHeaders = new Header[7];
		defaultHeaders[5] = new BasicHeader("Host","newtrade.sharekhan.com");
		defaultHeaders[5] = new BasicHeader("Connection","keep-alive");
		defaultHeaders[6] = new BasicHeader("Cache-Control","max-age=0");
		defaultHeaders[1] = new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*;q=0.8");
		defaultHeaders[4] = new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
		defaultHeaders[2] = new BasicHeader("Accept-Encoding","gzip,deflate,sdch");
		defaultHeaders[3] = new BasicHeader("Accept-Language","en-US,en;q=0.8");
	}
	public static HttpClient getHttpClient()
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}

	public static HttpClient getProxyHttpClient() throws Exception
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpHost proxy = new HttpHost("127.0.0.1",8888);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
		return httpClient;
	}

	public static void addDefaultHeaders(HttpRequestBase request)
	{
		for(Header h:defaultHeaders)
		{
			request.addHeader(h);
		}
	}
}
