package com.fs.app.portal.repository.impl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.RecommendSpeciesPojo;
import com.fs.commons.portal.repository.IRecommendSpeciesRepository;

@Repository
@Transactional
public class RecommendSpeciesRepository implements IRecommendSpeciesRepository {
	@Autowired 
	private SessionFactory sessionFactory;
	@Override
	public void Save(RecommendSpeciesPojo recommend) {
		sessionFactory.getCurrentSession().save(recommend);
	}
	@Override
	public void update(RecommendSpeciesPojo recommend) {
		sessionFactory.getCurrentSession().update(recommend);
	}
	@Override
	public void delete(Integer id) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(RecommendSpeciesPojo.class, id));
	}
	@Override
	public RecommendSpeciesPojo getRecommendSpecies(Integer reid) {
		return (RecommendSpeciesPojo)sessionFactory.getCurrentSession().get(RecommendSpeciesPojo.class, reid);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RecommendSpeciesPojo> getrecommends() {
		return sessionFactory.getCurrentSession().createQuery("from recommendSpecies").list();
	}
}
