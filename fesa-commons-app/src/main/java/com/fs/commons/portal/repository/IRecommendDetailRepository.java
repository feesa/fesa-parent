package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.RecommendDetailPojo;

public interface IRecommendDetailRepository {

	public void Save(RecommendDetailPojo recommend);
	
	public void update(RecommendDetailPojo recommend);
	
	public void delete(Integer id);
	
	public List<RecommendDetailPojo> getreDetails(Integer speid);
}
