package com.gzhc365.spider.single;

import com.gzhc365.utils.ImagesUtils;
import com.gzhc365.utils.JavaSpider;
import com.gzhc365.utils.MysqlUtil;
import com.gzhc365.vo.Dept;
import com.gzhc365.vo.Doctor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 14:54 2019/7/24 0024
 * @Modified by:
 */
public class devel_his_09_lpssrmyy {

    public static void main(String[] args) {
//        getDeptInfo();

        getDocInfo();
    }

    private static void getDeptInfo() {

        int his_id = 2297;
        int sort_no = 4;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,1307);
        map.put(1,1308);
        map.put(2,1309);
        map.put(3,1310);

        try {
//            String htmlGBK1 = JavaSpider.getHtmlGBK("http://www.lpssrmyy.cn/web100/listks.aspx");
//            Document parse1 = Jsoup.parse(htmlGBK1);
//            String src = parse1.select("span[id=web10010] iframe").get(0).attr("src");
//            System.out.println(src);//expLab/lablistMore.aspx?c=5&pc=50&

            String htmlGBK = JavaSpider.getHtmlGBK("http://www.lpssrmyy.cn/expLab/lablistMore.aspx?c=5&pc=50&");//异步加载，点击科室元素看看
            Document parse = Jsoup.parse(htmlGBK);
            Elements tbody_tr = parse.select("tbody tr");
            for (int i = 0; i < tbody_tr.size(); i++) {
                int bigClass = map.get(i);
                Elements select1 = tbody_tr.select("td[align=\"left\"] div");
                for (Element element : select1) {
                    Elements select = element.select("div[class=kslb] a");
                    String deptName = select.text();
                    String deptUrl = "http://www.lpssrmyy.cn/expLab/" + select.attr("href");
                    System.out.println(bigClass + deptName + deptUrl);

                    String dept_code = deptUrl.substring(deptUrl.lastIndexOf("=") + 1);

                    String htmlGBK1 = JavaSpider.getHtmlGBK(deptUrl);
                    Document parse1 = Jsoup.parse(htmlGBK1);
                    Elements select2 = parse1.select("body > div:nth-child(2) > div > div > div > table > tbody > tr > td:nth-child(1) > div > div:nth-child(2) > div > div:nth-child(4) > div");
                    String summary = select2.text();
                    String summary_full = select2.html();

                    Dept dept = new Dept();
                    dept.setHis_id(his_id);
                    dept.setDept_name(deptName);
                    dept.setDept_code(dept_code);
                    dept.setSummary(summary);
                    dept.setSummary_full(summary_full);
                    dept.setHas_child(1);
                    dept.setSort_no(sort_no);
                    sort_no ++;
                    dept.setPid(bigClass);

//                    System.out.println(dept.toString());
                    MysqlUtil.insertToDept(dept);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void getDocInfo() {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("重点学科",1307);
        map.put("临床科室",1308);
        map.put("医技科室",1309);
        map.put("行政科室",1310);
        map.put("消化内科（省级）",1311);
        map.put("普外科（省级）",1312);
        map.put("神经内科",1313);
        map.put("神经外科",1314);
        map.put("胸心外科",1315);
        map.put("肝胆外科",1316);
        map.put("泌尿外科",1317);
        map.put("重症医学科",1318);
        map.put("普儿科",1319);
        map.put("麻醉手术科",1320);
        map.put("心血管内科",1321);
        map.put("肛肠科",1322);
        map.put("骨二科",1323);
        map.put("骨一科",1324);
        map.put("耳鼻喉科",1325);
        map.put("口腔科",1326);
        map.put("烧伤整形血管介入科",1327);
        map.put("妇产科",1328);
        map.put("眼科",1329);
        map.put("感染性疾病科",1330);
        map.put("呼吸内科",1331);
        map.put("风湿免疫科",1332);
        map.put("全科医学科",1333);
        map.put("泌尿内科",1334);
        map.put("内分泌科",1335);
        map.put("肿瘤血液科",1336);
        map.put("康复医学科",1337);
        map.put("预防保健科",1338);
        map.put("门诊部",1339);
        map.put("急诊科",1340);
        map.put("体检科",1341);
        map.put("家庭病床科",1342);
        map.put("疼痛科",1343);
        map.put("皮肤科",1344);
        map.put("病理科",1345);
        map.put("放射科",1346);
        map.put("功能科",1347);
        map.put("检验科",1348);
        map.put("内窥镜科",1349);
        map.put("输血科",1350);
        map.put("西药剂科",1351);
        map.put("中药剂科",1352);
        map.put("影像科",1353);
        map.put("消毒供应室",1354);
        map.put("介入科",1355);
        map.put("党委办公室",1356);
        map.put("纪检监察室",1357);
        map.put("医务处",1358);
        map.put("人力资源科",1359);
        map.put("办公室",1360);
        map.put("护理部",1361);
        map.put("设备科",1362);
        map.put("感染管理科",1363);
        map.put("财务科",1364);
        map.put("审计科",1365);
        map.put("消化内科（省级）",1366);
        map.put("普外科（省级）",1367);
        map.put("神经内科",1368);
        map.put("神经外科",1369);
        map.put("胸心外科",1370);
        map.put("肝胆外科",1371);
        map.put("泌尿外科",1372);
        map.put("重症医学科",1373);
        map.put("普儿科",1374);
        map.put("麻醉手术科",1375);
        map.put("心血管内科",1376);
        map.put("肛肠科",1377);
        map.put("骨二科",1378);
        map.put("骨一科",1379);
        map.put("耳鼻喉科",1380);
        map.put("口腔科",1381);
        map.put("烧伤整形血管介入科",1382);
        map.put("妇产科",1383);
        map.put("眼科",1384);
        map.put("感染性疾病科",1385);
        map.put("呼吸内科",1386);
        map.put("风湿免疫科",1387);
        map.put("全科医学科",1388);
        map.put("泌尿内科",1389);
        map.put("内分泌科",1390);
        map.put("肿瘤血液科",1391);
        map.put("康复医学科",1392);
        map.put("预防保健科",1393);
        map.put("门诊部",1394);
        map.put("急诊科",1395);
        map.put("体检科",1396);
        map.put("家庭病床科",1397);
        map.put("疼痛科",1398);
        map.put("皮肤科",1399);
        map.put("病理科",1400);
        map.put("放射科",1401);
        map.put("功能科",1402);
        map.put("检验科",1403);
        map.put("内窥镜科",1404);
        map.put("输血科",1405);
        map.put("西药剂科",1406);
        map.put("中药剂科",1407);
        map.put("影像科",1408);
        map.put("消毒供应室",1409);
        map.put("介入科",1410);
        map.put("党委办公室",1411);
        map.put("纪检监察室",1412);
        map.put("医务处",1413);
        map.put("人力资源科",1414);
        map.put("办公室",1415);
        map.put("护理部",1416);
        map.put("设备科",1417);
        map.put("感染管理科",1418);
        map.put("财务科",1419);
        map.put("审计科",1420);
        map.put("消化内科（省级）",1421);
        map.put("普外科（省级）",1422);
        map.put("神经内科",1423);
        map.put("神经外科",1424);
        map.put("胸心外科",1425);
        map.put("肝胆外科",1426);
        map.put("泌尿外科",1427);
        map.put("重症医学科",1428);
        map.put("普儿科",1429);
        map.put("麻醉手术科",1430);
        map.put("心血管内科",1431);
        map.put("肛肠科",1432);
        map.put("骨二科",1433);
        map.put("骨一科",1434);
        map.put("耳鼻喉科",1435);
        map.put("口腔科",1436);
        map.put("烧伤整形血管介入科",1437);
        map.put("妇产科",1438);
        map.put("眼科",1439);
        map.put("感染性疾病科",1440);
        map.put("呼吸内科",1441);
        map.put("风湿免疫科",1442);
        map.put("全科医学科",1443);
        map.put("泌尿内科",1444);
        map.put("内分泌科",1445);
        map.put("肿瘤血液科",1446);
        map.put("康复医学科",1447);
        map.put("预防保健科",1448);
        map.put("门诊部",1449);
        map.put("急诊科",1450);
        map.put("体检科",1451);
        map.put("家庭病床科",1452);
        map.put("疼痛科",1453);
        map.put("皮肤科",1454);
        map.put("病理科",1455);
        map.put("放射科",1456);
        map.put("功能科",1457);
        map.put("检验科",1458);
        map.put("内窥镜科",1459);
        map.put("输血科",1460);
        map.put("西药剂科",1461);
        map.put("中药剂科",1462);
        map.put("影像科",1463);
        map.put("消毒供应室",1464);
        map.put("介入科",1465);
        map.put("党委办公室",1466);
        map.put("纪检监察室",1467);
        map.put("医务处",1468);
        map.put("人力资源科",1469);
        map.put("办公室",1470);
        map.put("护理部",1471);
        map.put("设备科",1472);
        map.put("感染管理科",1473);
        map.put("财务科",1474);
        map.put("审计科",1475);
        map.put("消化内科（省级）",1476);
        map.put("普外科（省级）",1477);
        map.put("神经内科",1478);
        map.put("神经外科",1479);
        map.put("胸心外科",1480);
        map.put("肝胆外科",1481);
        map.put("泌尿外科",1482);
        map.put("重症医学科",1483);
        map.put("普儿科",1484);
        map.put("麻醉手术科",1485);
        map.put("心血管内科",1486);
        map.put("肛肠科",1487);
        map.put("骨二科",1488);
        map.put("骨一科",1489);
        map.put("耳鼻喉科",1490);
        map.put("口腔科",1491);
        map.put("烧伤整形血管介入科",1492);
        map.put("妇产科",1493);
        map.put("眼科",1494);
        map.put("感染性疾病科",1495);
        map.put("呼吸内科",1496);
        map.put("风湿免疫科",1497);
        map.put("全科医学科",1498);
        map.put("泌尿内科",1499);
        map.put("内分泌科",1500);
        map.put("肿瘤血液科",1501);
        map.put("康复医学科",1502);
        map.put("预防保健科",1503);
        map.put("门诊部",1504);
        map.put("急诊科",1505);
        map.put("体检科",1506);
        map.put("家庭病床科",1507);
        map.put("疼痛科",1508);
        map.put("皮肤科",1509);
        map.put("病理科",1510);
        map.put("放射科",1511);
        map.put("功能科",1512);
        map.put("检验科",1513);
        map.put("内窥镜科",1514);
        map.put("输血科",1515);
        map.put("西药剂科",1516);
        map.put("中药剂科",1517);
        map.put("影像科",1518);
        map.put("消毒供应室",1519);
        map.put("介入科",1520);
        map.put("党委办公室",1521);
        map.put("纪检监察室",1522);
        map.put("医务处",1523);
        map.put("人力资源科",1524);
        map.put("办公室",1525);
        map.put("护理部",1526);
        map.put("设备科",1527);
        map.put("感染管理科",1528);
        map.put("财务科",1529);
        map.put("审计科",1530);

        for (int i = 1; i <= 23; i++) {
            System.out.println("爬取到第" + i + "页了");
            try {
                String htmlGBK = JavaSpider.getHtmlGBK("http://www.lpssrmyy.cn/expLab/expListMore.aspx?p=" + i + "&lid=0");
                Document parse = Jsoup.parse(htmlGBK);
                Elements select = parse.select("#lists > tbody > tr > td");
                for (Element element : select) {

                    System.out.println("===");
//                    System.out.println(element);

                    String attr = "http://www.lpssrmyy.cn/expLab/" + element.select("a[target=\"_blank\"]").attr("href");
                    String htmlGBK1 = JavaSpider.getHtmlGBK(attr);
                    Document parse1 = Jsoup.parse(htmlGBK1);
                    Elements element1 = parse1.select("span[style=\"color: #888888\"]");
//                    System.out.println(element1);

                    String docName = "";
                    String deptName = "";
                    String level = "";

                    String skill = "";
                    String summary = "";
                    String summary_full = "";
                    String img = "http://www.lpssrmyy.cn" + parse1.select("img[height=\"185\"]").attr("src");
                    String head_img = "";
                    int dept_id = -1;
                    String code = "";

                    //单独try避免某个列出错导致医生信息丢失
                    try{
                        level = element1.get(3).parent().text().replace("职称","").replace("：","").replace(":","");
                        deptName = element1.get(1).parent().text().replace("科室","").replace("：","").replace(":","");
                        docName = element1.get(0).parent().text().replace("姓名","").replace("：","").replace(":","");
                    }catch (Exception e){
                        System.out.println(attr);
                    }


                    try {
                        skill = parse1.select("body > div:nth-child(2) > div > div > div > table > tbody > tr > td:nth-child(1) > div > div:nth-child(2) > div > div:nth-child(3) > div:nth-child(2)").text();
                        summary = parse1.select("body > div:nth-child(2) > div > div > div > table > tbody > tr > td:nth-child(1) > div > div:nth-child(2) > div > div:nth-child(4) > div:nth-child(2)").text();
                        summary_full = parse1.select("body > div:nth-child(2) > div > div > div > table > tbody > tr > td:nth-child(1) > div > div:nth-child(2) > div > div:nth-child(4) > div:nth-child(2)").text();
                    }catch (Exception e){
                        System.out.println(attr);
                    }


                    try {
                        head_img = ImagesUtils.downImages("c:/images", img);
                    }catch (Exception e){
                        System.out.println(attr);
                    }

                    try {
                        dept_id = map.get(deptName);
                    }catch (Exception e){
                        System.out.println(attr);
                    }

                    try {
                        code = attr.substring(attr.lastIndexOf("=") + 1);
                    }catch (Exception e){
                        System.out.println(attr);
                    }

                    Doctor doc = new Doctor();
                    doc.setHis_id(2297);
                    doc.setDept_id(dept_id);
                    doc.setDoctor_name(docName);
                    doc.setCode(code);
                    doc.setHead_img(head_img);
                    doc.setSummary(summary);
                    doc.setSummary_full(summary_full);
                    doc.setSkill(skill);
                    doc.setLevel(level);

//                    System.out.println(doc.toString());

                    MysqlUtil.insertToDocter(doc);
                    Thread.sleep(1);
                }

                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }




}
