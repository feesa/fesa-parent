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
import com.fs.commons.app.pojo.UserInfoPojo;
import com.fs.commons.portal.service.IUserService;

@Controller
@RequestMapping("/api/users")
public class UserApiHandlers extends BaseHandlers{

	@Autowired
	private IUserService userService;
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Object username=request.getParameter("username");
		Object password=request.getParameter("password");
		ValidParam(response,username,password);
		UserInfoPojo userinfo= userService.Login(username.toString(), password.toString());
		RenderData result=new RenderData(userinfo);
		WriteJson(response,result);
	}
}
