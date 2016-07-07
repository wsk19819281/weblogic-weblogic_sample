package com.ictra.gu.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Properties;

public class GUPropertyReader {
	private static final String GUPROPERTIES_PROPERTIES = "guproperties.properties";
	static Properties properties = null;

	public static void init() throws Exception {
		if (properties == null) {
			properties = new Properties();
			URL url =  ClassLoader.getSystemResource(GUPROPERTIES_PROPERTIES);
			if (url == null) {
				String cp = System.getProperty("java.class.path");
				throw new FileNotFoundException("unable to locate property file " + GUPROPERTIES_PROPERTIES + " in classpath " + cp);
			}
			properties.load(new FileInputStream(new File(url.getFile())));

		}
	}

	private static String getProperty(String propertyName) throws Exception {
		init();
		return properties.getProperty(propertyName);
	}

	public static String getStringProperty(String propertyName) throws Exception {
		return getProperty(propertyName);
	}

	public static int getIntProperty(String propertyName) throws Exception  {
		return Integer.parseInt(getProperty(propertyName));
	}

	public static boolean getBooleanProperty(String propertyName) throws Exception  {
		return Boolean.parseBoolean(getProperty(propertyName));
	}

}
