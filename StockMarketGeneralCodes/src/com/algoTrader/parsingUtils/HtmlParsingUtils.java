package com.algoTrader.parsingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.log4j.Logger;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class HtmlParsingUtils 
{
	private static Logger logger= Logger.getLogger(HtmlParsingUtils.class);
	
	public static String getStringFromIs(InputStream is) throws IOException
	{
		String s = null;
		
		String retString = "";
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		while((s = bf.readLine()) != null)
		{
			retString += s+"\n";
		}
		return retString;
	}
	
	public static List<String> getTagBodiesFromAString(String s,String tag)
	{
		logger.debug("CALL getTagBodiesFromAString - Tag : "+tag);
		List<String> tagBodies = new LinkedList<String>();
		
		String tagStart = "<"+tag;
		String tagEnd = "</"+tag+">";
		
		int tagIndex = s.indexOf(tagStart);
		int tagEndIndex = s.indexOf(tagEnd,tagIndex);
		int startTagEndIndex = s.indexOf(">",tagIndex)+1;
		
		while(tagIndex!=-1)
		{
			if(tagEndIndex==-1)
			{
				logger.error("No end tag found for - Tag:"+tag);
				throw new ParseException("Invalid input string.");
			}
			
			String body = s.substring(startTagEndIndex,tagEndIndex);
			tagBodies.add(body);
			
			tagIndex = s.indexOf(tagStart,tagEndIndex);
			tagEndIndex = s.indexOf(tagEnd,tagIndex);
			startTagEndIndex = s.indexOf(">",tagIndex)+1;
		}
		logger.debug("RETURN getTagBodiesFromAString - Tag : "+tag);
		return tagBodies;
	}
	
	public static Map<String,String> getAllTagValues(String dom)
	{
		Map<String,String> tagValueMap = new HashMap<String,String>();
		
		int tagStartBraceIndex = dom.indexOf("<");
		int tagEndBraceIndex = dom.indexOf(">",tagStartBraceIndex);
		
		while(tagStartBraceIndex!=-1)
		{
			String tag = dom.substring(tagStartBraceIndex+1,tagEndBraceIndex);
			String endTag = "</"+tag+">";
			int endTagIndex = dom.indexOf(endTag,tagEndBraceIndex);
			String tagValue = dom.substring(tagEndBraceIndex+1,endTagIndex).trim();
			tagValueMap.put(tag, tagValue);
			
			tagStartBraceIndex = dom.indexOf("<",endTagIndex+endTag.length());
			tagEndBraceIndex = dom.indexOf(">",tagStartBraceIndex);
		}
		return tagValueMap;
	}
	
}
