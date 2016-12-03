package com.fs.app.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fs.commons.app.entity.RenderData;
import com.fs.commons.app.pojo.NewsClassifyPojo;
import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.portal.service.INewsService;

@Controller
@RequestMapping("/news")
public class NewsHandlers extends BaseHandlers{

	@Autowired
	private INewsService newsService;
	@Reference(timeout=5000)
	private com.fs.commons.crawler.service.INewsService crawlerNewsService;
	
	@ResponseBody
	@RequestMapping(value="/updateNewsRead",method=RequestMethod.POST)
	public void updateNewsRead(HttpServletRequest request, HttpServletResponse response){
		Object val1=request.getParameter("newsid");
		Object val2=request.getParameter("userid");
		Object val3=request.getParameter("typeid");
		ValidParam(response, val1,val2,val3);
		int newsid=Integer.valueOf(val1.toString());
		int userid=Integer.valueOf(val2.toString());
		int typeid=Integer.valueOf(val3.toString());
		RenderData result=newsService.updateNewsRead(userid, newsid, typeid);
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/getNewsRead",method=RequestMethod.GET)
	public void getNewsRead(HttpServletRequest request, HttpServletResponse response){
		Object val1=request.getParameter("newsid");
		Object val2=request.getParameter("typeid");
		ValidParam(response, val1,val2);
		int newsid=Integer.valueOf(val1.toString());
		int typeid=Integer.valueOf(val2.toString()); 
		RenderData result=newsService.getReadByNewId(newsid, typeid);
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/updateNewsComment",method=RequestMethod.POST)
	public void getTopBaseSpeceies(HttpServletRequest request, HttpServletResponse response){
		Object val1=request.getParameter("newsid");
		Object val2=request.getParameter("userid");
		Object val3=request.getParameter("commentInfo");
		ValidParam(response, val1,val2,val3);
		int newsid=Integer.valueOf(val1.toString());
		int userid=Integer.valueOf(val2.toString());
		String commentInfo=val3.toString();
		RenderData result=newsService.updateNewComment(userid, newsid, commentInfo);
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/getNewsComment",method=RequestMethod.GET)
	public void getFeedDetailLimit(HttpServletRequest request, HttpServletResponse response){
		Object val1=request.getParameter("newsid");
		ValidParam(response, val1);
		int newsid=Integer.valueOf(val1.toString()); 
		RenderData result=newsService.getCommentByNewId(newsid);
		WriteJson(response,result);
	}
	@ResponseBody
	@RequestMapping(value="/getNewsCategory",method=RequestMethod.GET)
	public void getNewsCategory(HttpServletRequest request, HttpServletResponse response){
		List<NewsClassifyPojo> result=crawlerNewsService.getNewsCategory();
		WriteJson(response,result);
	}
	@ResponseBody
	@RequestMapping(value="/getNewsDataForPage",method=RequestMethod.GET)
	public void getNewsDataForPage(HttpServletRequest request, HttpServletResponse response){
		int pid=Integer.valueOf(request.getParameter("pid"));
		long timestamp = Long.valueOf(request.getParameter("ptimestamp"));
		String type = request.getParameter("ptype");
		int cateid=Integer.valueOf(request.getParameter("cateid"));
		List<NewsPojo> result =crawlerNewsService.getNewsForPage(pid,timestamp,type,cateid);
		WriteJson(response,result);
	}
	@ResponseBody
	@RequestMapping(value = "/getNewsById", method = RequestMethod.GET)
	public void getNewsById(HttpServletRequest request,
			HttpServletResponse response) {
		int pid = Integer.valueOf(request.getParameter("pid"));
		NewsPojo result = crawlerNewsService.getNewsById(pid);
		WriteJson(response, result);
	}
}
