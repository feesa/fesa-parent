package com.fs.app.portal.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.BaseSpeciesPojo;
import com.fs.commons.portal.repository.IBaseSpeciesRepository;

@Repository
@Transactional
public class BaseSpeciesRepository implements IBaseSpeciesRepository {
	@Autowired 
	private SessionFactory sessionFactory;

	public void save(BaseSpeciesPojo basespecies){
		sessionFactory.getCurrentSession().persist(basespecies);
	}
	
	public void update(BaseSpeciesPojo basespecies){
		sessionFactory.getCurrentSession().merge(basespecies);
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BaseSpeciesPojo getBaseSpecies(Integer speciesid){
		return (BaseSpeciesPojo)sessionFactory.getCurrentSession().get(BaseSpeciesPojo.class, speciesid);
	}

	public void delete(Integer speciesid){
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(BaseSpeciesPojo.class, speciesid));
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	@SuppressWarnings("unchecked")
	public List<BaseSpeciesPojo> getbaseSpeciess(){		
		return sessionFactory.getCurrentSession().createQuery("from baseSpecies where Status=true").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseSpeciesPojo> getTopBaseSpeceies(int start, int end) {
		Query hquery=sessionFactory.getCurrentSession().createQuery("from baseSpecies spe where spe.status=1 order by spe.count desc");
		hquery.setFirstResult(start);
		hquery.setMaxResults(end);
		
		return (List<BaseSpeciesPojo>)hquery.list();
	}
}
