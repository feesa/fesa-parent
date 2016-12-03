package com.fs.commons.app.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "portal_userInfo")
public class UserInfoPojo implements java.io.Serializable{
	
	private static final long serialVersionUID = 6380760753913021388L;
	private Integer id;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userPhone;
	private String authLimit;
	private Date regTime;
	private Date creTime;
	private byte[] remark; 
	private int status;
	
	public UserInfoPojo(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "userName", length = 500)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "userPassword", length = 500)
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	@Column(name = "userEmail", length = 500)
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	@Column(name = "regTime")
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	@Column(name = "remark")
	public byte[] getRemark() {
		return remark;
	}

	public void setRemark(byte[] remark) {
		this.remark = remark;
	}
	@Column(name = "authLimit", length = 500)
	public String getAuthLimit() {
		return authLimit;
	}

	public void setAuthLimit(String authLimit) {
		this.authLimit = authLimit;
	}
	@Column(name = "creTime")
	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}
	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		String str="";
		str+="name:"+this.userName.toString()+"password:"+this.userPassword.toString();
		return str;
	}
	@Column(name = "userPhone", length = 500)
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
}
