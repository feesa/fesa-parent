package com.fs.platform.runtime;

import java.lang.reflect.Method;

import com.fs.platform.commons.annotation.FeSaTransaction;

import net.sf.cglib.proxy.CallbackFilter;

public class FeSaCallbackFilter implements CallbackFilter{

	private static final int FILTER_DBSERVICE = 0x1;
	private static final int FILTER_NOOP = 0x0;
	public static FeSaCallbackFilter instance;

	public static FeSaCallbackFilter getInstance(){
		if(instance == null){
			instance = new FeSaCallbackFilter();
		}
		return instance;
	}

	@Override
	public int accept(Method method) {
		int isNeed = FILTER_NOOP;
		if(method.getAnnotation(FeSaTransaction.class) != null){
			isNeed = FILTER_DBSERVICE;
		}
		return isNeed;
	}
}
