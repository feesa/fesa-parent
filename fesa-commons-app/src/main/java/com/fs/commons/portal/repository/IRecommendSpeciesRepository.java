package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.RecommendSpeciesPojo;

public interface IRecommendSpeciesRepository {

	public void Save(RecommendSpeciesPojo recommend);
	
	public void update(RecommendSpeciesPojo recommend);
	
	public void delete(Integer id);
	
    public RecommendSpeciesPojo getRecommendSpecies(Integer reid);
	
	public List<RecommendSpeciesPojo> getrecommends();
}
