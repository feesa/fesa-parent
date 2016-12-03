package com.fs.app.portal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

	public String getConfig(String key){
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("configs/fesa.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
}
