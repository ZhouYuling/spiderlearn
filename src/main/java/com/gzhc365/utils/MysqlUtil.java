package com.gzhc365.utils;

import com.gzhc365.vo.Build;
import com.gzhc365.vo.Dept;
import com.gzhc365.vo.Doctor;
import com.gzhc365.vo.His;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description: 通过QueryRunner进行数据的增删改查
 * @Date: Created in 下午 14:26 2019/7/1 0001
 * @Modified by:
 */
public class MysqlUtil {

    //插入数据到t_hc_his中
    public static void insertToHis(His his) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "insert into t_hc_his( his_id,his_name,introduction,introduction_full) values(?,?,?,?);";
        Object[] params = {his.getHis_id(),his.getHis_name(),his.getIntroduction(),his.getIntroduction_full()};
        qr.update(sql,params);
    }

    //插入数据到t_hc_dept中
    public static void insertToDept(Dept dept) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "insert into t_hc_dept( his_id,dept_name,dept_code,summary,summary_full,has_child,sort_no,pid) values(?,?,?,?,?,?,?,?);";
        Object[] params = {dept.getHis_id(),dept.getDept_name(),dept.getDept_code(),dept.getSummary(),dept.getSummary_full(),dept.getHas_child(),dept.getSort_no(),dept.getPid()};
        qr.update(sql,params);
    }

    //插入数据到t_hc_doctor中
    public static void insertToDocter(Doctor doctor) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "insert into t_hc_doctor( his_id,dept_id,doctor_name,code,head_img,summary,summary_full,level,skill) values(?,?,?,?,?,?,?,?,?);";
        Object[] params = {doctor.getHis_id(),doctor.getDept_id(),doctor.getDoctor_name(),doctor.getCode(),doctor.getHead_img(),doctor.getSummary(),doctor.getSummary_full(),doctor.getLevel(),doctor.getSkill()};
        qr.update(sql,params);
    }

    //插入数据到t_hc_his_build
    public static void insertToHisBuild(Build build) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "insert into t_hc_his_build( his_id,build_name,build_desp,par_build_id,sort_no) values(?,?,?,?,?);";
        Object[] params = {build.getHis_id(),build.getBuild_name(),build.getBuild_desp(),build.getPar_build_id(),build.getSort_no()};
        qr.update(sql,params);
    }

    //获取某个医院的医生列表
    public static List<Doctor> getDoctorInfoByHisId(int his_id)throws SQLException{
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "SELECT * FROM t_hc_doctor where his_id = " + his_id;
//        String sql = "SELECT * FROM t_hc_doctor WHERE his_id = 2034 AND skill IS NULL ";
        return qr.query(sql, new BeanListHandler<Doctor>(Doctor.class));
    }

    //获取某个医院的科室列表
    public static List<Dept> getDeptInfoByHisId(int his_id)throws SQLException{
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "SELECT * FROM t_hc_dept WHERE his_id = "+ his_id +" AND summary_full = ''";
        return qr.query(sql, new BeanListHandler<Dept>(Dept.class));
    }

    //更新医生的head_img、一般情况下在上传本地头像到Fast-DFS上使用
    public static int updateDocHeadImg(Doctor doc) throws SQLException {
        //创建queryrunner对象
        QueryRunner queryRunner = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "UPDATE t_hc_doctor SET head_img=? WHERE his_id=? and doctor_name = ? and dept_id = ? and code = ?";
        //创建object数组
        Object[] params = {doc.getHead_img(),doc.getHis_id(),doc.getDoctor_name(),doc.getDept_id(),doc.getCode()};
        return queryRunner.update(sql, params);
    }

    //更新科室summary_full
    public static int updateDeptSummaryFull(Dept dept) throws SQLException {
        //创建queryrunner对象
        QueryRunner queryRunner = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "UPDATE t_hc_dept SET summary_full=? WHERE his_id=? and dept_name = ? and dept_code = ? and sort_no = ?";
        //创建object数组
        Object[] params = {dept.getSummary_full(),dept.getHis_id(),dept.getDept_name(),dept.getDept_code(),dept.getSort_no()};
        return queryRunner.update(sql, params);
    }

    //更新医生skill
    public static int updateDocSkill(Doctor doc) throws SQLException {
        //创建queryrunner对象
        QueryRunner queryRunner = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "UPDATE t_hc_doctor SET skill=? WHERE his_id=? and doctor_name = ? and dept_id = ? and code = ?";
        //创建object数组
        Object[] params = {doc.getSkill(),doc.getHis_id(),doc.getDoctor_name(),doc.getDept_id(),doc.getCode()};
        return queryRunner.update(sql, params);
    }

    /**
     * 自增主键表，自增主键不能放在insert语句中，不然会获得意想不到的值
     * @param tableName 插入数据的表名
     * @return 返回自增主键的数值
     * @throws SQLException
     */
    public static long getInsertPrimaryKey(String tableName) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        Object _ret = qr.query("SELECT LAST_INSERT_ID() FROM" + tableName, new ScalarHandler());
        if (_ret instanceof BigInteger) {//类型转换
            return ((BigInteger) _ret).longValue();
        } else if (_ret instanceof Long) {
            return (Long) _ret;
        } else {
            return ((Integer) _ret).longValue();
        }
    }

}
