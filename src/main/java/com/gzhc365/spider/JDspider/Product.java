package com.gzhc365.spider.JDspider;

import java.util.ArrayList;

/**
 * 想要什么数据，
 * @author maoxiangyi
 *
 */
public class Product {
	
	private String pid;//商品编号
	private String title;//标题
	private String price;//价格
	private String url;//商品
	private ArrayList<String> imgUrls;//图片
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<String> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(ArrayList<String> imgUrls) {
		this.imgUrls = imgUrls;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", title=" + title + ", price=" + price + ", url=" + url + ", imgUrls=" + imgUrls
				+ "]";
	}

}
