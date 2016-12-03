package com.fs.app.portal.repository.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.RecommendDetailPojo;
import com.fs.commons.portal.repository.IRecommendDetailRepository;

@Repository
@Transactional
public class RecommendDetailRepository implements IRecommendDetailRepository {
	@Autowired 
	private SessionFactory sessionFactory;
	@Override
	public void Save(RecommendDetailPojo recommend) {
		sessionFactory.getCurrentSession().save(recommend);
	}
	@Override
	public void update(RecommendDetailPojo recommend) {
		sessionFactory.getCurrentSession().update(recommend);
	}
	@Override
	public void delete(Integer id) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(RecommendDetailPojo.class, id));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RecommendDetailPojo> getreDetails(Integer feid) {
		Query hquery = sessionFactory.getCurrentSession().createQuery("from recommendDetail de where de.speid=:spe");
		hquery.setInteger("spe", feid);
		List<RecommendDetailPojo> lists= hquery.list();
		return lists;
	}
}
