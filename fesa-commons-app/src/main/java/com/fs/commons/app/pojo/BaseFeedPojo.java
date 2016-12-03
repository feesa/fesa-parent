package com.fs.commons.app.pojo;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name = "portal_baseFeed")
public class BaseFeedPojo implements java.io.Serializable {

	private static final long serialVersionUID = 2384402481040629426L;
	private Integer Id;
	private String feedSpecies;
	private String feedName;
	private String feedAddress;
	private String feedDescription;
	private String feedPhoto;
	private Boolean feedStatus;
	private Integer feedCount;
	private String feedRemark;
	
	public BaseFeedPojo(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		this.Id = id;
	}

	@Column(name = "feedSpecies", length = 500)
	public String getFeedSpecies() {
		return feedSpecies;
	}

	public void setFeedSpecies(String feedSpecies) {
		this.feedSpecies = feedSpecies;
	}
	@Column(name = "feedName", length = 500)
	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}
	@Column(name = "feedAddress", length = 500)
	public String getFeedAddress() {
		return feedAddress;
	}

	public void setFeedAddress(String feedAddress) {
		this.feedAddress = feedAddress;
	}
	@Column(name = "feedDescription", length = 500)
	public String getFeedDescription() {
		return feedDescription;
	}

	public void setFeedDescription(String feedDescription) {
		this.feedDescription = feedDescription;
	}
	@Column(name = "feedCount")
	public Integer getFeedCount() {
		return feedCount;
	}

	public void setFeedCount(Integer feedCount) {
		this.feedCount = feedCount;
	}
	@Column(name = "feedRemark", length = 500)
	public String getFeedRemark() {
		return feedRemark;
	}

	public void setFeedRemark(String feedRemark) {
		this.feedRemark = feedRemark;
	}
	@Column(name = "feedPhoto", length = 500)
	public String getFeedPhoto() {
		return feedPhoto;
	}

	public void setFeedPhoto(String feedPhoto) {
		this.feedPhoto = feedPhoto;
	}
	@Column(name = "feedStatus")
	public Boolean getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(Boolean feedStatus) {
		this.feedStatus = feedStatus;
	}
}
