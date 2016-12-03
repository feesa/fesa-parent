package com.fs.platform.runtime.rpc;

import java.util.HashSet;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcUtils implements IRpcUtils {

	private HashSet<Class<?>> scanningRemoteInterface = null;
	public IRpcProxyFactory rpcProxy = null;
	public Map<String, Object> interfaceNames2singleton = null;
	private static Logger log = LoggerFactory.getLogger(RpcUtils.class);

	public RpcUtils(HashSet<Class<?>> scanningRemoteInterface, IRpcProxyFactory rpcProxy,
			Map<String, Object> interfaceNames2singleton) {
		this.scanningRemoteInterface = scanningRemoteInterface;
		this.rpcProxy = rpcProxy;
		this.interfaceNames2singleton = interfaceNames2singleton;
	}

	public void newInstance(Class<?> cls) {
		if (interfaceNames2singleton.get(cls.getName()) != null) {
            return;
        }
        if (scanningRemoteInterface.contains(cls)) {
            Object object = rpcProxy.proxyInvoke(cls);
            interfaceNames2singleton.put(cls.getName(), object);
            log.info("Create Remote Service Proxy : {}", cls.getName());
        }
	}

	public void newInstance(String clsName) {
		try {
            newInstance(Class.forName(clsName));
        }
        catch (Exception e) {
            log.error("加载类：{}失败:{}", clsName,e.getMessage());
        }
	}
}
