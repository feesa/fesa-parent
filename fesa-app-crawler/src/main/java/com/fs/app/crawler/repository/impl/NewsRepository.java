package com.fs.app.crawler.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fs.commons.app.pojo.NewsClassifyPojo;
import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.crawler.repository.INewsRepository;

@Repository
@Transactional
public class NewsRepository implements INewsRepository{
	
	private Logger log=LoggerFactory.getLogger(NewsRepository.class);
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveNews(NewsPojo pojo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(pojo);
			session.flush();
			return true;
		} catch (Exception ex) {
			log.error("===saveNews失败:"+ex.getMessage());
		}
		return false;
	}

	public List<NewsPojo> getNewsForPage(int pid,Long timstamp, String type,int cateid) {
		String sql = "";
		if (pid>0&&timstamp>0) {
			if(type.equals("loadmore")){
				sql = "from NewsPojo where category="+cateid+" and timestamp<="+timstamp+" and id !="+pid+" order by timestamp desc";
			}else if(type.equals("loadnew")){
				sql = "from NewsPojo where category="+cateid+" and timestamp>="+timstamp+" and id !="+pid+" order by timestamp desc";
			}else{
				sql = "from NewsPojo where category="+cateid+" order by timestamp desc";
			}
		}else{
			sql="from NewsPojo where category="+cateid+" order by timestamp desc";
		}
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		return query.list();
	}

	public NewsPojo getNewsById(int id) {
		try {
			String sql = "from NewsPojo where id=" + id;
			Session session =sessionFactory.getCurrentSession();
			Query query = session.createQuery(sql);
			List<NewsPojo> result = query.list();
			if (result.size() > 0) {
				return (NewsPojo) result.get(0);
			}
			return null;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	@Override
	public List<NewsClassifyPojo> getNewsCategory() {
		String sql="from NewsClassifyPojo";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		List<NewsClassifyPojo> result = query.list();
		return result;
	}
	
	@Override
	public List<NewsPojo> getNewsForCategory(String category,int pagesize) {
		String cate=category;
		String sql="";
		if(cate==null||cate.equals("")){
			sql="from NewsPojo where category is null";
		}else{
			sql="from NewsPojo where category='"+cate+"'";
		}
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(pagesize);
		List<NewsPojo> result = query.list();
		return result;
	}

	@Override
	public boolean updateNewsCategory(NewsPojo pojo, String category) {
		Session session = this.sessionFactory.getCurrentSession();
		pojo.setCategory(category);
		session.update(pojo);
		return true;
	}
}
