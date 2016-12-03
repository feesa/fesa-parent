package com.fs.app.crawler.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fs.app.crawler.controller.BaseHandlers;
import com.fs.commons.app.pojo.NewsPojo;
import com.fs.commons.crawler.service.INewsService;

@Controller
@RequestMapping("/api/news")
public class NewsApiHandlers extends BaseHandlers{
	
	@Autowired
	private INewsService newsService;
	
	@ResponseBody
	@RequestMapping(value = "/getNewsDataForPage", method = RequestMethod.GET)
	public void getNewsDataForPage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String[]> params = request.getParameterMap();
		int pid=0;
		Long timestamp = (long) 0;
		String type = "";
		int cateid=0;
		String callback = "";
		for (String ele : params.keySet()) {
			String[] val = params.get(ele);
			if (ele.equals("ptimestamp"))
				timestamp = Long.valueOf(val[0]);
			else if (ele.equals("ptype"))
				type = String.valueOf(val[0]);
			else if(ele.equals("pid"))
				pid=Integer.valueOf(val[0]);
			else if(ele.equals("cateid"))
				cateid=Integer.valueOf(val[0]);
			else if (ele.equals("callback"))
				callback = String.valueOf(val[0]);
		}
		List<NewsPojo> result = newsService.getNewsForPage(pid,timestamp,type,cateid);
		WriteJsonWithCallBack(response,callback,result);
	}

	@ResponseBody
	@RequestMapping(value = "/getNewsById", method = RequestMethod.GET)
	public void getNewsById(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String[]> params = request.getParameterMap();
		int pid = 0;
		String callback = "";
		for (String ele : params.keySet()) {
			String[] val = params.get(ele);
			if (ele.equals("pid"))
				pid = Integer.valueOf(val[0]);
			else if (ele.equals("callback"))
				callback = String.valueOf(val[0]);
		}
		NewsPojo result = newsService.getNewsById(pid);
		WriteJsonWithCallBack(response, callback, result);
	}
}
