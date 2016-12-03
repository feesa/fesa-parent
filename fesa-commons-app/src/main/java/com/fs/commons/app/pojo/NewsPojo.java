package com.fs.commons.app.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="crawler_news",uniqueConstraints={@UniqueConstraint(columnNames={"url","title"})})
public class NewsPojo implements Serializable{
	private static final long serialVersionUID = 1351243388774465776L;
	private Integer id;
	private Long timestamp;
	private String time;
	private String image;
	private String title;
	private String context;
	private String source;
	private String url;
	private String category;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="timestamp",length=30)
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	@Column(name="image",length=300)
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column(name="time",length=64)
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Column(name="title",length=64)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="context",columnDefinition="text")
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@Column(name="url",length=300)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="source",length=100)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Column(name="category",length=100)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("id:"+(id==null?"":id));
		sb.append("image:"+this.image);
		sb.append("source:"+this.source);
		sb.append("url:"+this.url);
		sb.append("time:"+this.time);
		sb.append("title:"+this.title);
		sb.append("timestamp:"+this.timestamp.toString());
		return sb.toString();
	}
}
