package com.fs.app.recsys.etl.model;

import java.io.Serializable;

public class RelationModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698664106664606403L;

	private long id;
	
	private String sponsorid;
	
	private String relation;
	
	private String itemid;
	
	private String timeline;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSponsorid() {
		return sponsorid;
	}

	public void setSponsorid(String sponsorid) {
		this.sponsorid = sponsorid;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
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
		str+=",sponsorid:"+sponsorid;
		str+=",relation:"+relation;
		str+=",itemid:"+itemid;
		str+=",timeline:"+timeline;
		return str;
	}
	
}
