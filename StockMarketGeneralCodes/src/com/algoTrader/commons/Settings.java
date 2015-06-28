package com.algoTrader.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.algoTrader.brokerWebSessions.ShareKhanWebBrokerSession;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class Settings 
{
	private static Logger logger= Logger.getLogger(Settings.class);
	public static final String SETTINGS_FILENAME = "settings.properties";
	public static final String GLOBAL_PROPERTIES_FILENAME = "globalConstants.properties";

	public static final String SHAREKHAN_USERNAME_KEY = "shareKhanUserName";
	public static final String SHAREKHAN_PASSWORD_KEY = "shareKhanPassword";
	public static final String SHAREKHAN_TRADING_PASSWORD_KEY = "shareKhanTradingPassword";
	public static final String SHAREKHAN_REPORT_FILE_PATH_KEY = "shareKhanReportFilePath";
	public static final String SHAREKHAN_ORDER_DETAILS_FOLDER_PATH_KEY = "shareKhanOrderDetailsFolderPath";
	public static final String DATA_FOLDER_KEY = "dataFolder";
	
	private String shareKhanUserName;
	private String shareKhanPassword;
	private String shareKhanTradingPassword;
	private String shareKhanReportFilePath;
	private String shareKhanOrderDetailsFolderPath;
	private String dataFolder;		
	
	private static Settings settings;
	private Properties settingsFile;
	
	public static Settings getSettings()
	{
		if(settings == null)
		{
			settings = new Settings();
			settings.settingsFile = new Properties();
			try 
			{
				settings.settingsFile.load(new FileInputStream(Settings.SETTINGS_FILENAME));
			} 
			catch (Exception e)
			{
				logger.error("Could not instantiate Settings object",e);
				e.printStackTrace();
			}
			settings.shareKhanUserName = settings.settingsFile.getProperty(SHAREKHAN_USERNAME_KEY);
			settings.shareKhanPassword = settings.settingsFile.getProperty(SHAREKHAN_PASSWORD_KEY);
			settings.shareKhanTradingPassword = settings.settingsFile.getProperty(SHAREKHAN_TRADING_PASSWORD_KEY);
			settings.shareKhanReportFilePath = settings.settingsFile.getProperty(SHAREKHAN_REPORT_FILE_PATH_KEY);
			settings.shareKhanOrderDetailsFolderPath = settings.settingsFile.getProperty(SHAREKHAN_ORDER_DETAILS_FOLDER_PATH_KEY);
			settings.dataFolder = settings.settingsFile.getProperty(DATA_FOLDER_KEY);
		}
		return settings;
	}
	
	private Settings()
	{
		
	}

	public String getShareKhanUserName() {
		return shareKhanUserName;
	}
	
	public String getShareKhanPassword() {
		return shareKhanPassword;
	}

	public String getShareKhanTradingPassword() {
		return shareKhanTradingPassword;
	}
	
	public String getShareKhanReportFilePath() {
		return shareKhanReportFilePath;
	}

	public String getShareKhanOrderDetailsFolderPath() {
		return shareKhanOrderDetailsFolderPath;
	}

	public String getDataFolder() {
		return dataFolder;
	}
}
