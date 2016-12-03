package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.BaseFeedPojo;

public interface IBaseFeedRepository {

	public void save(BaseFeedPojo baseFeed);

	public void update(BaseFeedPojo baseFeed);

	public BaseFeedPojo getBaseFeed(Integer feedid);
	
	public BaseFeedPojo getBaseFeed(String fedname);

	public BaseFeedPojo getBaseFeedbyURL(String address);
	
	public void delete(Integer feedid);

	public List<BaseFeedPojo> getbaseFeeds();
	
	public List<BaseFeedPojo> getbaseFeeds(String species);

}