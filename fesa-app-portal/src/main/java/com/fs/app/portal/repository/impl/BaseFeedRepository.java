package com.fs.app.portal.repository.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.BaseFeedPojo;
import com.fs.commons.portal.repository.IBaseFeedRepository;

@Repository
@Transactional
public class BaseFeedRepository implements IBaseFeedRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(BaseFeedPojo baseFeed) {
		// sessionFactory.getCurrentSession().persist(baseFeed);
		sessionFactory.getCurrentSession().save(baseFeed);
	}

	@Override
	public void update(BaseFeedPojo baseFeed) {
		// sessionFactory.getCurrentSession().merge(baseFeed);
		sessionFactory.getCurrentSession().update(baseFeed);
	}

	@Override
	public BaseFeedPojo getBaseFeed(Integer feedid) {
		return (BaseFeedPojo) sessionFactory.getCurrentSession().get(BaseFeedPojo.class, feedid);
	}

	@Override
	public BaseFeedPojo getBaseFeed(String fedname) {
		Query hquery = sessionFactory.getCurrentSession().createQuery("from baseFeed fed where fed.feedName=:name");
		hquery.setString("name", fedname);
		List<BaseFeedPojo> list_resuls = hquery.list();
		if (list_resuls != null && list_resuls.size() > 0)
			return list_resuls.get(0);
		else
			return null;
	}

	@Override
	public BaseFeedPojo getBaseFeedbyURL(String address) {
		Query hquery = sessionFactory.getCurrentSession()
				.createQuery("from baseFeed fed where fed.feedAddress=:address");
		hquery.setString("address", address);
		List<BaseFeedPojo> list_resuls = hquery.list();
		if (list_resuls != null && list_resuls.size() > 0)
			return list_resuls.get(0);
		else
			return null;
	}

	@Override
	public void delete(Integer feedid) {
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().load(BaseFeedPojo.class, feedid));
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public List<BaseFeedPojo> getbaseFeeds() {
		return sessionFactory.getCurrentSession().createQuery("from baseFeed").list();
	}

	@Override
	public List<BaseFeedPojo> getbaseFeeds(String species) {
		// TODO Auto-generated method stub
		Query hquery = sessionFactory.getCurrentSession()
				.createQuery("from baseFeed fed where fed.feedSpecies=:species");
		hquery.setString("species", species);
		return hquery.list();
	}
}
