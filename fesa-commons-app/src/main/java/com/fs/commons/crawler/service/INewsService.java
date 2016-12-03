package com.fs.commons.crawler.service;

import java.util.List;
import java.util.Map;

import com.fs.commons.app.pojo.NewsClassifyPojo;
import com.fs.commons.app.pojo.NewsPojo;

public interface INewsService {
	/*
	 * 野狗接口:添加新闻
	 */
	String pushNewsBywilddog(Map<String, String[]> pdata);

	/*
	 * 本地接口:添加新闻
	 */
	String pushNewsByNative(Map<String, String[]> pdata);

	/*
	 * 本地接口:查询新闻 type:loadmore,loadnew
	 */
	List<NewsPojo> getNewsForPage(int pid,Long timstamp, String type,int cateid);

	/*
	 * 本地接口:根据ID获取新闻实体
	 */
	NewsPojo getNewsById(int id);
	/*
	 * 获取所有的新闻分类
	 */
	List<NewsClassifyPojo> getNewsCategory();
	/*
	 * 获取未分类新闻
	 */
	List<NewsPojo> getNewsForCategory(String category,int pagesize);
	/*
	 * 更新新闻分类
	 */
	Boolean updateNewsCategory(NewsPojo pojo,String category);
}
