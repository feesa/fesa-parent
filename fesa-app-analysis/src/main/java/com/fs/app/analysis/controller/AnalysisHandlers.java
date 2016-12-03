package com.fs.app.analysis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fs.commons.analysis.service.IClassifyService;

@Controller
public class AnalysisHandlers extends BaseHandlers{

	@Autowired
	private IClassifyService classifyService;
	
	@ResponseBody
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modview=new ModelAndView("/front/main");
		return modview;
	}
	
	@ResponseBody
	@RequestMapping(value = "/classifyPage", method = RequestMethod.GET)
	public void classifyPage(HttpServletRequest request,HttpServletResponse response) {
		int pagesize=Integer.valueOf(request.getParameter("pagesize"));
		boolean result=classifyService.initClassify(pagesize);
		WriteJson(response,result);
	}
}
