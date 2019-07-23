package com.gzhc365.spider.JDspider;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JDProductSlave {
	private static ProductDao productDao =new ProductDao();
	public static void main(String[] args) {
		//1.创建一个线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		//2.提交多个线程任务到线程池中
		for (int i = 0; i < 1; i++) {
			threadPool.execute(new Runnable() {
				public void run() {
					Jedis jedis = new Jedis("127.0.0.1",6379);
					//直获取数据，解析
					while(true){
						//4.从redis中获取一个pid
						String pid = jedis.rpop("gzhc_spider_jd_sku");
						Boolean sismember = jedis.sismember("gzhc_spider_jd_pidset", pid);
						//5.组装成个url

						if(!sismember){
							String url = "https://item.jd.com/"+pid+".html";
							try {
								//6.发起一个http请求
								HttpGet httpGet = new HttpGet(url);
								//7.得到一个返回值
								String html = Utils.getHtml(httpGet);
//								JavaSpider.getHtmlUTF8(url);
								//8.解析
								Product product = parseDetail(html);
								product.setPid(pid);
								product.setUrl(url);
								//8.1----解析价格
								String priceUrl ="https://p.3.cn/prices/mgets?callback=jQuery2695111&type=1&area=1&pdtk=&pduid=150486508323084592877&pdpin=&pin=null&pdbp=0&ext=11000000&source=item-pc&skuIds=J_";
								HttpGet priceHttpGet = new HttpGet(priceUrl+pid);
								String priceData = Utils.getHtml(priceHttpGet);
								int startIndex = priceData.indexOf("[{");
								int endIndex = priceData.indexOf("]);");
								priceData= priceData.substring(startIndex+1, endIndex);

								//使用Gson解析json传
								Gson gson = new Gson();
								HashMap map = gson.fromJson(priceData, HashMap.class);
								product.setPrice((String) map.get("p"));

								//9.保存到数据库
								//---------------保存数据-----------
								productDao.save(product);
								//--------将商品对象，存放到redis中。

								//10.将爬取过的url存到到redis的set容器中
								System.out.println(product);
								jedis.sadd("gzhc_spider_jd_pidset", pid);

							} catch (Exception e) {
								System.out.println("slave访问商品详情页失败"+pid);
								System.out.println(e);
							}
						}
					}
				}
			});
		}
	}

	/**
	 * 解析商品详情页，得到一个商品对象
	 * @param html
	 * @return
	 */
	public static Product parseDetail(String html) {
		Product product = new Product();
		if(html!=null){
			Document doc = Jsoup.parse(html);
//			//标题
			String title =doc.select("div.sku-name").get(0).ownText();
			product.setTitle(title);
			
			//获取价格 价格是一个动态的，需要单独的接口进行访问
			ArrayList<String> imgList = new  ArrayList<String>();
			//获取轮播图片
			Elements eles = doc.select("script[charset=gbk]");
			for (Element element : eles) {
				String text = element.toString();
				int imgIndex = text.indexOf("imageList");
				int endIndex = text.indexOf("cat");
				endIndex = endIndex-6;
				String imgs = text.substring(imgIndex+13,endIndex);
				//imageList: ["jfs/t5815/57/1307544515/161558/a4577c0f/592536afN51878437.jpg"
//				,"jfs/t5698/177/1314573205/100804/5310986/592536b4N68b02458.jpg"
//				,"jfs/t5917/88/146435037/125441/ef9a8d3c/592536b8Nee463a6a.jpg","jfs/t5911/105/135621496/82361/3d8ff7c1/592536bdNeea3b55a.jpg","jfs/t5677/305/1297228629/116225/22f696f8/592536c1Nf81817ce.jpg","jfs/t5878/98/1320837698/78028/2b5c4c19/592536c5Ne25cee5c.jpg"
//				,"jfs/t5845/75/1299831685/118141/60f2b863/592536c8N5e724868.jpg"],
				
				String[] imgArr = imgs.split("\",\""); 
				for (String img : imgArr) {
					imgList.add(img.replace("\"],", ""));
				}
			}
			product.setImgUrls(imgList);
		}
		return product;
	}

}
