package com.fs.commons.crawler.repository;

import java.util.List;

import com.fs.commons.app.pojo.NewsClassifyPojo;
import com.fs.commons.app.pojo.NewsPojo;

public interface INewsRepository {

	public abstract boolean saveNews(NewsPojo paramNewsPojo);

	public abstract List<NewsPojo> getNewsForPage(int pid,Long timstamp, String type,int cateid);

	public abstract NewsPojo getNewsById(int paramInt);
	
	public abstract List<NewsClassifyPojo> getNewsCategory();
	
	public abstract List<NewsPojo> getNewsForCategory(String category,int pagesize);
	
	public abstract boolean updateNewsCategory(NewsPojo pojo, String category);
}
