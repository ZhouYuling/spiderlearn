package com.gzhc365.spider.single;

import com.gzhc365.utils.ImagesUtils;
import com.gzhc365.utils.JavaSpider;
import com.gzhc365.utils.MysqlUtil;
import com.gzhc365.utils.RedisUtil;
import com.gzhc365.vo.Dept;
import com.gzhc365.vo.Doctor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 10:59 2019/7/23 0023
 * @Modified by:
 */
public class devel_his_08_whuh {

    public static final String URL = "http://www.nxxzyyy.com";
    public static final int his_id = 187;

    public static void main(String[] args) {

//        getDeptInfo();

//        getDocInfo();
//        RedisUtil.listRemoveDupcate("whuh");
//        RedisUtil.backupList("whuh_list_1_bakup_1");

        Jedis jedis = RedisUtil.getJedis();
        Long whuh_list_11 = jedis.llen("whuh_list_1");
        ArrayList<String> errorList = new ArrayList<>();
        while (jedis.llen("whuh_list_1") != 0){
            String whuh_list_1 = jedis.lpop("whuh_list_1");
            String[] split = whuh_list_1.split("\\|\\|");
            String dept_code = split[0];
            int dept_id = Integer.parseInt(split[1]);
            int page = Integer.parseInt(split[2]);
            for (int i = 1; i <= page; i++) {
                try {
                    getDocDetailInfo(dept_code, dept_id, i + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    errorList.add(whuh_list_1);
                }
            }
        }
        System.out.println(errorList);
    }

    private static void getDocInfo() {

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(4,1241);
        map.put(7,1242);
        map.put(8,1243);
        map.put(5,1244);
        map.put(10,1245);
        map.put(82,1246);
        map.put(9,1247);
        map.put(12,1248);
        map.put(83,1249);
        map.put(20,1250);
        map.put(27,1251);
        map.put(15,1252);
        map.put(91,1253);
        map.put(24,1254);
        map.put(18,1255);
        map.put(25,1256);
        map.put(28,1257);
        map.put(17,1258);
        map.put(16,1259);
        map.put(22,1260);
        map.put(23,1261);
        map.put(13,1262);
        map.put(19,1263);
        map.put(21,1264);
        map.put(32,1265);
        map.put(33,1266);
        map.put(26,1267);
        map.put(30,1268);
        map.put(31,1269);
        map.put(29,1270);
        map.put(34,1271);
        map.put(85,1272);
        map.put(86,1273);
        map.put(87,1274);
        map.put(88,1275);
        map.put(89,1276);
        map.put(93,1277);
        map.put(94,1278);
        map.put(95,1279);
        map.put(96,1280);
        map.put(110,1281);
        map.put(52,1282);
        map.put(53,1283);
        map.put(49,1284);
        map.put(51,1285);
        map.put(57,1286);
        map.put(50,1287);
        map.put(54,1288);
        map.put(73,1289);
        map.put(66,1290);
        map.put(65,1291);
        map.put(109,1292);
        map.put(81,1293);
        map.put(80,1294);
        map.put(79,1295);
        map.put(78,1296);
        map.put(77,1297);
        map.put(75,1298);
        map.put(74,1299);
        map.put(58,1300);
        map.put(59,1301);
        map.put(60,1302);
        map.put(61,1303);
        map.put(62,1304);
        map.put(63,1305);
        map.put(64,1306);

        String url = "http://www.whuh.com/doctorss/search.html";

        try {
            String htmlGBK = JavaSpider.getHtmlGBK(url);
            Document parse = Jsoup.parse(htmlGBK);
            String tsks = parse.select("body > div.m.mt20.ksdh.p20 > div:nth-child(5)").text();//修改一下
            Elements select = parse.select("body > div.m.mt20.ksdh.p20 > div:nth-child(9) > ul > li");//修改一下
            for (Element element : select) {
                System.out.println("===");
                String dept_url = element.select("a").attr("href");
                String deptName = element.select("a").text();
                String dept_code = dept_url.substring(dept_url.lastIndexOf("/") + 1, dept_url.lastIndexOf("."));
                int dept_id = map.get(Integer.parseInt(dept_code));
//                System.out.println(tsks + "  " + dept_url + "  " + dept_id);

                //医生列表 http://www.whuh.com/doctorss/index/sections_id/4/page/3.html
                //医生列表首页 http://www.whuh.com/doctorss/index/sections_id/4.html
                //医生列表http://www.whuh.com/doctorss/index/sections_id/4/page/1.html

                String htmlGBK8 = JavaSpider.getHtmlGBK("http://www.whuh.com/doctorss/index/sections_id/" + dept_code + ".html");
                System.out.println("http://www.whuh.com/doctorss/index/sections_id/" + dept_code + ".html");
                Document parse8 = Jsoup.parse(htmlGBK8);
                String page = parse8.select("li.itemCount").text().replace("共","").replace("条","");
                int flag = 1;
                if(page.length() != 0){
                    flag = Integer.parseInt(page) / 16 + 1;
                    System.out.println("flag " + flag);
                    Jedis jedis = RedisUtil.getJedis();
                    jedis.lpush("whuh",dept_code + "||" + dept_id + "||" + flag);
                }else {
                    System.out.println("错误的信息：" + "http://www.whuh.com/doctorss/index/sections_id/" + dept_code + ".html");
                }

//                for (int i = 1; i <= flag; i++) {
//                    getDocDetailInfo(dept_code, dept_id, i + "");
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void getDocDetailInfo(String dept_code, int dept_id, String page) throws Exception {
        String htmlGBK1 = JavaSpider.getHtmlGBK("http://www.whuh.com/doctorss/index/sections_id/" + dept_code +"/page/"+ page + ".html");//医生列表url
        System.out.println("http://www.whuh.com/doctorss/index/sections_id/" + dept_code +"/page/"+ page + ".html");
        Document parse1 = Jsoup.parse(htmlGBK1);
        Elements select1 = parse1.select("div.zj_list ul li");
        for (Element element1 : select1) {
            System.out.println("====");
            String href = element1.select("li div.FL a").attr("href");
            String code = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
            String htmlGBK2 = JavaSpider.getHtmlGBK("http://www.whuh.com" + href);
            System.out.println("http://www.whuh.com" + href);
            Document parse2 = Jsoup.parse(htmlGBK2);
            String docName = parse2.select("div.zj_b1").text();
            String src = parse2.select("div.m.mt20 > div > div.x.pt10 > div:nth-child(1) > img").attr("src");
            String head_img = ImagesUtils.downImages("c:/images", src);
            Elements select2 = parse2.select("body > div.m.mt20 > div > div.nr3.pt10");
            String summary = select2.text();
            String summary_full = select2.html();

            String skillAndLevel = parse2.select("div.zj_nr").text();

            String level = skillAndLevel.substring(skillAndLevel.indexOf("职　　称") + 6,skillAndLevel.indexOf("专业专长"));
            String skill = skillAndLevel.substring(skillAndLevel.indexOf("专业专长") + 5);

            //docDetailUrl = http://www.whuh.com/doctorss/view/1149.html
            Doctor doc = new Doctor();
            doc.setHis_id(his_id);
            doc.setDept_id(dept_id);
            doc.setDoctor_name(docName);
            doc.setCode(code);
            doc.setHead_img(head_img);
            doc.setSummary(summary);
            doc.setSummary_full(summary_full);
            doc.setSkill(skill);
            doc.setLevel(level);
//            System.out.println(doc.toString());

            MysqlUtil.insertToDocter(doc);
            Thread.sleep(1);
        }
    }

    private static void getDeptInfo() {

        String url = "http://www.whuh.com/sections/index.html";
        HashMap<String, Integer> map = new HashMap<>();
        map.put("特色专科",1238);
        map.put("医技科室",1239);
        map.put("医疗中心",1240);

        int sort_no = 53;

        try {
            String htmlGBK = JavaSpider.getHtmlGBK(url);
            Document parse = Jsoup.parse(htmlGBK);
            String tsks = parse.select("body > div.m.mt20.ksdh.p20 > div:nth-child(8)").text();//修改一下
            Elements select = parse.select("body > div.m.mt20.ksdh.p20 > div:nth-child(9) > ul > li");//修改一下
            int tsks_1 = map.get(tsks);
            for (Element element : select) {
                System.out.println("===");
                String dept_url = element.select("a").attr("href");
                String deptName = element.select("a").text();
                String dept_id = dept_url.substring(dept_url.lastIndexOf("/") + 1,dept_url.lastIndexOf("."));
//                System.out.println(tsks + "  " + dept_url + "  " + dept_id);
                String dept_Detail = "http://www.whuh.com/sections/detail/id/" + dept_id + "/select/desc.html";
                System.out.println(deptName + "  " + dept_url + "  " + dept_Detail);

                String htmlGBK1 = JavaSpider.getHtmlGBK(dept_Detail);
                Document parse1 = Jsoup.parse(htmlGBK1);
                Elements select1 = parse1.select("#con_tab_1 > p");
                if(select1.text().length() == 0){
                    select1 = parse1.select("#Tab > div.Contentbox_xk1");
                }

//                System.out.println(select1.text());
                String summary = select1.text();
                String summary_full = select1.html();

                Dept dept = new Dept();
                dept.setHis_id(his_id);
                dept.setDept_name(deptName);
                dept.setDept_code(dept_id);
                dept.setSummary(summary);
                dept.setSummary_full(summary_full);
                dept.setHas_child(1);
                dept.setSort_no(sort_no);
                sort_no ++;
                dept.setPid(tsks_1);

//                System.out.println(dept.toString());

                MysqlUtil.insertToDept(dept);
                Thread.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
