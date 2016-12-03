package com.fs.platform.runtime;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ServiceMappingEntry {
	/**
	 * 接口类
	 */
	private Class<?> interfaceClass;

	/**
	 * FeSaService实现类需要注入的全局变量
	 */
	private Set<Field> serviceFields = new HashSet<Field>();

	/**
	 * FeSaService服务别名
	 */
	private String serviceName;

	/**
	 * @return the serviceFields
	 */
	public Set<Field> getServiceFields() {
		return serviceFields;
	}

	/**
	 * @param serviceFields the serviceFields to set
	 */
	public void setServiceFields(Set<Field> serviceFields) {
		this.serviceFields = serviceFields;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the interfaceClass
	 */
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	/**
	 * @param interfaceClass the interfaceClass to set
	 */
	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	/**
	 * @return the interfaceClassNm
	 */
	public String getInterfaceClassNm() {
		return interfaceClass.getName();
	}
}
