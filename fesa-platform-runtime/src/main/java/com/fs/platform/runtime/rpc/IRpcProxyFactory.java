package com.fs.platform.runtime.rpc;

import java.util.Map;

public interface IRpcProxyFactory {

	static final String RPC_FACTORY = "fesa.platform.rpc.factory";
	public static final String appName = "fesa.apps.dubbo.application.name";
	public static final String appOwner = "fesa.apps.dubbo.application.owner";
	public static final String registryAdress = "fesa.apps.dubbo.registry.address";
	public static final String protocolName = "fesa.apps.dubbo.protocol.name";
	public static final String protocolPort = "fesa.apps.dubbo.protocol.port";
	public static final String refTimeOut = "fesa.apps.dubbo.reference.timeout";
    /**
     * 根据接口反射rpc代理对象，支持接口继承
     * @return
     */
    public Object proxyInvoke(Class<?> serviceInterface);
    
    /**
     * 生成服务提供者代理对象
     * @return
     */
    public Object proxyService(Object obj, Class<?> serviceInterface);
    
    /**
     * 设置接口单例
     * @param interfaceNames2singleton
     */
    public void setInterfaceNames2singleton(Map<String, Object> interfaceNames2singleton);
}
