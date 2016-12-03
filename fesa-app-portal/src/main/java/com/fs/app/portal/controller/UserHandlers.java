package com.fs.app.portal.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fs.commons.app.entity.RenderData;
import com.fs.commons.app.pojo.UserInfoPojo;
import com.fs.commons.portal.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserHandlers extends BaseHandlers{

	@Autowired
	private IUserService userService;
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response){
		Object username=request.getParameter("username");
		Object password=request.getParameter("password");
		if(username==null||password==null){
			RenderData result=new RenderData("登录异常！");
			WriteErrorJson(response,result);
			return;
		}
		UserInfoPojo userinfo= userService.Login(username.toString(), password.toString());
		if(userinfo!=null){
			request.getSession().setMaxInactiveInterval(900);
			request.getSession().setAttribute("user", userinfo);
			Cookie[] reqcookies=request.getCookies();
			Boolean existcookies=false;
			for (Cookie cookie : reqcookies) {
				if(cookie.getName().toString().equals("feisa")){
					existcookies=true;
				}
			}
			if (!existcookies) {
				Cookie userCookie = new Cookie("feisa", username+"^"+password);
				userCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(userCookie);
			}
		}
		RenderData result=new RenderData((userinfo==null?false:true));
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		request.getSession().invalidate();
		Cookie userCookie = new Cookie("feisa","");
		userCookie.setMaxAge(0);
		response.addCookie(userCookie);
		RenderData result=new RenderData(true);
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/existemail",method=RequestMethod.POST)
	public void existemail(HttpServletRequest request, HttpServletResponse response){
		String email=request.getParameter("email").toString();
		RenderData resdata= userService.ExistEmail(email);
		WriteJson(response,resdata);
	}
	
	@ResponseBody
	@RequestMapping(value="/findpwd",method=RequestMethod.POST)
	public void findpwd(HttpServletRequest request, HttpServletResponse response){
		String email=request.getParameter("tomail").toString();
		RenderData result= userService.FindPwd(email);
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/changepwd",method=RequestMethod.POST)
	public void changepwd(HttpServletRequest request, HttpServletResponse response){
		String m=request.getParameter("m").toString();
		String u=request.getParameter("u").toString();
		String newPwd=request.getParameter("newPwd").toString();
		RenderData result= userService.ResetPwd(m, u,newPwd);
		WriteJson(response,result);
	}
	
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public void register(HttpServletRequest request, HttpServletResponse response){
		String username=request.getParameter("username").toString();
		String password=request.getParameter("password").toString();
		String email=request.getParameter("email").toString();
		String phone=request.getParameter("phone").toString();
		RenderData resdata= userService.Register(username, password, email, phone);
		WriteJson(response,resdata);
	}
}
