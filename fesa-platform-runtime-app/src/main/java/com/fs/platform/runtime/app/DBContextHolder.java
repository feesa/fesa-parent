package com.fs.platform.runtime.app;

public class DBContextHolder {
	
	public static final String DATA_SOURCE = "dataSource";  
    public static final String DATA_SOURCE_1 = "dataSource1";  
      
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
      
    public static void setDBType(String dbType) {  
        contextHolder.set(dbType);  
    }  
      
    public static String getDBType() {  
        return contextHolder.get();  
    }  
      
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}
