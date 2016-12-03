package com.fs.commons.portal.service;

public interface IEmailService {

	public void RegisterMessage(String to_mail,String user_name);
	
	public void FindPassWord(String to_mail);
}
