package com.fs.platform.runtime;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppPlatformServletContainerInitializer implements ServletContainerInitializer{

	private static final Logger logger=LoggerFactory.getLogger("AppPlatformServletContainerInitializer");
	
	public void onStartup(Set<Class<?>> classesParam, ServletContext ctxParam) throws ServletException {
		logger.info("===平台容器初始化开始===");
		final AppPlatformRuntime pr = new AppPlatformRuntime(ctxParam);
		logger.info("===平台容器初始化完成===");
	}
}
