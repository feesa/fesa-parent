package com.fs.app.recsys.etl.data.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.app.recsys.etl.model.PropertyModel;
import com.fs.app.recsys.etl.model.RelationModel;

public abstract class IDataDriver {
	
	public String hdfsUrl="hdfs://archive.cloudera.com/user/rectest/";//hdfs数据存储目录
	public List<RelationModel> userdatas=new ArrayList<RelationModel>();//用户关系数据
	public List<PropertyModel> itemdatas=new ArrayList<PropertyModel>();//物品数据
	public List<Map<String,String>> originaldata=new ArrayList<Map<String,String>>();//原始数据
	public Map<String,String> metamap=new HashMap<String,String>();//字段映射
	/**
	 * 初始化配置
	 * @author :lvyf
	 * @created :2016年12月2日 上午9:21:03
	 */
	public abstract void initConf(DataSourceProperties prop);
	/**
	 * 数据预处理
	 * @author :lvyf
	 * @created :2016年12月2日 上午9:46:21
	 */
	public abstract void preProcess(String sql,Map<String,String> meta_map);
	/**
	 * 数据处理
	 * @author :lvyf
	 * @created :2016年12月2日 上午9:20:07
	 */
	public abstract void processData();
	/**
	 * 持久化数据
	 * @author :lvyf
	 * @created :2016年12月2日 上午9:42:10
	 */
	public abstract void persistentData();
}
