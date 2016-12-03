package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.BaseFeedDetailPojo;

public interface IFeedDetailRepository {

	public void save(BaseFeedDetailPojo feedDetail);

	public void update(BaseFeedDetailPojo feedDetail);
	
	public BaseFeedDetailPojo getFeedDetail(Integer detaid);
	
	public List<BaseFeedDetailPojo> getFeedDetail(String speid,Integer fedid); 
	
	public List<BaseFeedDetailPojo> getFeedDetailLimit(String speid,Integer fedid,int min,int max); 
}