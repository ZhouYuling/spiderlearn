package com.gzhc365.vo;

public class Build {
	
	private int his_id;//医院编码
	private String build_name;//建筑物/建筑物楼层名称
	private String build_desp;//建筑物/建筑物楼层说明
	private int par_build_id;//父建筑代码
	private int sort_no;//排序号
	public Build() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Build(int his_id, String build_name, String build_desp, int par_build_id, int sort_no) {
		super();
		this.his_id = his_id;
		this.build_name = build_name;
		this.build_desp = build_desp;
		this.par_build_id = par_build_id;
		this.sort_no = sort_no;
	}
	public int getHis_id() {
		return his_id;
	}
	public void setHis_id(int his_id) {
		this.his_id = his_id;
	}
	public String getBuild_name() {
		return build_name;
	}
	public void setBuild_name(String build_name) {
		this.build_name = build_name;
	}
	public String getBuild_desp() {
		return build_desp;
	}
	public void setBuild_desp(String build_desp) {
		this.build_desp = build_desp;
	}
	public int getPar_build_id() {
		return par_build_id;
	}
	public void setPar_build_id(int par_build_id) {
		this.par_build_id = par_build_id;
	}
	public int getSort_no() {
		return sort_no;
	}
	public void setSort_no(int sort_no) {
		this.sort_no = sort_no;
	}
	@Override
	public String toString() {
		return "Build [his_id=" + his_id + ", build_name=" + build_name + ", build_desp=" + build_desp
				+ ", par_build_id=" + par_build_id + ", sort_no=" + sort_no + ", getHis_id()=" + getHis_id()
				+ ", getBuild_name()=" + getBuild_name() + ", getBuild_desp()=" + getBuild_desp()
				+ ", getPar_build_id()=" + getPar_build_id() + ", getSort_no()=" + getSort_no() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
