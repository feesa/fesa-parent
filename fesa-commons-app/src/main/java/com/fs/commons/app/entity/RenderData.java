package com.fs.commons.app.entity;

public class RenderData {

	private int code=200;//相应代码
	private String message="成功";//信息
	private Object datas;//返回数据
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	
	public RenderData(){
		
	}
	
	public RenderData(Object data){
		this.datas=data;
	}
	
	public RenderData(Object data,String msg){
		this.datas=data;
		this.message=msg;
	}
	
	public RenderData(Object data,int cod,String msg){
		this.datas=data;
		this.code=cod;
		this.message=msg;
	}
}
