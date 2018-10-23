package com.qa.restassured;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class BaseClass {

	public static final String configFile = "./src/main/java/com/qa/config/config.properties";
	static Properties properties;
	public static final int RESPONSE_STATUS_CODE_200 = 200;
	public static final int RESPONSE_STATUS_CODE_201 = 201;
	public static final int RESPONSE_STATUS_CODE_400 = 400;
	public static final int RESPONSE_STATUS_CODE_404 = 404;
	public static final int RESPONSE_STATUS_CODE_204 = 204;
	
	public BaseClass() {
		properties = new Properties();
		
		try {
			properties.load(new FileInputStream(configFile));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static URL setUp(String endpoint)  {
		
	String serviceUrl = properties.getProperty("serviceUrl");
	String url = properties.getProperty(endpoint);
	URL uri = null;
	try {
		uri = new URL( serviceUrl + url );
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
	return uri;
	}
}
