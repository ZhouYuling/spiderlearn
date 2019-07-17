package com.gzhc365.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBCUtils工具类, 结合配置文件使用，配置文件在resources文件下的druid.properties
 *
 * @author HangGe
 *
 */
public class JDBCUtilsDruid {
	private JDBCUtilsDruid() {
	}

	private static Properties properties = new Properties();
	private static DataSource dataSource;

	// 静态代码块: 给属性赋值, 注册驱动
	static {
		readConfig();

		// 注册驱动
		try {
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//返回连接池对象
	public static DataSource getDataSource() {
		return dataSource;
	}

	// 读取配置文件的信息, 给四个成员变量赋值
	public static void readConfig() {

		try {
			properties.load(JDBCUtilsDruid.class.getClassLoader().getResourceAsStream("druid.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取连接对象的
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 释放资源
	public static void release(ResultSet rs, Statement stat, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null; // GC会优先回收null对象
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stat = null; // GC会优先回收null对象
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null; // GC会优先回收null对象
		}
	}

	// 释放资源
	public static void release(Statement stat, Connection conn) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stat = null; // GC会优先回收null对象
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null; // GC会优先回收null对象
		}
	}
}

