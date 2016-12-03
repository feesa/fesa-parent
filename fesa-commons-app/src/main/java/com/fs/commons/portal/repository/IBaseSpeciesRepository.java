package com.fs.commons.portal.repository;

import java.util.List;
import com.fs.commons.app.pojo.BaseSpeciesPojo;

public interface IBaseSpeciesRepository {

	public void save(BaseSpeciesPojo basespecies);

	public void update(BaseSpeciesPojo basespecies);

	public BaseSpeciesPojo getBaseSpecies(Integer Speciesid);
	
	public List<BaseSpeciesPojo> getTopBaseSpeceies(int start,int end);

	public void delete(Integer Speciesid);

	public List<BaseSpeciesPojo> getbaseSpeciess();

}