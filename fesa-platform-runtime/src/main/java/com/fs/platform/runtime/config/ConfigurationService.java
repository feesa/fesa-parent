package com.fs.platform.runtime.config;

import java.util.Properties;

import com.fs.platform.runtime.ClassUtils;

public class ConfigurationService implements IConfigurationService{

	protected final Properties[] appProperties;
	
	public ConfigurationService() {
		appProperties = ClassUtils.scanProperties("configs/fesa.properties");
	}
	public String get(String name) {
		Object result=null;
		for (Properties properties : appProperties) {
			Object val=properties.get(name);
			result=val;
			break;
		}
		return result==null?"":result.toString();
	}

}
