package com.gzhc365.spider.JDspider;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ProductDao extends JdbcTemplate {

	public ProductDao() {
		// 创建C3P0的datasource 1.配置 2.代码
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// 1.url
		// 2.driver
		// 3.username&password
		dataSource.setUser("gzhc");
		dataSource.setPassword("MyPass4!");
		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/gzhc?characterEncoding=utf-8");
		setDataSource(dataSource);
	}

	public void save(Product product) {
		String sql = "INSERT INTO `gzhc`.`jd_product` (`pid`, `title`, `price`, `url`)VALUES(?,?,?,?)";
		int update = update(sql, product.getPid(), product.getTitle(), product.getPrice(), product.getUrl());
		System.out.println("update:" + update + " " + product.toString());
	}
}
