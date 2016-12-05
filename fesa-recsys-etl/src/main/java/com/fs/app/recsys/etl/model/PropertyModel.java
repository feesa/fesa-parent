package com.fs.app.recsys.etl.model;

import java.io.Serializable;

public class PropertyModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1944537315289280641L;

	private int id;
	
	private String itemid;
	
	private String featureid;
	
	private String featurevalue;
	
	private String timeline;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getFeatureid() {
		return featureid;
	}

	public void setFeatureid(String featureid) {
		this.featureid = featureid;
	}

	public String getFeaturevalue() {
		return featurevalue;
	}

	public void setFeaturevalue(String featurevalue) {
		this.featurevalue = featurevalue;
	}

	public String getTimeline() {
		return timeline;
	}

	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}
}
