package com.fs.commons.portal.service;

import com.fs.commons.app.entity.RenderData;
import com.fs.commons.app.pojo.UserInfoPojo;

public interface IUserService {

	public UserInfoPojo Login(String uname,String pwd);
	
	public RenderData Register(String uname,String pwd,String email,String phone);
	
	public RenderData ExistEmail(String email);
	
	public RenderData FindPwd(String email);
	
	public RenderData verifyExpired(String m, String u);
	
	public RenderData ResetPwd(String m,String u,String newPwd);
}
