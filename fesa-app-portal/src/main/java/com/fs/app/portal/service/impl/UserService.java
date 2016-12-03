package com.fs.app.portal.service.impl;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fs.app.portal.utils.encryptionAnddecryption;
import com.fs.commons.app.entity.RenderData;
import com.fs.commons.app.pojo.UserInfoPojo;
import com.fs.commons.app.pojo.UserPasswordPojo;
import com.fs.commons.portal.repository.IUserInfoRepository;
import com.fs.commons.portal.repository.IUserPasswordRepository;
import com.fs.commons.portal.service.IEmailService;
import com.fs.commons.portal.service.IUserService;

@Service
public class UserService implements IUserService{

	@Autowired
	private IUserInfoRepository userInfoRepository;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private IUserPasswordRepository userPasswordRepository;
	
	@Override
	public UserInfoPojo Login(String uname, String pwd) {
		UserInfoPojo userinfo= userInfoRepository.getUserInfo(uname, pwd);
		return userinfo;
	}

	@Override
	public RenderData Register(String uname, String pwd, String email,String phone) {
		UserInfoPojo userinfo=new UserInfoPojo();
		userinfo.setUserName(uname);
		encryptionAnddecryption jiami = new encryptionAnddecryption();
		SecretKey key = jiami.createSecretKey("DES");//对称加密DES
		String str_password = jiami.encryptToDES(key, pwd);
		userinfo.setUserPassword(str_password);
		userinfo.setRemark(key.getEncoded());
		userinfo.setUserEmail(email);
		userinfo.setUserPhone(phone);
		userinfo.setCreTime(new Date());
		userinfo.setRegTime(new Date());
		userinfo.setAuthLimit("1");
		Boolean res=userInfoRepository.addUser(userinfo);
		emailService.RegisterMessage(email, uname);
		RenderData result=new RenderData(res);
		return result;
	}

	@Override
	public RenderData ExistEmail(String email) {
		UserInfoPojo userinfo= userInfoRepository.getUserEmail(email);
		if(userinfo!=null)
			return new RenderData(true);
		else
			return new RenderData(false);
	}

	@Override
	public RenderData FindPwd(String email) {
		RenderData result=new RenderData();
		try{
			emailService.FindPassWord(email);
			result.setDatas(true);
			result.setMessage("发送成功");
		}catch(Exception ex){
			result.setDatas(false);
			result.setMessage("发送失败");
		}
		return result;
	}
	@Override
	public RenderData verifyExpired(String m, String u){
		RenderData result=new RenderData();
		String url_temp = "http://localhost:9090/portal/resetpwd?m=" + m+ "&u=" + u;
		UserPasswordPojo userpwds_temp = userPasswordRepository.getUserPasswordByUrl(url_temp);
		if (userpwds_temp != null) {
			result.setDatas(true);
		}else{
			result.setDatas(false);
			result.setMessage("修改密码连接已过时，请重新点击发送！");
		}
		return result;
	}
	@Override
	public RenderData ResetPwd(String m, String u,String newPwd) {
		RenderData result=new RenderData();
		String url_temp = "http://localhost:9090/portal/resetpwd?m=" + m+ "&u=" + u;
		UserPasswordPojo userpwds_temp = userPasswordRepository.getUserPasswordByUrl(url_temp);
		if (userpwds_temp != null) {
			try {
				userInfoRepository.changePassword(userpwds_temp.getUserId(), newPwd);
				userPasswordRepository.updatePwd(userpwds_temp.getUserId(), newPwd);
				result.setDatas(true);
			} catch (Exception e) {
				result.setDatas(false);
				result.setMessage("修改密码失败！");
			}
		}else{
			result.setDatas(false);
			result.setMessage("修改密码连接已过时，请重新点击发送！");
		}
		return result;
	}
}
