package com.gzhc365.vo;

public class Dept {
	
	private int his_id;//医院id
	private String dept_name;//科室名称
	private String dept_code;//科室编码
	private String summary;//科室介绍
	private String summary_full;//带样式的科室介绍
	private int has_child;//'0：非最底层(说明它有子科室)，1：没有子科室',
	private int sort_no;//从小到大排序、在展示的时候使用
	private int pid;//父id '0：最顶层(说明它无父科室)'

	public Dept() {
	}

	public int getHis_id() {
		return his_id;
	}

	public void setHis_id(int his_id) {
		this.his_id = his_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
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

	public int getHas_child() {
		return has_child;
	}

	public void setHas_child(int has_child) {
		this.has_child = has_child;
	}

	public int getSort_no() {
		return sort_no;
	}

	public void setSort_no(int sort_no) {
		this.sort_no = sort_no;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public Dept(int his_id, String dept_name, String dept_code, String summary, String summary_full, int has_child, int sort_no, int pid) {
		this.his_id = his_id;

		this.dept_name = dept_name;
		this.dept_code = dept_code;
		this.summary = summary;
		this.summary_full = summary_full;
		this.has_child = has_child;
		this.sort_no = sort_no;
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Dept{" +
				"his_id=" + his_id +
				", dept_name='" + dept_name + '\'' +
				", dept_code='" + dept_code + '\'' +
				", summary='" + summary + '\'' +
				", summary_full='" + summary_full + '\'' +
				", has_child=" + has_child +
				", sort_no=" + sort_no +
				", pid=" + pid +
				'}';
	}
}
