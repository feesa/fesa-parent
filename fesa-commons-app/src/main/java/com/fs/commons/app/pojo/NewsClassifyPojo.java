package com.fs.commons.app.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="crawler_news_classify")
public class NewsClassifyPojo implements Serializable{
	private static final long serialVersionUID = 7041587770666413612L;
	private Integer id;
	private Integer classifytype;
	private String classifyname;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="classifytype",length=5)
	public Integer getClassifytype() {
		return classifytype;
	}
	public void setClassifytype(Integer classifytype) {
		this.classifytype = classifytype;
	}
	@Column(name="classifyname",length=20)
	public String getClassifyname() {
		return classifyname;
	}
	public void setClassifyname(String classifyname) {
		this.classifyname = classifyname;
	}
}
