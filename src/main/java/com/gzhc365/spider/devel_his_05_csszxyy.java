package com.gzhc365.spider;

import com.gzhc365.utils.*;
import com.gzhc365.vo.Dept;
import com.gzhc365.vo.Doctor;
import com.gzhc365.vo.MyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 15:09 2019/7/5 0005
 * @Modified by:
 */
public class devel_his_05_csszxyy {

    public static final String URL = "http://www.csszxyy.com/";
    public static final int his_id = 2042;

    public static void main(String[] args) {

        //医院介绍
        // 如果手快还是直接手动粘贴到navicat、存入mysql数据库中

        //科室信息
//        getDeptInfo();

        //医生信息
        getDocInfo();
//        getDocInfoByHashSet();//通过HashSet去重医生列表

        //楼层分布
//        String textContent = ImagesUtils.getPictureWordByBaiduOrc(System.getProperty("user.dir")  + "\\src\\main\\resources\\pic\\test.png");
//        System.out.println(textContent);

    }

    private static void getDeptInfo(){

        //手动输入大类以后、导出用notepad++进行处理、变成map 详见resources/tools中2.科室的 星号部分
        //SELECT dept_name,id FROM t_hc_dept WHERE his_id = 2042
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        map.put("临床科室",749);
        map.put("医技科室",750);
        map.put("肺科医院科室",751);
        map.put("北院科室",752);
        map.put("研究所",753);

        //sort_no是科室显示顺序、如上所示，大类有5种了、sort_no则为大类数量+1即为6
        int sort_no = 6;
        //用于保存爬取数据出错的url
        ArrayList<String> errorList = new ArrayList<String>();
        //通过url获取页面信息
        String htmlGBK = null;
        try {
            htmlGBK = JavaSpider.getHtmlGBK("http://www.csszxyy.com/ksdh/list_46.aspx");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用Jsoup进行页面解析
        Document parse = Jsoup.parse(htmlGBK);
        //获取5个大类(临床科室/医技科室/肺科医院科室/北院科室/研究所)的遍历Element
        Elements select = parse.select("div.ksdh_yjs table");//select中为解析css路径
        //遍历获取每个大类下的科室url
        for (Element element : select) {
            //首先打印下查看循环是否正常、Elements错误会导致科室url重复或者缺失等现象
            System.out.println("----");
//            System.out.println(element);
            //获取大类的名称，从而能够从map中获取大类的id，即为这个科室的pid
            String bigDeptName = element.select("tr td").get(0).text().replace(" ","");
            System.out.println(bigDeptName);
            //获取这个大类下每个子类的Element,Elements错误会导致科室url重复或者缺失等现象
            Elements tr_td = element.select("tr td").get(1).select(" ul li");
            for (Element element1 : tr_td) {
                //首先打印下是否正常
                System.out.println("===");
//                System.out.println(element1);
                //获取科室详细介绍url
                String deptUrl = URL + element1.select("a").attr("href");//http://www.csszxyy.com//sub/index.aspx?sub=63
                String deptName = element1.select("a").text();
                System.out.println(deptName + " " + deptUrl);
                //通过url获取科室的dept_code,一般有两种形式
                //形式一：http://www.csszxyy.com/sub/jieshao.aspx?sub=1
                String dept_code = deptUrl.substring(deptUrl.lastIndexOf("=") + 1);
                //形式二：http://www.csszxyy.com/sub/jieshao.aspx?sub=1.html
//                String dept_code1 = deptUrl.substring(deptUrl.lastIndexOf("=") + 1,deptUrl.lastIndexOf("."));
                //通过科室详细介绍url，获取科室介绍,这个地方找规律、或者提取url
                String htmlGBK1 = null;
                try {
                    htmlGBK1 = JavaSpider.getHtmlGBK("http://www.csszxyy.com/sub/jieshao.aspx?sub=" + dept_code);//http://www.csszxyy.com/sub/jieshao.aspx?sub=63
                } catch (MyException e) {
                    errorList.add(e.getUrl());
                    System.out.println(e.getMessage());
                }catch (Exception e) {
                    e.printStackTrace();
                }
                //Jsoup解析
                Document parse1 = Jsoup.parse(htmlGBK1);
                //获取详细介绍Element
                Elements select2 = parse1.select("div.txt_list");
                //调用.text()方法获取summary文本信息
                String summary = select2.select("div.txt_list").text();
                //调用.html()方法获取带html标签文本信息
                String summary_full = select2.select("div.txt_list").html();
                //获取父pid的值
                int pid = map.get(bigDeptName) != null ? map.get(bigDeptName) : -1;
                //创建Dept对象
                Dept dept = new Dept();
                dept.setHis_id(his_id);
                dept.setDept_name(deptName);
                dept.setDept_code(dept_code);
                dept.setSummary(summary);
                dept.setSummary_full(summary_full);//在打印台输出的时候、可以注释掉summary和summary_full这两行、确保其他信息正确
                dept.setHas_child(1);//如果有还有子科室、这个地方设置为0
                dept.setSort_no(sort_no);
                sort_no ++; //每存入一个科室到数据库中、sort_no增加1
                dept.setPid(pid);
                //打印dept对象、查看各个参数是否正常
                System.out.println(dept.toString());
                //存储到mysql数据库中、注意存储之前先检查druid.properties 数据库用户名和密码是否正确
//                try {
//                    MysqlUtil.insertToDept(dept);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
            }
        }
        System.out.println(errorList);
    }

    private static void getDocInfo() {

        //SELECT dept_name,id FROM t_hc_dept WHERE his_id = 2042
        //手动导出用notepad++进行处理、变成以下形式map 详见resources/tools中2.科室的 星号部分
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        map.put("临床科室",749);
        map.put("医技科室",750);
        map.put("肺科医院科室",751);
        map.put("北院科室",752);
        map.put("研究所",753);
        map.put("肿瘤科",754);
        map.put("消化内科",755);
        map.put("儿科",756);
        map.put("儿童结核科",757);
        map.put("新生儿科",758);
        map.put("心血管内一科",759);
        map.put("心血管内二科",760);
        map.put("普外科",761);
        map.put("运动医学、关节、骨病科",762);
        map.put("创伤、手外科",763);
        map.put("妇科",764);
        map.put("产科",765);
        map.put("呼吸内科",766);
        map.put("耳鼻咽喉头颈外科",767);
        map.put("眼科",768);
        map.put("口腔科",769);
        map.put("胸外科、心脏、大血管外科",770);
        map.put("泌尿外科",771);
        map.put("脊柱外科",772);
        map.put("神经外科",773);
        map.put("神经内科",774);
        map.put("健康管理中心",775);
        map.put("肾病、风湿免疫科",776);
        map.put("皮肤病、性病科",777);
        map.put("血液科",778);
        map.put("康复医学科",779);
        map.put("老年医学科",780);
        map.put("内分泌科",781);
        map.put("急诊科",782);
        map.put("麻醉手术科",783);
        map.put("重症医学科",784);
        map.put("血液净化中心",785);
        map.put("营养科",786);
        map.put("检验科",787);
        map.put("放射科（含介入诊疗中心）",788);
        map.put("超声诊断科",789);
        map.put("病理科",790);
        map.put("窥镜科",791);
        map.put("电生理科",792);
        map.put("核医学科",793);
        map.put("放疗科",794);
        map.put("药学部",795);
        map.put("门诊部",796);
        map.put("消毒供应中心",797);
        map.put("中心实验室",798);
        map.put("输血科",799);
        map.put("儿童结核科",800);
        map.put("肺外结核科",801);
        map.put("肺结核一科",802);
        map.put("肺结核二科",803);
        map.put("耐药结核、中医肺病科",804);
        map.put("结核重症科",805);
        map.put("内一科",806);
        map.put("全科医学科",807);
        map.put("中西医结合科",808);
        map.put("门诊部",809);
        map.put("结核病学研究所",810);
        map.put("老年医学研究所",811);
        map.put("肿瘤研究所",812);
        //以上是手工处理

        //使用一个ArrayList存储出错的url
        ArrayList<String> errorList = new ArrayList<String>();
        //暂时保存url
        String errorUrl = "";
        //观察发现医生共有29页
        for (int i = 1; i <= 29; i++) {
            String url ="http://www.csszxyy.com/index_48.aspx?ezeip=/TNSH862MDgNMUHG6fXMozXk96hgubNAJp3QNhlccy5ylGvTePke1KxSnEvD8Kxlsklu5mH%20oVkJTroVlsfjo0muVhdvcAh4vEMVvIDAQbRgnXW9FsCuY8iJz%20IBu1J2tYjWFndPf7EPUsuvFEzo8dmfdKGOEx5/7%206JtWWieeCJqJfd2ZmWWn6RSXzBze/rYxeO5MoBIxeASU42NACbha4k2flIau5Vkxchd7oSqJ72vIEfxUt694e46kQLM2CY&page=";
            String htmlGBK = null;
            try {
                htmlGBK = JavaSpider.getHtmlGBK(url + i);
                //当爬取页面出错时候、自定义Exception存储这个url
            } catch (MyException e) {
                System.out.println(e.getMessage());
                errorList.add(e.getUrl());
            }catch (Exception e) {
                e.printStackTrace();
            }
            //Jsoup解析html
            Document parse = Jsoup.parse(htmlGBK);
            //选择这一页的医生列表
            Elements select = parse.select("div.zjfc_qt ul li");
            for (Element element1 : select) {
                try {
                    //首先打印是否正常、Elements错误会导致科室url重复或者缺失等现象
                    System.out.println("--");
//                    System.out.println(element1.select("div.div").text());
                    String select3 = element1.select("div.div").text();
                    String[] split = select3.split(" ");
                    String docName = "";
                    String level = "";
                    String dept_name = "";
                    for (String s : split) {
                        if (s.startsWith("姓名")) {
                            docName = s.split("：")[1];
                        }else if (s.startsWith("职称")) {
                            level = s.split("：")[1];
                        }else if (s.startsWith("科室")) {
                            dept_name = s.split("：")[1];
                        }
                    }
                    String docInfoUrl = URL + element1.select("div.div a").get(1).attr("href");
                    errorUrl = docInfoUrl;
//                    System.out.println(docInfoUrl);

                    String htmlGBK1 = JavaSpider.getHtmlGBK(docInfoUrl);
                    Document parse1 = Jsoup.parse(htmlGBK1);
                    String summary = parse1.select("div.Content").text();
                    String summary_full = parse1.select("div.Content").html();

//                    System.out.println(element1.select("div.jyzn_mzcx p"));

                    String head_img = URL + parse1.select("div.jyzn_mzcx p").get(0).select("img").attr("src");
                    int dept_id = map.get(dept_name) != null ? map.get(dept_name) : -1;
                    String code = docInfoUrl.substring(docInfoUrl.lastIndexOf("_") + 1,docInfoUrl.lastIndexOf("."));

                    String picLoc = "";
                    try {
                        picLoc = ImagesUtils.downImages("c:/images", head_img);
                    }catch (Exception e){//如果String#substring()和String#lastIndexOf()出现问题、图片没有下载成功，将没有下载成功的url保存
                        e.printStackTrace();
                        errorList.add(docName + "||" + errorUrl);
                    }

                    //创建一个医生对象
                    Doctor doc = new Doctor();
                    doc.setHis_id(his_id);//医院名称
                    doc.setDept_id(dept_id);//医生科室名称
                    doc.setDoctor_name(docName);//医生姓名
                    doc.setCode(code);//医生编码
                    doc.setHead_img(picLoc);//医生头像位置
                    doc.setSummary(summary);//医生介绍
                    doc.setSummary_full(summary_full);//带标签的医生介绍

                    String skill = TextUtils.regexText(summary, "擅长(.*?)。");//通过正则匹配获取summary中医生擅长、每个医院的风格不同、可能是专长、能够、对 等。
                    doc.setSkill(skill);//医生擅长
                    doc.setLevel(level);//医生的职称

                    //在存入数据库之前检查数据是否正确
                    System.out.println(doc.toString());
                    //存入数据库中
//                    MysqlUtil.insertToDocter(doc);

                }catch (Exception e){
                    e.printStackTrace();
                    errorList.add(errorUrl);
                }
            }
        }
        System.out.println("爬取失败的网站：|没有下载成功的医生头像：");
        System.out.println(errorList);
    }

    public static void getDocInfoByHashSet(){

        HashMap<String,Integer> map = new HashMap<String,Integer>();
        map.put("临床科室",749);
        map.put("医技科室",750);
        map.put("肺科医院科室",751);
        map.put("北院科室",752);
        map.put("研究所",753);


        //使用一个ArrayList存储出错的url
        ArrayList<String> errorList = new ArrayList<String>();
        //暂时保存url
        String errorUrl = "";
        HashSet<String> docDetails = new HashSet<>();
        //观察发现医生共有29页,此处只爬取2页做示范
        for (int i = 1; i <= 2; i++) {
            String url ="http://www.csszxyy.com/index_48.aspx?ezeip=/TNSH862MDgNMUHG6fXMozXk96hgubNAJp3QNhlccy5ylGvTePke1KxSnEvD8Kxlsklu5mH%20oVkJTroVlsfjo0muVhdvcAh4vEMVvIDAQbRgnXW9FsCuY8iJz%20IBu1J2tYjWFndPf7EPUsuvFEzo8dmfdKGOEx5/7%206JtWWieeCJqJfd2ZmWWn6RSXzBze/rYxeO5MoBIxeASU42NACbha4k2flIau5Vkxchd7oSqJ72vIEfxUt694e46kQLM2CY&page=";
            String htmlGBK = null;
            try {
                htmlGBK = JavaSpider.getHtmlGBK(url + i);
            } catch (MyException e) {//当爬取页面出错时候、自定义Exception存储这个url
                errorList.add(e.getUrl());
                System.out.println(e.getMessage());
            }catch (Exception e) {
                e.printStackTrace();
            }
            //Jsoup解析html
            Document parse = Jsoup.parse(htmlGBK);
            //选择这一页的医生列表
            Elements select = parse.select("div.zjfc_qt ul li");

            for (Element element1 : select) {
                try {
                    //首先打印是否正常、Elements错误会导致科室url重复或者缺失等现象
                    System.out.println("--");
//                    System.out.println(element1.select("div.div").text());
                    String select3 = element1.select("div.div").text();
                    String[] split = select3.split(" ");
                    String docName = "";
                    String level = "";
                    String dept_name = "";
                    //通过这种方式能够避免由于某个医生介绍较少、数组越界异常
                    for (String s : split) {
                        if (s.startsWith("姓名")) {
                            docName = s.split("：")[1];
                            continue;
                        }
                        if (s.startsWith("职称")) {
                            level = s.split("：")[1];
                            continue;
                        }
                        if (s.startsWith("科室")) {
                            dept_name = s.split("：")[1];
                        }
                    }
                    String docInfoUrl = URL + element1.select("div.div a").get(1).attr("href");
                    docDetails.add(docName + "||" + level + "||" + dept_name + "||" + docInfoUrl);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        //以上可以去重、同时讲HashSet改为Redis可是实现分布式爬虫，加入线程池可以增加爬虫爬取的速度，
        // 并且加入Redis能够避免程序退出造成重复爬取数据等现象
        for (String docDetail : docDetails) {
            String[] docArray = docDetail.split("\\|\\|");//这个地方要注意、多加转义字符没事
            String docName = docArray[0];
            String level = docArray[1];
            String dept_name = docArray[2];
            String docInfoUrl = docArray[3];

            String htmlGBK1 = null;
            try {
                htmlGBK1 = JavaSpider.getHtmlGBK(docInfoUrl);
            } catch (MyException e) {
                errorList.add(e.getUrl());
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            Document parse1 = Jsoup.parse(htmlGBK1);
            String summary = parse1.select("div.Content").text();
            String summary_full = parse1.select("div.Content").html();

//                    System.out.println(element1.select("div.jyzn_mzcx p"));

            String head_img = URL + parse1.select("div.jyzn_mzcx p").get(0).select("img").attr("src");
            int dept_id = map.get(dept_name) != null ? map.get(dept_name) : -1;
            String code = docInfoUrl.substring(docInfoUrl.lastIndexOf("_") + 1,docInfoUrl.lastIndexOf("."));

            String picLoc = "";
            try {
                picLoc = ImagesUtils.downImages("c:/images", head_img);
            }catch (Exception e){//如果String#substring()和String#lastIndexOf()出现问题、图片没有下载成功，将没有下载成功的url保存
                e.printStackTrace();
                errorList.add(docName + "||" + errorUrl);
            }

            //创建一个医生对象
            Doctor doc = new Doctor();
            doc.setHis_id(his_id);//医院名称
            doc.setDept_id(dept_id);//医生科室名称
            doc.setDoctor_name(docName);//医生姓名
            doc.setCode(code);//医生编码
            doc.setHead_img(picLoc);//医生头像位置
            doc.setSummary(summary);//医生介绍
            doc.setSummary_full(summary_full);//带标签的医生介绍

            String skill = TextUtils.regexText(summary, "擅长(.*?)。");//通过正则匹配获取summary中医生擅长、每个医院的风格不同、可能是专长、能够、对 等。
            doc.setSkill(skill);//医生擅长
            doc.setLevel(level);//医生的职称

            //在存入数据库之前检查数据是否正确
            System.out.println(doc.toString());
            //存入数据库中
//                    MysqlUtil.insertToDocter(doc);

        }

        System.out.println("爬取失败的网站：|没有下载成功的医生头像：");
        System.out.println(errorList);

    }

}
