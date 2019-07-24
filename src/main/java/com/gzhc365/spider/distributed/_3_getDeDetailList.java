package com.gzhc365.spider.distributed;

import com.gzhc365.utils.DoProxy;
import com.gzhc365.utils.RedisUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 17:06 2019/7/17 0017
 * @Modified by:
 */
public class _3_getDeDetailList {

    protected static void getDeDetailList(int size) {

        ExecutorService pool = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            pool.submit(new Thread(() -> {
                Jedis jedis = RedisUtil.getJedis();
                String url = "";
                if (jedis != null) {
                    while (jedis.exists("SmallClassiUrl")) {
                        try {
                            List<String> smallClassiUrl = jedis.blpop(10, "SmallClassiUrl");
    //                            String redisKey = smallClassiUrl.get(0);
                            url = smallClassiUrl.get(1);
                            parseLine(url,jedis);
                            Thread.sleep(10);
                            System.out.println("arrayBlockingQueue.size()" + jedis.llen("SmallClassiUrl") + "  " + url);
                        } catch (Exception e) {
                            System.out.println("这个url出错了：" + url);
                            jedis.lpush("getDeDetailListError",url);
                        }finally {
                            RedisUtil.returnResource(jedis);
                        }
                    }
                }
            }));
        }
    }

    private static void parseLine(String line2,Jedis jedis) throws Exception {
        String[] split = line2.split("\\|\\|");
        String bigClass = "";
        String smallClass = "";
        String smallSmallClass = "";
        String smallSmallSmallClass = "";
        String url = "";
        int index = 1;

        if(split.length == 3){
            bigClass = split[0];
            smallClass = split[1];
            url = split[1];
//            System.out.println(1 + "||" + bigClass + "||" + smallClass + "||" + url);
            index = fromUrlGetMediDetail(url);
        }else if(split.length == 4){
            bigClass = split[0];
            smallClass = split[1];
            smallSmallClass = split[2];
            url = split[3];
//            System.out.println(2 + "||" + bigClass + "||" + smallClass + "||" + smallSmallClass + "||" + url);
            index = fromUrlGetMediDetail(url);
        }else if(split.length == 5){
            bigClass = split[0];
            smallClass = split[1];
            smallSmallClass = split[2];
            smallSmallSmallClass = split[3];
            url = split[4];
//            System.out.println(3 + "||" + bigClass + "||" + smallClass + "||"  + smallSmallClass + "||"  + smallSmallSmallClass + "||" + url);
            index = fromUrlGetMediDetail(url);
        }else {
            System.out.println("这是一个错误的url" + line2);
        }

        if (!"".equals(index+"")){
            for (int i = 1; i <= index; i++) {
                try {
                    String s = url + "&page=" + i;
                    String urlCount = bigClass + "||" + smallClass + "||" + smallSmallClass + "||" + smallSmallSmallClass + "||" + s;
//                System.out.println(urlCount);

                    if (jedis != null) {
                        jedis.rpush("pageUrl",urlCount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("出错页面" + line2);
        }

    }

    private static int fromUrlGetMediDetail(String zlUrl) throws Exception {

        //TODO:解析列表、获取药品详细信息
        String htmlGBK2 = null;
//        htmlGBK2 = JavaSpider.getHtmlGBK(zlUrl);
        htmlGBK2 = DoProxy.doProxy(zlUrl);

        Document parse2 = Jsoup.parse(htmlGBK2);
        String text = parse2.select("div.fenye ul span").text();
        try {
            String index = text.substring(1, text.indexOf("页"));
            return Integer.parseInt(index);
        }catch (Exception e){
            System.out.println("String index out of range: -2 ：" + zlUrl);
            throw new Exception("这个地方解析出错了！");
        }
    }

}
