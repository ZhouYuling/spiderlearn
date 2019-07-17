package com.gzhc365.spider.distributed;

import com.gzhc365.utils.JavaSpider;
import com.gzhc365.utils.RedisUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 17:06 2019/7/17 0017
 * @Modified by:
 */
public class _2_getSmallClassiUrl {

    protected static void getSmallClassiUrl() {

        String[] bigUrl = {
                "西药||外科用药及消毒防腐药||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H20",
                "西药||皮肤科用药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H21",
                "西药||眼科用药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H22",
                "西药||耳鼻喉科用药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H23",
                "西药||口腔科用药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H24",
                "西药||妇产科用药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H25",
                "西药||男科用药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H26",
                "西药||生物制品||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H27",
                "西药||诊断用药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H28",
                "西药||作用于植物神经系统的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H29",
                "西药||治疗丙肝用药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H30",
                "西药||治疗肺动脉高压的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H31",
                "西药||影响骨代谢的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H32",
                "西药||治疗类风湿关节炎的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H33",
                "西药||银屑病的治疗药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H34",
                "西药||治疗强直性脊柱炎的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H35",
                "西药||治疗黑色素瘤的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H36",
                "西药||治疗特发性肺纤维化药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H37",
                "西药||治疗罕见病的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H38",
                "西药||治疗克罗恩病的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H39",
                "西药||治疗骨质疏松的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H40",
                "西药||预防宫颈癌的疫苗||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H41",
                "西药||治疗多发性骨髓瘤的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H42",
                "西药||治疗非小细胞肺癌的靶向药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H43",
                "西药||治疗肝细胞癌(HCC)治疗药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H44",
                "西药||治疗乙肝的药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H45",
                "西药||抗艾滋(HIV)病毒类药物||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=H46",
                "中药||中药材||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=Z01",
                "中药||中成药||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=Z02",
                "中药||方剂||http://drugs.medlive.cn/drugref/drugCate.do?treeCode=Z03"};

        Jedis jedis = RedisUtil.getJedis();
        List<String> lrange = jedis.lrange("BigClassiUrl", 0, jedis.llen("BigClassiUrl"));
        for (String s : lrange) {
            System.out.println("===========");
//            System.out.println(s);
            String[] split = s.split("\\|\\|");
            String bigClass = split[0];
            String smallClass = split[1];
            String smallClassUrl = split[2];

            try {
                String htmlGBK1 = JavaSpider.getHtmlGBK(smallClassUrl);
                Document parse1 = Jsoup.parse(htmlGBK1);
                Elements select1 = parse1.select("div.drug_title");
                Elements select2 = parse1.select("div.drug_list");//理论上 select1.size()和select2.size()应该是相等的
                for (int j = 0; j < select1.size(); j++) {
                    String smallName = select1.get(j).text();
                    //首先判断其是否有子类
                    String text = select2.get(j).text().replace(" ","");
                    if(!"".equals(text)){//有子类则直接根据子类的url访问，并且存储树形结构
                        Elements span = select2.get(j).select("span a");
                        for (Element element : span) {
                            String zlName = element.text();
                            String zlUrl = "http://drugs.medlive.cn/" + element.attr("href").replace("drugCate","drugCateLast");
                            System.out.println(bigClass + "||" + smallClass + "||" + smallName + "||" + zlName + "||" + zlUrl);
                        }
                    }else { //如果没有子类根据code和j的值进行访问
                        String code = "";
                        if(j < 10){
                            code =  "0" + (1 + j);
                        }else{
                            code = "" + (1 + j);
                        }
                        String zlUrl = "http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=" + smallClassUrl.substring("http://drugs.medlive.cn/drugref/drugCate.do?treeCode=".length()) + code;
//                        System.out.println(bigClass + "||" + smallClass + "||" +  smallName + "||" + zlUrl);
                        jedis.lpush("SmallClassiUrl",bigClass + "||" + smallClass + "||" +  smallName + "||" + zlUrl);
                    }
                }

                try {
                    Random rndSecond=new Random();
                    int second=rndSecond.nextInt(10);
                    System.out.println("||" +second + "||");
                    Thread.sleep(second * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                jedis.lpush("SmallClassErrorUrl",s);
            }
        }
        RedisUtil.returnResource(jedis);
    }

}
