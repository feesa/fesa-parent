package com.fs.app.portal.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fs.app.portal.controller.BaseHandlers;
import com.fs.commons.app.entity.RenderData;
import com.fs.commons.portal.service.INewsService;

@Controller
@RequestMapping("/api/news")
public class NewsApiHandlers extends BaseHandlers{

	@Autowired
	private INewsService newsService;
	
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
}
