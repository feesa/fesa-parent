package com.fs.app.portal.repository.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.UserReadPojo;
import com.fs.commons.portal.repository.IUserReadRepository;

@Repository
@Transactional
public class UserReadRepository implements IUserReadRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Boolean updateUserRead(UserReadPojo userred) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(userred);
            return true;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserReadPojo> getUserReads(int newsid,int type) {
		try {
			Query hquery = sessionFactory.getCurrentSession().createQuery(
					"from UserReadPojo uread where uread.newsId=:nid and uread.actionType=:atype");
			hquery.setInteger("nid", newsid);
			hquery.setInteger("atype", type);
			List<UserReadPojo> list_result = hquery.list();
			if (list_result != null && list_result.size() > 0)
				return list_result;
			else
				return null;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
}
