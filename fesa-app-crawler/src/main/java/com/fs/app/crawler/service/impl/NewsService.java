package com.fs.app.crawler.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.fs.commons.app.pojo.NewsClassifyPojo;
import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.crawler.repository.INewsRepository;
import com.fs.commons.crawler.service.INewsService;
import com.wilddog.client.Wilddog;

@Service
@org.springframework.stereotype.Service
public class NewsService implements INewsService {
	@Autowired
	private INewsRepository newsRepository;

	@Override
	public String pushNewsBywilddog(Map<String, String[]> pdata) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String[] obj = pdata.get("data");
			String[] obj_url = pdata.get("url");
			System.out.println("开始判断参数");
			if (obj != null && obj.length > 0) {
				NewsPojo pojo = JSONObject.parseObject(obj[0], NewsPojo.class);
				String stime = pojo.getTime().replace("来源:", "").trim();
				pojo.setTime(stime);
				pojo.setTimestamp(sdf.parse(stime).getTime());
				pojo.setUrl(obj_url[0]);
				System.out.println("开始调用野狗API");
				Wilddog ref = new Wilddog("https://201605111151fei.wilddogio.com");
				ref.child("news").push().setValue(pojo);
				Thread.sleep(100);
				System.out.println("调用野狗API结束");
			} else
				return "";
		} catch (Exception ex) {
			System.out.println("错误：" + ex.getMessage());
		}
		return "success";
	}

	@Override
	public String pushNewsByNative(Map<String, String[]> pdata) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date_flag=new Date();
		Calendar cale_flag = Calendar.getInstance();
		try {
			cale_flag.setTime(date_flag);
			cale_flag.set(Calendar.DATE, cale_flag.get(Calendar.DATE) - 7);
			date_flag = sdf.parse(sdf.format(cale_flag.getTime()));
			String[] obj = pdata.get("data");
			String[] obj_url = pdata.get("url");
			if (obj != null && obj.length > 0) {
				NewsPojo pojo = JSONObject.parseObject(obj[0], NewsPojo.class);
				String stime = pojo.getTime().replace("来源:", "").trim();
				pojo.setTime(stime);
				pojo.setTimestamp(sdf.parse(stime).getTime());
				pojo.setUrl(obj_url[0]);
				if(pojo.getTimestamp()>date_flag.getTime()){
					newsRepository.saveNews(pojo);
				}
			} else
				return "";
		} catch (Exception ex) {
			System.out.println("错误：" + ex.getMessage());
		}
		return "success";
	}

	@Override
	public List<NewsPojo> getNewsForPage(int pid,Long timstamp, String type,int cateid) {
		List<NewsPojo> list_news = newsRepository.getNewsForPage(pid,timstamp,type,cateid);
		return list_news;
	}

	@Override
	public NewsPojo getNewsById(int id) {
		NewsPojo pojo = newsRepository.getNewsById(id);
		return pojo;
	}
	@Override
	public List<NewsClassifyPojo> getNewsCategory(){
		List<NewsClassifyPojo> classify=newsRepository.getNewsCategory();
		return classify;
	}
	@Override
	public List<NewsPojo> getNewsForCategory(String category,int pagesize) {
		List<NewsPojo> news=newsRepository.getNewsForCategory(category,pagesize);
		return news;
	}

	@Override
	public Boolean updateNewsCategory(NewsPojo pojo, String category) {
		Boolean result= newsRepository.updateNewsCategory(pojo,category);
		return result;
	}
}
