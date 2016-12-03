package com.fs.app.recsys.etl.utils;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
	
	public String url = "jdbc:mysql://10.0.30.59/ptdb";  
    public String name = "com.mysql.jdbc.Driver";  
    public String user = "platform_app";  
    public String password = "yV2x60A3";
    
    public Connection conn = null;  
    public PreparedStatement pst = null;
    
    public void initConf(String _url,String _name,String _user,String _password){
    	this.url=_url;
    	this.name=_name;
    	this.user=_user;
    	this.password=_password;
    }
    public List<Map<String,String>> execQuery(String sql) {
    	List<Map<String,String>> result=new ArrayList<Map<String,String>>();
    	List<String> metas=new ArrayList<String>();
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
            ResultSet ret=pst.executeQuery();
            ResultSetMetaData retMetaData= ret.getMetaData();
            for (int i = 1; i < retMetaData.getColumnCount(); i++) {
            	String _name=retMetaData.getColumnLabel(i);
            	metas.add(_name);
			}
            while (ret.next()) {
            	Map<String,String> map=new HashMap<String,String>();
            	for (String str : metas) {
            		map.put(str, ret.getString(str));
				}
            	result.add(map);
            }//显示数据 
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	try {
				this.conn.close();
				this.pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}  
        }
        return result;
    }
}
