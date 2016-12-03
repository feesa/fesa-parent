package com.fs.app.portal.service.impl;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fs.app.portal.utils.encryptionAnddecryption;
import com.fs.commons.app.entity.fsMail;
import com.fs.commons.app.pojo.UserInfoPojo;
import com.fs.commons.app.pojo.UserPasswordPojo;
import com.fs.commons.portal.repository.IUserInfoRepository;
import com.fs.commons.portal.repository.IUserPasswordRepository;
import com.fs.commons.portal.service.IEmailService;

@Service
public class EmailService implements IEmailService{

	@Autowired
	private IUserInfoRepository userInfoRepository;
	@Autowired
	private IUserPasswordRepository userPasswordRepository;
	private String frommail = "feilongyouyuan@sina.com";
	private String name = "feilongyouyuan@sina.com";
	private String password = "3302213fei";
	
	@Override
	public void RegisterMessage(String to_mail, String user_name) {
		StringBuffer sb = new StringBuffer();
		sb.append("亲爱的用户" + user_name + " ：您好！<br><br>");
		sb.append("        欢迎使用FS阅读，收这封电子邮件, 首先感谢你对FS阅读的认可,我们将用最大的能力给你带来舒适的阅读体验。<br><br>");
		sb.append("        你的登录账号为:"+user_name+"<br><br>");
		sb.append("<br><br><br>获取最新资讯，请访问FS主页：<a href='http://localhost:9090/portal/main'>http://localhost:9090/portal/main</a><br>");
		sb.append("访问个人订阅信息,请访问个人中心：<a href='http://localhost:9090/portal/person'>http://localhost:9090/portal/person</a><br>");
		sb.append("获取更多的订阅信息,请访问：<a href='http://localhost:9090/portal/feedmanage'>http://localhost:9090/portal/feedmanage</a><br>");
		sb.append("下载手机客户端，请访问移动App：<a href='http://localhost:9090/portal/download'>http://localhost:9090/portal/download</a><br>");
		sb.append("联系我们,洽谈合作请访问：<a href='http://localhost:9090/portal/about'>http://localhost:9090/portal/about</a><br>");
		sb.append("<br><br><br>我们将一如既往、热忱的为您服务！");
		sb.append("<br><br>http://localhost:9090/portal/ - 打造最好的阅读平台！");
		sb.append("<br>用户服务支持：<a href='mailto:feilongyouyuan@sina.com'>feilongyouyuan@sina.com</a><br><br><br>");
		// 创建邮件
		fsMail mail = new fsMail();
		mail.setTo(to_mail);
		mail.setFrom(frommail);// 你的邮箱 feilongyouyuan@sina.com
		mail.setHost("smtp.sina.com.cn");
		mail.setUsername(name);// 用户 feilongyouyuan@sina.com
		mail.setPassword(password);
		mail.setSubject("[FS阅读] 欢迎使用FS阅读");
		mail.setContent(sb.toString());
		mail.sendMail();
	}

	@Override
	public void FindPassWord(String to_mail) {
		UserInfoPojo uinfo=userInfoRepository.getUserEmail(to_mail);
		String user_name=uinfo.getUserName();
		UserPasswordPojo upassword = new UserPasswordPojo();
		upassword.setUserId(uinfo.getId());
		encryptionAnddecryption jiami = new encryptionAnddecryption();
		SecretKey key = jiami.createSecretKey("DES");
		String str_mail = jiami.encryptToDES(key, to_mail);
		String str_uid = jiami.encryptToDES(key, uinfo.getId().toString());
		upassword.setMark("http://localhost:9090/portal/resetpwd?m="
				+ str_mail + "&u=" + str_uid);
		byte[] keybyt = key.getEncoded();
		upassword.setEncryKey(keybyt);
		upassword.setStatus("1");
		upassword.setOldPassword(uinfo.getUserPassword());
		upassword.setAddTime(new Date());
		upassword.setUpdateTime(null);
		userPasswordRepository.addPassword(upassword);
		//开始发送邮件
		StringBuffer sb = new StringBuffer();
		sb.append("亲爱的用户" + user_name + " ：您好！<br><br>");
		sb.append("        您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。假如这不是您本人所申请, 请不用理会<br>这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。<br><br>");
		sb.append("        要使用新的密码, 请使用以下链接启用密码。<br><br>");
		sb.append("        <a href='" + upassword.getMark() + "'>" + upassword.getMark() + "</a>");
		sb.append("<br><br><br>获取最新资讯，请访问FS主页：<a href='http://localhost:9090/portal/main'>http://localhost:9090/portal/main</a><br>");
		sb.append("访问个人订阅信息,请访问个人中心：<a href='http://localhost:9090/portal/person'>http://localhost:9090/portal/person</a><br>");
		sb.append("获取更多的订阅信息,请访问：<a href='http://localhost:9090/portal/feedmanage'>http://localhost:9090/portal/feedmanage</a><br>");
		sb.append("下载手机客户端，请访问移动App：<a href='http://localhost:9090/portal/download'>http://localhost:9090/portal/download</a><br>");
		sb.append("联系我们,洽谈合作请访问：<a href='http://localhost:9090/portal/about'>http://localhost:9090/portal/about</a><br>");
		sb.append("<br><br><br>我们将一如既往、热忱的为您服务！");
		sb.append("<br><br>http://localhost:9090/portal/ - 打造最好的阅读平台！");
		sb.append("<br>用户服务支持：<a href='mailto:feilongyouyuan@sina.com'>feilongyouyuan@sina.com</a><br><br><br>");
		// 创建邮件
		fsMail mail = new fsMail();
		mail.setTo(to_mail);
		mail.setFrom(frommail);// 你的邮箱 feilongyouyuan@sina.com
		mail.setHost("smtp.sina.com.cn");
		mail.setUsername(name);// 用户 feilongyouyuan@sina.com
		mail.setPassword(password);
		mail.setSubject("[FS阅读]找回您的账户密码");
		mail.setContent(sb.toString());
		mail.sendMail();
	}
}
