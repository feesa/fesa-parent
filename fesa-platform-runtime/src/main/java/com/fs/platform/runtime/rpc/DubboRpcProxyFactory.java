package com.fs.platform.runtime.rpc;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.fs.platform.runtime.config.IConfigurationService;

public class DubboRpcProxyFactory implements IRpcProxyFactory {

	private static final Logger logger = LoggerFactory.getLogger(DubboRpcProxyFactory.class);
	
	private final IConfigurationService conf;
	
	public DubboRpcProxyFactory(IConfigurationService conf){
		this.conf=conf;
	}
	
	public Object proxyInvoke(Class<?> serviceInterface) {
		if (!serviceInterface.isInterface()) {
			return null;
		}
		logger.info("-->begin proxyInvoke serviceInterface:{}", serviceInterface.getName());
		String appName = conf.get(IRpcProxyFactory.appName);
		String owner = conf.get(IRpcProxyFactory.appOwner);
		String registryAdress = conf.get(IRpcProxyFactory.registryAdress);
		String timeout = conf.get(IRpcProxyFactory.refTimeOut);

		StringBuffer _sb = new StringBuffer();
		_sb.append("appName : ").append(appName).append(", owner : ")
				.append(owner).append(", registryAdress : ")
				.append(registryAdress).append(", timeout : ").append(timeout);

		logger.info("dubbo Reference Configs :{}", _sb.toString());

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName(appName);
		application.setOwner(owner);
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(registryAdress);

		//监控中心
//		MonitorConfig monitor = new MonitorConfig();
//		monitor.setProtocol("registry");
		
		// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
		// 引用远程服务
		ReferenceConfig<Object> reference = new ReferenceConfig<Object>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplication(application);
		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
		reference.setInterface(serviceInterface);
		reference.setTimeout(Integer.parseInt(timeout));
		reference.setCheck(false);
		reference.setRetries(0);
//		reference.setMonitor(monitor);
		//reference.setFilter("serviceOutLogConsumer");
		
		ReferenceConfigCache cache = ReferenceConfigCache.getCache();
		// 调用服务方
		Object object = cache.get(reference); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用

		logger.info("-->end proxyInvoke ");
		return object;
	}

	public Object proxyService(Object obj, Class<?> serviceInterface) {
		if(!serviceInterface.isInterface()){
			return null;
		}
		
		logger.info("-->begin proxyService serviceInterface:{}" , serviceInterface.getName());
		
		String appName = conf.get(IRpcProxyFactory.appName);
		String owner = conf.get(IRpcProxyFactory.appOwner);
		String registryAdress = conf.get(IRpcProxyFactory.registryAdress);
		String protocolName = conf.get(IRpcProxyFactory.protocolName);
		String protocolPort = conf.get(IRpcProxyFactory.protocolPort);

		StringBuffer _sb = new StringBuffer();
		_sb.append("appName : ").append(appName).append(", owner : ")
				.append(owner).append(", registryAdress : ")
				.append(registryAdress).append(", protocolName : ")
				.append(protocolName).append(", protocolPort : ")
				.append(protocolPort);

		logger.info("dubbo Service Configs :{}", _sb.toString());

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName(appName);
		application.setOwner(owner);
		
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(registryAdress);
		
		// 服务提供者协议配置
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName(protocolName);
		protocol.setPort(Integer.parseInt(protocolPort));
		protocol.setRegister(true);
		//protocol.setThreads(200);
		
		//监控中心
//		MonitorConfig monitor = new MonitorConfig();
//		monitor.setProtocol("registry");
		
		// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
		// 服务提供者暴露服务配置
		ServiceConfig<Object> service = new ServiceConfig<Object>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
		service.setApplication(application);
		service.setRegistry(registry); // 多个注册中心可以用setRegistries()
		service.setProtocol(protocol); // 多个协议可以用setProtocols()
		service.setInterface(serviceInterface);
		service.setRef(obj);
//		service.setMonitor(monitor);
		//service.setFilter("serviceinlogprovider");
		// 暴露及注册服务
		service.export();	
		logger.info("-->end proxyService ");
		return service;
	}

	public void setInterfaceNames2singleton(Map<String, Object> interfaceNames2singleton) {
		// Dubbo不需要处理
	}
}
