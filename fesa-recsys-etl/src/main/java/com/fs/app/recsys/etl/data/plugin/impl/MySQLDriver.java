package com.fs.app.recsys.etl.data.plugin.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataOutputStream;
import com.fs.app.recsys.etl.data.plugin.DataSourceProperties;
import com.fs.app.recsys.etl.data.plugin.IDataDriver;
import com.fs.app.recsys.etl.model.RelationModel;
import com.fs.app.recsys.etl.utils.DBHelper;

public class MySQLDriver extends IDataDriver {

	@Override
	public void initConf(DataSourceProperties prop) {

	}

	@Override
	public void preProcess(String sql, Map<String, String> meta_map) {
		metamap = meta_map;
		DBHelper dbHelper = new DBHelper();
		List<Map<String, String>> result = dbHelper.execQuery(sql);
		originaldata = result;
	}

	@Override
	public void processData() {
		Object obj = metamap.get("modeltype");
		if (obj != null) {
			String _type = obj.toString();
			if (_type.equals("1")) {
				for (Map<String, String> ele : originaldata) {
					RelationModel usermodel = new RelationModel();
					usermodel.setId(Calendar.getInstance().getTimeInMillis());
					for (Entry<String, String> entry : ele.entrySet()) {
						String _name = metamap.get(entry.getKey());
						if (_name != null) {
							if (_name.equals("sponsorid")) {
								usermodel.setSponsorid(entry.getValue());
							} else if (_name.equals("relation")) {
								usermodel.setRelation(entry.getValue());
							} else if (_name.equals("itemid")) {
								usermodel.setItemid(entry.getValue());
							} else if (_name.equals("timeline")) {
								usermodel.setTimeline(entry.getValue());
							}
						}
					}
					userdatas.add(usermodel);
				}
			} else if (_type.equals("2")) {

			} else {

			}
		}
	}

	@Override
	public void persistentData() {
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
//			fs.delete(new Path(hdfsUrl));
			FSDataOutputStream hdfsOutStream = fs.create(new Path(hdfsUrl+"001"));
			Object obj = metamap.get("modeltype");
			if (obj != null) {
				String _type = obj.toString();
				if (_type.equals("1")) {
					for (RelationModel model : userdatas) {
						hdfsOutStream.writeBytes(model.toString());
					}
				}else{
					//todo
				}
			}
			hdfsOutStream.close();
			fs.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
