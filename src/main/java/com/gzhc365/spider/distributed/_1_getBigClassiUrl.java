package com.gzhc365.spider.distributed;

import com.gzhc365.utils.JavaSpider;
import com.gzhc365.utils.RedisUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 17:05 2019/7/17 0017
 * @Modified by:
 */
public class _1_getBigClassiUrl {

    protected static void getBigClassiUrl(String enterURL) {

        try {
            String htmlGBK = JavaSpider.getHtmlGBK(enterURL);
            Document parse = Jsoup.parse(htmlGBK);
            Elements select = parse.select("div.table1 table");
            String[] big = {"西药", "中药"};//手动添加药品分类
            Jedis jedis = RedisUtil.getJedis();
            for (int i = 0; i < select.size(); i++) {
                System.out.println("=========");
//                System.out.println(element);
                Elements tbody_tr_td_a = select.get(i).select("tbody tr td a");
                for (Element element1 : tbody_tr_td_a) {
                    String href = element1.attr("href");
                    if ("".equals(href)) {
                        continue;
                    }

                    String name = element1.text();
                    String url = "http://drugs.medlive.cn/" + href;
                    jedis.lpush("BigClassiUrl",big[i] + "||" + name + "||" + url);//出入Redis中
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
