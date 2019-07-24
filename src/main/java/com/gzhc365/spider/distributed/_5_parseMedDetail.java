package com.gzhc365.spider.distributed;

import com.gzhc365.utils.DoProxy;
import com.gzhc365.utils.Mongo;
import com.gzhc365.utils.RedisUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 9:32 2019/7/16 0016
 * @Modified by:
 */
public class _5_parseMedDetail {

    protected static void parseMedicineDetail(int size){

        ExecutorService pool = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            pool.submit(new Thread(() -> {
                String url = "";
                Jedis jedis =  RedisUtil.getJedis();
                if (jedis != null) {
                    while (jedis.exists("medUrl_other")) {
                        try {
                            List<String> pageUrl = null;
                            if (jedis != null) {
                                pageUrl = jedis.blpop(1, "medUrl_other");
    //                                String key = pageUrl.get(0);
                                url = pageUrl.get(1);
    //                                url = "西药||||β内酰胺类||||阿莫西林胶囊 - 回音必集团浙江亚东制药有限公司||http://drugs.medlive.cn/drugref/html/17683.shtml";
    //                                System.out.println("key:" + key + " url:" + url);
                                if(url != null && !"".equals(url)){
                                    getMediDetail(url);
                                }
                            }
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("这个url出错了：" + url);
                            if (jedis != null) {
                                jedis.rpush("medUrl_other",url);//TODO:这个地方需要修改一下
                            }
                        }finally {
                            RedisUtil.returnResource(jedis);
                        }
                    }
                }
            }));
        }
    }


    private static void getMediDetail(String href1) throws Exception {

        String[] split = href1.split("\\|\\|");
        String bigClass = split[0];
        String smallClass = split[1];
        String smallSmallClass = split[2];
        String smallSmallSmallClass = split[3];
        String tempName = split[4];
        String url = split[5];

        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();//使用LinkedHashMap、保证put顺序一直
        map.put("bigClass",bigClass);
        map.put("smallClass",smallClass);
        map.put("smallSmallClass",smallSmallClass);
        map.put("smallSmallSmallClass",smallSmallSmallClass);
        map.put("tempName",tempName);

        //TODO:注释
//        System.out.println(bigClass + "||" + smallClass + "||"  + smallSmallClass + "||"  + smallSmallSmallClass + "||" + tempName + "||" + url);
        String htmlGBK3 = DoProxy.doProxy(url);
//            htmlGBK3 = JavaSpider.getHtmlUTF8(url);
        Document parse3 = Jsoup.parse(htmlGBK3);
        Elements select3 = parse3.select("div.info-left div");
        for (Element element3 : select3) {
            String title1 = element3.select("div div.title").text().replace("：","").replace(":","");
            if("".equals(title1)){
                continue;
            }

            String cont = element3.select("div div.more-infomation").text();
            if("".equals(cont)){
                continue;
            }
//            System.out.println("******");
//            System.out.println(title1  + "\r\n" + cont);
            map.put(title1,cont);
        }
        Mongo instans = Mongo.getInstanstance();
        instans.insertMedicineIntoMongo(map);

    }

}
