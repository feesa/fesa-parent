package com.fs.commons.app.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "portal_recommendSpecies")
public class RecommendSpeciesPojo implements java.io.Serializable{

	private static final long serialVersionUID = 119258490817070298L;
	private Integer Id;
	private String name;
	private String icon;
	private Boolean restatus;
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
	@Column(name = "name", length = 500)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "icon", length = 500)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name = "restatus")
	public Boolean getRestatus() {
		return restatus;
	}

	public void setRestatus(Boolean restatus) {
		this.restatus = restatus;
	}
	@Column(name = "remark",length=500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
