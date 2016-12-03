package com.fs.app.analysis.service.classify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fs.app.analysis.utils.BosonApiHelper;
import com.fs.app.analysis.utils.HtmlParse;
import com.fs.commons.analysis.service.IClassifyService;
import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.crawler.service.INewsService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class ClassifyService implements IClassifyService{
	
	@Reference(timeout=5000)
	private INewsService newsService;
	
	public List<NewsPojo> updateClassify(List<NewsPojo> news){
		List<String> contents=new ArrayList<String>();
		for (NewsPojo pojo : news) {
			contents.add(HtmlParse.delHTMLTag(pojo.getContext()));
		}
		if(contents.size()>0){
			try {
				String str=com.alibaba.fastjson.JSONArray.toJSONString(contents);
				HttpResponse<JsonNode> Response = Unirest
					    .post(BosonApiHelper.classifyurl)
					    .header("Accept", "application/json")
					    .header("Content-Type", "application/json")
					    .header("X-Token", BosonApiHelper.myapitoken)
					    .body(str)
					    .asJson();
				JsonNode node=Response.getBody();
				JSONArray jsonarray= node.getArray();
				for(int i=0;i<contents.size();i++){
					String cls=jsonarray.get(i).toString();
					news.get(i).setCategory(cls);
				}
				//Unirest.shutdown();
				for (NewsPojo pojo : news) {
					newsService.updateNewsCategory(pojo,pojo.getCategory());
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
		return news;
	}
	@Override
	public boolean initClassify(int pagesize){
		List<NewsPojo> pojos= newsService.getNewsForCategory("",pagesize);
		List<NewsPojo> tmppojos=new ArrayList<NewsPojo>();
		int index=0;
		for (NewsPojo newsPojo : pojos) {
			tmppojos.add(newsPojo);
			index++;
			if(index>=40){
				updateClassify(tmppojos);
				index=0;
				tmppojos=new ArrayList<NewsPojo>();
			}
		}
		if(index<40){
			updateClassify(tmppojos);
		}
		return true;
	}
}
