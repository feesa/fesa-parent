package com.fs.app.crawler.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.crawler.service.INewsService;

@Controller
@RequestMapping("/news")
public class NewsHandlers extends BaseHandlers{

	@Autowired
	private INewsService newsService;
	
	@ResponseBody
	@RequestMapping(value = "/pushNewsBywilddog", method = RequestMethod.POST)
	public void pushNewsBywilddog(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("======pushNewsBywilddog========");
		Map<String, String[]> params = request.getParameterMap();
		newsService.pushNewsBywilddog(params);
		WriteJson(response,"成功");
	}

	@ResponseBody
	@RequestMapping(value = "/pushNewsByNative", method = RequestMethod.POST)
	public void pushNewsByNative(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("======pushNewsByNative========");
		Map<String, String[]> params = request.getParameterMap();
		newsService.pushNewsByNative(params);
		WriteJson(response,"成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNewsDataForPage", method = RequestMethod.GET)
	public void getNewsDataForPage(HttpServletRequest request,
			HttpServletResponse response) {
		int pid=Integer.valueOf(request.getParameter("pid"));
		long timestamp = Long.valueOf(request.getParameter("ptimestamp"));
		String type = request.getParameter("ptype");
		int cateid=Integer.valueOf(request.getParameter("cateid"));
		List<NewsPojo> result = newsService.getNewsForPage(pid,timestamp,type,cateid);
		WriteJson(response,result);
	}

	@ResponseBody
	@RequestMapping(value = "/getNewsById", method = RequestMethod.GET)
	public void getNewsById(HttpServletRequest request,
			HttpServletResponse response) {
		int pid = Integer.valueOf(request.getParameter("pid"));
		NewsPojo result = newsService.getNewsById(pid);
		WriteJson(response, result);
	}
}
