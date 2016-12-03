package com.fs.platform.runtime;

import java.util.ArrayList;
import java.util.List;

public class AspectJoinClass {
	
	private String interFaceName;
	
	private List<String> beforeList = new ArrayList<>();
	
	private List<String> afterList = new ArrayList<>();

	private List<String> throwingList = new ArrayList<>();
	
	private boolean isBefore;
	
	private boolean isAfter;
	
	private boolean isError;
	
	public String getInterFaceName() {
		return interFaceName;
	}

	public void setInterFaceName(String interFaceName) {
		this.interFaceName = interFaceName;
	}

	public List<String> getBeforeList() {
		return beforeList;
	}

	public void setBeforeList(List<String> beforeList) {
		this.beforeList = beforeList;
	}

	public List<String> getAfterList() {
		return afterList;
	}

	public void setAfterList(List<String> afterList) {
		this.afterList = afterList;
	}

	public boolean isBefore() {
		return isBefore;
	}

	public void setBefore(boolean isBefore) {
		this.isBefore = isBefore;
	}

	public boolean isAfter() {
		return isAfter;
	}

	public void setAfter(boolean isAfter) {
		this.isAfter = isAfter;
	}

	public List<String> getThrowingList() {
		return throwingList;
	}

	public void setThrowingList(List<String> throwingList) {
		this.throwingList = throwingList;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}
}
