package com.fs.commons.app.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "portal_userPassword")
public class UserPasswordPojo implements java.io.Serializable{
	
	private static final long serialVersionUID = 2176965408058037431L;
	private Integer Id;
	private Integer userId;
	private String mark;
	private byte[] encryKey;
	private String status;
	private String oldPassword;
	private Date addTime; 
	private Date updateTime;
	public UserPasswordPojo(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "mark",length=500)
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Column(name = "encryKey")
	public byte[] getEncryKey() {
		return encryKey;
	}
	public void setEncryKey(byte[] encryKey) {
		this.encryKey = encryKey;
	}
	@Column(name = "status",length=10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "oldPassword",length=100)
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	@Column(name = "addTime")
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
