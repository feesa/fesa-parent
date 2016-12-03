package com.fs.platform.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.platform.commons.annotation.FeSaFieldInit;
import com.fs.platform.commons.annotation.FeSaService;
import com.fs.platform.runtime.rpc.RpcUtils;

public class AppPlatformRuntime {

	private static final Logger logger=LoggerFactory.getLogger("AppPlatformRuntime");
	//-------------------0.接口和类集合-------------------
	final protected HashSet<Class<?>> scanningClasses = new HashSet<>();//所有接口和类集合
	//-------------------1.接口集合----------------------
	static Map<String, Class<?>> interfaceNames2Class = new HashMap<>();//含有FeSaService的接口定义[接口名,接口]
	final Map<String, Object> interfaceNames2ServiceNm = new HashMap<>();//含有FeSaService的接口定义[接口名,服务名]
	final Map<String, Object> interfaceNames2singleton = new HashMap<>();//含有FeSaService的接口定义[接口名,null]
	//-------------------2.类集合-----------------------
	final protected HashSet<Class<?>> toBeCreatedServiceClasses = new HashSet<>();//需要实例化的类[类]
	final protected HashMap<String, LinkedList<String>> toBeCreatedClasses4interfaces = new HashMap<>();//需要实例化的类[类名,实现的接口名]
	//-------------------3.[远程]接口和类-------------------
	final protected HashSet<Class<?>> scanningRemoteInterface = new HashSet<>();//远程服务接口[接口]
	final protected HashSet<Class<?>> scanningRemoteClasses = new HashSet<>();//远程服务实现[类]
	//------------------
	final Map<String, Set<Field>> classFieldCaches = new HashMap<String, Set<Field>>();//类字段缓存
	static final Map<String, ServiceMappingEntry> serviceFieldCaches = new HashMap<>();//服务字段缓存
	final Map<String, Object> classNames2singleton = new HashMap<>();//
	
	final Map<String, Object> platformServiceInterfaceNames2singleton = new HashMap<>();
	Map<String, Object> definedBean = new HashMap<>();// 自定义可注入类
	final Map<String, AspectJoinClass> classtoInterceptors = new ConcurrentHashMap<>();
	static Map<Class<?>, Object> contextClassMap = new HashMap<Class<?>, Object>();
	static Map<String, Object> contextServiceNameMap = new HashMap<String, Object>();
	//------------------100.其它-------------------------
	static volatile RpcUtils rpcUtils;
	
