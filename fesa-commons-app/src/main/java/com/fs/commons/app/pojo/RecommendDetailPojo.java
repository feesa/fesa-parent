package com.fs.commons.app.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "portal_recommendDetail")
public class RecommendDetailPojo {
	
	private Integer Id;
	private Integer speid;
	private Integer feedid;
	private String feedname;
	private String feedphoto;
	private String remark;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@Column(name = "feedid", length = 500)
	public Integer getFeedid() {
		return feedid;
	}
	public void setFeedid(Integer feedid) {
		this.feedid = feedid;
	}
	@Column(name = "feedname", length = 500)
	public String getFeedname() {
		return feedname;
	}
	public void setFeedname(String feedname) {
		this.feedname = feedname;
	}
	@Column(name = "feedphoto", length = 500)
	public String getFeedphoto() {
		return feedphoto;
	}
	public void setFeedphoto(String feedphoto) {
		this.feedphoto = feedphoto;
	}
	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "speid")
	public Integer getSpeid() {
		return speid;
	}
	public void setSpeid(Integer speid) {
		this.speid = speid;
	}
}
