package com.gzhc365.spider.JDspider;

import com.gzhc365.utils.JavaSpider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;

public class JDProductMaster {
	private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

	public static void main(String[] args) {
		// 1.准备url
		String indexUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E6%89%8B%E6%9C%BA&pvid=aa9a99bb0895488197c2a663d777a51b";
		try {

			String html = JavaSpider.getHtmlUTF8(indexUrl);
			// 2.获取首页的信息
//			HttpGet httpGet = new HttpGet(indexUrl);	//不加User-Agent会导致系统发现是爬虫，从而导致爬取失败
//			String html = Utils.getHtml(httpGet);
//			System.out.println(html);
			// 3.解析首页 此处不需要返回值，直接在方法中调用redis的jedis的客户端
			parseHtml(html);
		} catch (Exception e) {
			System.out.println("首页访问失败！" + indexUrl);
			System.out.println("错误信息" +  e);
		}
		// 4、做分页请求
		int page = 1;
		for (int num = 2; num <= 100; num++) {
			System.out.println( "num:"  + num);
			page = (2 * num) - 1;
			String pagingUrl="https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655&click=0&page="+page;
			try {
//				HttpGet httpGet = new HttpGet(pagingUrl);
//				String pagingHtml = Utils.getHtml(httpGet);
				String pagingHtml = JavaSpider.getHtmlUTF8(pagingUrl);
				parseHtml(pagingHtml);
			} catch (Exception e) {
				System.out.println("请求分页失败，分页编号是:"+page);
				System.out.println("错误信息："+e);
			}
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 解析搜索页的首页时，需要获取到pid
	 * 
	 * @param html
	 */
	public static void parseHtml(String html) {
		if (html != null) {
			Document doc = Jsoup.parse(html);
			Elements eles = doc.select("[data-sku]");
			for (Element element : eles) {
				jedis.lpush("gzhc_spider_jd_sku", element.attr("data-sku"));
			}
		}
	}


}
