package com.fs.platform.runtime;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DbServiceInterceptor implements MethodInterceptor{

	private static Logger logger = LoggerFactory.getLogger(DbServiceInterceptor.class);
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,MethodProxy proxy) throws Throwable {
		logger.info("===before invoke "+method+"===");
		Object result= proxy.invokeSuper(obj, args);
		logger.info("===after invoke "+method+"===");
		return result;
	}

}
