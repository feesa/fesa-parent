package com.fs.app.portal.repository.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fs.commons.app.pojo.UserCommentPojo;
import com.fs.commons.portal.repository.IUserCommentRepository;

@Repository
@Transactional
public class UserCommentRepository implements IUserCommentRepository {
	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	public Boolean updateUserComment(UserCommentPojo usercomment) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(usercomment);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserCommentPojo> getUserComments(int newsid) {
		try{
			Query hquery= sessionFactory.getCurrentSession().createQuery("from UserCommentPojo ucomment where ucomment.newsId=:nid");
			hquery.setInteger("nid", newsid);
			List<UserCommentPojo> list_result= hquery.list();
			if(list_result!=null&&list_result.size()>0)
				return list_result;
			else
			   return null;
		}catch(Exception ex){
			System.out.println(ex.toString());
			return null;
		}
	}
}