	AppPlatformRuntime(ServletContext ctx) {
		//1.包扫描
		for (String scanningPackage : new String[] { "com.fs.app", "com.fs.commons","com.fs.platform"}) {
			Set<Class<?>> a = ClassUtils.getClassesForSet(scanningPackage);
			scanningClasses.addAll(a);
		}
		//2.查询含有FeSaService注解的接口
		for (Class<?> scanningClass : scanningClasses) {
			FeSaService a=scanningClass.getAnnotation(FeSaService.class);
			if(a!=null){
				String scanningClassName=scanningClass.getName();
				if(!interfaceNames2Class.containsKey(scanningClassName)){
					interfaceNames2singleton.put(scanningClassName, null);
					interfaceNames2ServiceNm.put(scanningClassName, a.serviceName());
					interfaceNames2Class.put(scanningClassName, scanningClass);
				}
				if(a.remoting()){
					scanningRemoteInterface.add(scanningClass);
				}
			}
		}
		//3.查询所有实现接口的服务类
		for (Class<?> scanningClass : scanningClasses) {
			boolean addedToToBeCreated=false;
			for (Class<?> i : scanningClass.getInterfaces()) {
				if(scanningClass.isInterface())
					continue;
				String interfaceName=i.getName();
				String scanningClassName=scanningClass.getName();
				if(interfaceNames2singleton.containsKey(i.getName())){
					if(!toBeCreatedClasses4interfaces.containsKey(scanningClassName)){
						toBeCreatedClasses4interfaces.put(scanningClassName, new LinkedList<String>());
					}
					toBeCreatedClasses4interfaces.get(scanningClassName).add(interfaceName);
					if(!addedToToBeCreated){
						addedToToBeCreated=true;
						toBeCreatedServiceClasses.add(scanningClass);
						logger.info("===App implements 1st scan : will create instance for @FeSaService "+scanningClassName);
					}
					if(scanningRemoteInterface.contains(i)){
						scanningRemoteClasses.add(scanningClass);
						logger.info("===RemoteServiceClass : "+scanningClass.getName()+" RemoteInterface : "+i.getName());
					}
				}
			}
		}
		//4.创建动态代理
		createInstances(toBeCreatedServiceClasses, toBeCreatedClasses4interfaces, null, null, interfaceNames2singleton, true);
		//5.注解注入:对FeSaService和FeSaUriHandler注解注入
		for (Class<?> scanningClass : scanningClasses) {
			String classNm=scanningClass.getName();
			//5.1-扫描FeSaFieldInit注解过的全局属性并注入对应的service
			ServiceMappingEntry injectedEntry=serviceFieldCaches.get(classNm);
			if(injectedEntry!=null&&injectedEntry.getInterfaceClassNm()!=null){
				classFieldCaches.remove(scanningClass);
				Object target= getObjectFromSingleton(injectedEntry.getInterfaceClassNm());
				if(target!=null){
					for (Field injected : injectedEntry.getServiceFields()) {
						Class<?> fieldClass=injected.getType();
						Object fieldInstance=null;
						if(platformServiceInterfaceNames2singleton.containsKey(fieldClass.getName())){
							fieldInstance=platformServiceInterfaceNames2singleton.get(fieldClass.getName());
						}else if(interfaceNames2singleton.containsKey(fieldClass.getName())){
							fieldInstance=interfaceNames2singleton.get(fieldClass.getName());
							if(fieldInstance==null){
								rpcUtils.newInstance(fieldClass);
								fieldInstance=interfaceNames2singleton.get(fieldClass.getName());
							}
						}else if(definedBean.containsKey(fieldClass.getName())){
							fieldInstance=definedBean.get(fieldClass.getName());
						}else{
							logger.error("===not found instance of class{"+classNm+"}.field{"+fieldClass.getName()+"}");
							continue;
						}
						injected.setAccessible(true);//属性注入
						try{
							if(classtoInterceptors.containsKey(fieldClass.getName())){
								//todo:获取aop代理
							}
							injected.set(target, fieldInstance);
						}catch(Exception ex){
							ex.printStackTrace();
							continue;
						}
					}
					//将属性放入平台应用上下文
					contextClassMap.put(injectedEntry.getInterfaceClass(), target);
					if(injectedEntry.getServiceName()!=null&&injectedEntry.getServiceName().trim().length()>0){
						if(!contextServiceNameMap.keySet().contains(injectedEntry.getServiceName())){
							contextServiceNameMap.put(injectedEntry.getServiceName(), target);
						}
					}
				}else{
					continue;
				}
			}else{
				
			}
			//5.2-扫描FeSaFieldInit注解过的全局属性并注入对应的Handler
			Set<Field> objectFields=classFieldCaches.get(classNm);
			if(objectFields!=null&&!objectFields.isEmpty()){
				Object target=getObjectFromSingleton(classNm);
				if(target!=null){
					Field[] fields=(Field[])objectFields.toArray(new Field[objectFields.size()]);
					for(Field field : fields){
						Annotation fieldAnno=field.getAnnotation(FeSaFieldInit.class);
						if(fieldAnno!=null){
							try{
								Object instance=null;
								Class<?> fieldClass=field.getType();
								if(platformServiceInterfaceNames2singleton.containsKey(fieldClass.getName())){
									instance=platformServiceInterfaceNames2singleton.get(fieldClass.getName());
								}else if(interfaceNames2singleton.containsKey(fieldClass.getName())){
									instance=interfaceNames2singleton.get(fieldClass.getName());
									if(instance==null){
										rpcUtils.newInstance(fieldClass);
										instance=interfaceNames2singleton.get(fieldClass.getName());
									}
								}else if(definedBean.containsKey(fieldClass.getName())){
									instance=definedBean.get(fieldClass.getName());
								}else{
									logger.error("===not found instance of class{"+classNm+"}.field{"+fieldClass.getName()+"}");
									continue;
								}
								field.setAccessible(true);
								if(classtoInterceptors.containsKey(fieldClass.getName())){
									//todo:获取aop代理
								}
								field.set(target, instance);
							}catch(Exception ex){
								ex.printStackTrace();
								continue;
							}
						}
					}
				}
			}
		}
		//6.注解注入:将所有已实例化得对象放入ApplicationContext
		for (Class<?> scanningClass : scanningClasses) {
			String classNm=scanningClass.getName();
			if(scanningClass.isInterface()){
				Object ob=interfaceNames2singleton.get(classNm);
				if(ob!=null){
					Class<?> instanceClass=interfaceNames2Class.get(classNm);
					if(instanceClass==null){
						instanceClass=ob.getClass();
					}
					if(!contextClassMap.keySet().contains(instanceClass)){
						contextClassMap.put(interfaceNames2Class.get(classNm), ob);
					}
				}else{
					
				}
			}else{
				for(Object bean : definedBean.values()){
					contextClassMap.put(bean.getClass(), bean);
				}
			}
		}
		//7.初始化FeSaService初始化方法
	}
	protected static void createInstances(Set<Class<?>> toBeCreatedClasses,final HashMap<String, LinkedList<String>> toBeCreatedClasses4interfaces, 
			Map<String, Object> classNames2singleton,Map<String, Object> platformServiceInterfaceNames2singleton, 
			final Map<String, Object> interfaceNames2singleton, final boolean isServices) {
		Map<String, Object> singletonCache = new HashMap<String, Object>();
		boolean successed = ClassUtils.createInstances(rpcUtils,serviceFieldCaches, new IOnCreatedInstance() {
			@Override
			public boolean onCreatedInstance(String className, Object obj) {
				if (!isServices) {
					return false;
				}
				List<String> l = toBeCreatedClasses4interfaces.remove(className);
				if (l != null && !l.isEmpty()) {
					for (String i : l) {
						assert interfaceNames2singleton.get(i) == null;
						interfaceNames2singleton.put(i, obj);
					}
					return true;
				}
				return false;
			}
		}, toBeCreatedClasses, singletonCache, platformServiceInterfaceNames2singleton, interfaceNames2singleton);
		if (!successed) {
			logger.error("error creating instances, check log for detail");
		} else {
			toBeCreatedClasses.clear();
			if (isServices) {
				for (Map.Entry<String, Object> e : singletonCache.entrySet()) {
					String s = e.getKey();
					Object o = e.getValue();
					List<String> l = toBeCreatedClasses4interfaces.remove(s);
					if (l != null && !l.isEmpty()) {
						for (String i : l) {
							assert interfaceNames2singleton.get(i) == null;
							interfaceNames2singleton.put(i, o);
						}
					}
				}
			} else {
				classNames2singleton.putAll(singletonCache);
			}
		}
	}
	private Object getObjectFromSingleton(String classNm) {
		Object ob = classNames2singleton.get(classNm);
		if (ob == null) {
			ob = platformServiceInterfaceNames2singleton.get(classNm);
		}
		if (ob == null) {
			ob = interfaceNames2singleton.get(classNm);
			if(ob == null){
			    rpcUtils.newInstance(classNm);
			    ob = interfaceNames2singleton.get(classNm);
			}
		}
		if (ob == null) {
			ob = definedBean.get(classNm);
		}
		if(ob == null){
		    rpcUtils.newInstance(classNm);
		    ob = interfaceNames2singleton.get(classNm);
		}
		return ob;
	}
	protected static void cacheClassFields(Map<String, Set<Field>> classFieldCaches, Class<?> c) {
		String cn = c.getName();
		Set<Field>  fs = classFieldCaches.get(cn);
		if (fs == null) {
			fs = new HashSet<Field>();
			classFieldCaches.put(cn, fs);
		}
		Field[] classFields = c.getDeclaredFields();
		for (Field classField : classFields) {
			fs.add(classField);
		}
	}
}
