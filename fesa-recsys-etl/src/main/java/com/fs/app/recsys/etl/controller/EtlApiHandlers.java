package com.fs.app.recsys.etl.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fs.app.recsys.etl.data.plugin.IDataDriver;
import com.fs.app.recsys.etl.data.plugin.impl.MySQLDriver;
import com.fs.commons.app.entity.RenderData;

@Controller
@RequestMapping("/data")
public class EtlApiHandlers extends BaseHandlers{

	@ResponseBody
	@RequestMapping(value = "/getDataFromDB", method = RequestMethod.GET)
	public void getDataFromDB(HttpServletRequest request,HttpServletResponse response) {
		//业务数据
		String sql="select guess_id,user_id,period_id,number_id,guess_date from portal_wallet_guess order by guess_date desc limit 10";
		Map<String,String> metas=new HashMap<String, String>();
		metas.put("modeltype", "1");//1:user,2:item
		metas.put("user_id","sponsorid");
		metas.put("period_id","relation");
		metas.put("number_id","itemid");
		metas.put("guess_date","timeline");
		//数据接入
		IDataDriver dataDriver=new MySQLDriver();
		dataDriver.preProcess(sql,metas);
		dataDriver.processData();
		dataDriver.persistentData();
		RenderData result=new RenderData(dataDriver.userdatas,"猜大盘数据");
		WriteJson(response,result);
	}
}
