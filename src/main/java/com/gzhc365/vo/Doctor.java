package com.gzhc365.vo;

public class Doctor {
	
	private int his_id;//医院id
	private int dept_id;//科室表的主键id
	private String doctor_name;//医生名字
	private String code;//医生编码
	private String head_img;//医生头像url
	private String summary;//医生描述
	private String summary_full;//带样式的描述
	private String skill;//医生擅长
	private String level;//医生的职称

	public Doctor() {
	}

	public Doctor(int his_id, int dept_id, String doctor_name, String code, String head_img, String summary, String summary_full, String skill, String level) {
		this.his_id = his_id;
		this.dept_id = dept_id;
		this.doctor_name = doctor_name;
		this.code = code;
		this.head_img = head_img;
		this.summary = summary;
		this.summary_full = summary_full;
		this.skill = skill;
		this.level = level;
	}

	public int getHis_id() {
		return his_id;
	}

	public void setHis_id(int his_id) {
		this.his_id = his_id;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSummary_full() {
		return summary_full;
	}

	public void setSummary_full(String summary_full) {
		this.summary_full = summary_full;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Doctor{" +
				"his_id=" + his_id +
				", dept_id=" + dept_id +
				", doctor_name='" + doctor_name + '\'' +
				", code='" + code + '\'' +
				", head_img='" + head_img + '\'' +
				", summary='" + summary + '\'' +
				", summary_full='" + summary_full + '\'' +
				", skill='" + skill + '\'' +
				", level='" + level + '\'' +
				'}';
	}
}
