package com.gzhc365.spider.distributed;

import com.gzhc365.utils.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 15:53 2019/7/16 0016
 * @Modified by:
 */
public class _3_errorUrl {

    public static void main(String[] args) {

        String[] url = {"西药||耳鼻喉科用药||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H23||3",
                "西药||口腔科用药||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H24||6",
                "西药||治疗丙肝用药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H30||2",
                "西药||治疗肺动脉高压的药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H31||1",
                "西药||影响骨代谢的药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H32||2",
                "西药||治疗特发性肺纤维化药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H37||2",
                "西药||治疗黑色素瘤的药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H36||2",
                "西药||治疗克罗恩病的药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H39||4",
                "西药||治疗骨质疏松的药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H40||7",
                "西药||预防宫颈癌的疫苗||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H41||1",
                "西药||治疗多发性骨髓瘤的药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H42||1",
                "西药||治疗非小细胞肺癌的靶向药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H43||2",
                "西药||治疗肝细胞癌(HCC)治疗药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H44||1",
                "西药||抗艾滋(HIV)病毒类药物||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H46||8",
                "西药||外科用药及消毒防腐药||http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H20||12"};

        for (String s : url) {
            getMedDetail(s);
        }

    }

    private static void getMedDetail(String s) {

        Jedis jedis = RedisUtil.getJedis();

        String[] split = s.split("\\|\\|");
        String bigClass = split[0];
        String smallClass = split[1];
        String url = split[2];
        String smallSmallClass = "";
        String smallSmallSmallClass = "";
        int index = Integer.parseInt(split[3]);

        for (int i = 1; i <= index; i++) {

            String urlNew = url + "&page=" + i;
            String big = bigClass + "||" + smallClass + "||" + smallSmallClass +  "||" + smallSmallSmallClass +  "||" + urlNew;
            if (jedis != null) {
                jedis.rpush("pageUrl_other",big);
            }

        }
    }

}
