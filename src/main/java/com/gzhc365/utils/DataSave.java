package com.gzhc365.utils;

import com.gzhc365.vo.Dept;
import com.gzhc365.vo.Doctor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * 通过JdbcTemplate进行数据的增删改查，更加清爽
 */
public class DataSave extends JdbcTemplate {

	public DataSave() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();// 创建C3P0的datasource
		dataSource.setUser("gzhc");//账户
		dataSource.setPassword("MyPass4!");//密码
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/gzhc?characterEncoding=utf-8");//url
		setDataSource(dataSource);
	}

	public void save(Doctor doctor) throws SQLException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		String sql = "insert into t_hc_doctor( his_id,dept_id,doctor_name,code,head_img,summary,summary_full,level,skill) values(?,?,?,?,?,?,?,?,?);";
		update(sql,doctor.getHis_id(),doctor.getDept_id(),doctor.getDoctor_name(),doctor.getCode(),doctor.getHead_img(),doctor.getSummary(),doctor.getSummary_full(),doctor.getLevel(),doctor.getSkill());
	}

	public void save(Dept dept) throws SQLException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		String sql = "insert into t_hc_dept( his_id,dept_name,dept_code,summary,summary_full,has_child,sort_no,pid) values(?,?,?,?,?,?,?,?);";
		update(sql,dept.getHis_id(),dept.getDept_name(),dept.getDept_code(),dept.getSummary(),dept.getSummary_full(),dept.getHas_child(),dept.getSort_no(),dept.getPid());
	}

//	public void save(){
//		String sql = "INSERT INTO `student` (`user_name`, `course`, `score`) VALUES( ?,?,?)";
//		update(sql,"aaa","英文",10);
//	}

}
