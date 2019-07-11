package com.gzhc365.vo;

public class His {
	
	private int his_id;//医院id
	private String his_name;//医院名称
	private String introduction;//医院介绍
	private String introduction_full;//带样式的医院介绍
	
	public His() {
		super();
		// TODO Auto-generated constructor stub
	}
	public His(int his_id, String his_name, String introduction, String introduction_full) {
		super();
		this.his_id = his_id;
		this.his_name = his_name;
		this.introduction = introduction;
		this.introduction_full = introduction_full;
	}
	public int getHis_id() {
		return his_id;
	}
	public void setHis_id(int his_id) {
		this.his_id = his_id;
	}
	public String getHis_name() {
		return his_name;
	}
	public void setHis_name(String his_name) {
		this.his_name = his_name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getIntroduction_full() {
		return introduction_full;
	}
	public void setIntroduction_full(String introduction_full) {
		this.introduction_full = introduction_full;
	}
	@Override
	public String toString() {
		return "His [his_id=" + his_id + ", his_name=" + his_name + ", introduction=" + introduction
				+ ", introduction_full=" + introduction_full + ", getHis_id()=" + getHis_id() + ", getHis_name()="
				+ getHis_name() + ", getIntroduction()=" + getIntroduction() + ", getIntroduction_full()="
				+ getIntroduction_full() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	

}
