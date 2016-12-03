package com.fs.app.portal.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.fs.commons.app.entity.RenderData;
import com.fs.commons.portal.service.IUserService;

@Controller
public class HomeHandlers extends BaseHandlers{

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public ModelAndView homepage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modview=new ModelAndView("/front/main");
		modview.addObject("crawler", getConfigValue("fesa.app.crawler.url"));
		return modview;
	}
	
	@RequestMapping(value="/person",method=RequestMethod.GET)
	public String person(HttpServletRequest request, HttpServletResponse response){
		return "/front/person";
	}
	
	@RequestMapping(value="/feedmanage",method=RequestMethod.GET)
	public String feedmanage(HttpServletRequest request, HttpServletResponse response){
		return "/front/feedmanage";
	}
	
	@RequestMapping(value="/reader",method=RequestMethod.GET)
	public String reader(HttpServletRequest request, HttpServletResponse response){
		return "/front/reader";
	}
	
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public String download(HttpServletRequest request, HttpServletResponse response){
		return "/front/download";
	}
	
	@RequestMapping(value="/about",method=RequestMethod.GET)
	public String description(HttpServletRequest request, HttpServletResponse response){
		return "/front/about";
	}
	
	//站点相关
	@RequestMapping(value="/newdetail",method=RequestMethod.GET)
	public ModelAndView feeddescription(HttpServletRequest request, HttpServletResponse response){
		String newid=request.getParameter("newid");
		ModelAndView modview=new ModelAndView("/front/newdetail");
		modview.addObject("crawler", getConfigValue("fesa.app.crawler.url"));
		modview.addObject("newid", newid);
		return modview;
	}
	
	//登陆相关
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response){
		return "/front/login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(HttpServletRequest request, HttpServletResponse response){
		return "/front/register";
	}
	
	@RequestMapping(value="/missingpwd",method=RequestMethod.GET)
	public String missingpwd(HttpServletRequest request, HttpServletResponse response){
		return "/front/missingpwd";
	}
	
	@RequestMapping(value="/resetpwd",method=RequestMethod.GET)
	public ModelAndView resetpwd(HttpServletRequest request, HttpServletResponse response){
		String para_m=request.getParameter("m").toString();
		String para_u=request.getParameter("u").toString();
		RenderData rendata= userService.verifyExpired(para_m,para_u);
		Map<String,Object> data = new HashMap<String,Object>();
		if(Boolean.valueOf(rendata.getDatas().toString())){
			data.put("m", para_m);
			data.put("u", para_u);
			return new ModelAndView("/front/resetpwd", data);
		}else{
			data.put("message", rendata.getMessage());
			return new ModelAndView("/front/error", data);
		}
	}
	
	//后台配置
	@RequestMapping(value="/feedlist",method=RequestMethod.GET)
	public String feedlist(HttpServletRequest request, HttpServletResponse response){
		return "/page/feedlist";
	}

	@RequestMapping(value="/addfeed",method=RequestMethod.GET)
	public String addfeed(HttpServletRequest request, HttpServletResponse response){
		return "/page/addfeed";
	}
	
	@RequestMapping(value="/editfeed",method=RequestMethod.GET)
	public String editfeed(HttpServletRequest request, HttpServletResponse response){
		return "/page/editfeed";
	}
	
	@RequestMapping(value="/recommend",method=RequestMethod.GET)
	public String recommend(HttpServletRequest request, HttpServletResponse response){
		return "/page/recommend";
	}
}
