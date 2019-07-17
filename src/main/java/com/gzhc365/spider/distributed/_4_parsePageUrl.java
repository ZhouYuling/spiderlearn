package com.gzhc365.spider.distributed;

import com.gzhc365.utils.DoProxy;
import com.gzhc365.utils.RedisUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 18:34 2019/7/15 0015
 * @Modified by:
 */
public class _4_parsePageUrl {

    protected static void parseMedicineUrl(int size){

        ExecutorService pool = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            pool.submit(new Thread(() -> {
                while (true) {
                    String url = "";
                    Jedis jedis =  RedisUtil.getJedis();
                    try {
                        List<String> pageUrl = null;
                        if (jedis != null) {
                            pageUrl = jedis.blpop(1, "pageUrl_other");
//                            String key = pageUrl.get(0);
                            url = pageUrl.get(1);
//                            System.out.println("key:" + key + " url:" + url);
                            if(url != null && !"".equals(url)){
                                parseUrl(url,jedis);
                            }
                        }
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("这个url出错了：" + url);
                        if (jedis != null) {
                            jedis.rpush("pageUrl_other",url);
                        }
                    }finally {
                        RedisUtil.returnResource(jedis);
                    }
                }
            }));
        }


    }

    private static void parseUrl(String zlUrl,Jedis jedis) throws Exception {

//        System.out.println(zlUrl);
        String[] split = zlUrl.split("\\|\\|");
        String bigClass = split[0];
        String smallClass = split[1];
        String smallSmallClass = split[2];
        String smallSmallSmallClass = split[3];
        String url = split[4];

//        String html = JavaSpider.getHtmlGBK(url);
        String html = DoProxy.doProxy(url);
        Document parse3 = Jsoup.parse(html);
        Elements box_list1 = parse3.select("div.box-list div.box1");
        for (Element element2 : box_list1) {
            System.out.println("-------");
            String tempName = element2.select("div.medince-name a").text();
            String href1 = "http://drugs.medlive.cn/" + element2.select("div.medince-name a").attr("href");
            String bigString = bigClass + "||" + smallClass + "||"  + smallSmallClass + "||"  + smallSmallSmallClass + "||" + tempName + "||" + href1;
            System.out.println(bigString);
            jedis.rpush("medUrl_other",bigString);
        }

    }



}
