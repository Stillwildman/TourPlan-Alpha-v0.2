package com.map1;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XmlWeatherParser {

		public static TomorrowWeatherVO parse(String googleWeatherString) {

			Log.i("Brack", "XmlWeatherParser.parse");

			//int findCount = 1;

			TomorrowWeatherVO tomorrowWeatherVO = new TomorrowWeatherVO();

			try {
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = factory.newPullParser();

				parser.setInput(new StringReader(googleWeatherString));

				int eventType = parser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					
					case XmlPullParser.START_DOCUMENT:
						break;

					case XmlPullParser.START_TAG:
						String tagName = parser.getName();
						
							if (tagName.equals("low")) {
								tomorrowWeatherVO.setLow(parser.getAttributeValue(0));
							}
							if (tagName.equals("high")) {
								tomorrowWeatherVO.setHigh(parser.getAttributeValue(0));
							}
							if (tagName.equals("condition")) {
								Log.i("Brack", "condition=" + parser.getAttributeValue(0));
								tomorrowWeatherVO.setCondition(parser.getAttributeValue(0));
							}
						
						break;
					case XmlPullParser.END_TAG:
						break;
					case XmlPullParser.END_DOCUMENT:
						break;
					}

					eventType = parser.next();
				}

			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return tomorrowWeatherVO;
		}

	
	/*	
	try {
    	String xml = "http://xml.ascc.net/test/wf/utf-8/application_xml/zh-utf8-3.xml";
    	URL url = new URL(xml);
    	InputStream is = url.openStream();
    	int eventType;
    	
    	
    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    	XmlPullParser xpParser = factory.newPullParser();
    	
    	xpParser.setInput(is, "utf-8");
    	
    	StringBuffer strBuf = new StringBuffer();
    	String strNode = null;
    	
    	eventType = xpParser.next();
    	
    	while (eventType != XmlPullParser.END_DOCUMENT) {
    		if (eventType == XmlPullParser.START_DOCUMENT) {
    			strBuf.append("-- XML_START_DOCUMENT --");
    		} else if (eventType == XmlPullParser.START_TAG) {
    			strNode = xpParser.getName();
    			strBuf.append("\nSTART_TAG: "+strNode);
    		} else if (eventType == XmlPullParser.TEXT) {    
                strNode = xpParser.getText();
                strBuf.append("\nTEXT: "+strNode);
    		} else if(eventType == XmlPullParser.END_TAG) {
                strNode = xpParser.getName();
                strBuf.append("\nEND_TAG: "+strNode);
    		}
    	}
        eventType = xpParser.next();
    } 
    catch (MalformedURLException e) {
    }
	catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/		

}