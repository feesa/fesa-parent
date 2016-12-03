package com.fs.app.recsys.etl.model;

import java.io.Serializable;

public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698664106664606403L;

	private long id;
	
	private String userid;
	
	private String action;
	
	private String target;
	
	private String timeline;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTimeline() {
		return timeline;
	}

	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}

	@Override
	public String toString() {
		String str="id:"+id;
		str+=",userid:"+userid;
		str+=",action:"+action;
		str+=",target:"+target;
		str+=",timeline:"+timeline;
		return str;
	}
	
}
