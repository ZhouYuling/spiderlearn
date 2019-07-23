package com.gzhc365.spider.distributed;

import static com.gzhc365.spider.distributed._1_getBigClassiUrl.getBigClassiUrl;
import static com.gzhc365.spider.distributed._2_getSmallClassiUrl.getSmallClassiUrl;
import static com.gzhc365.spider.distributed._3_getDeDetailList.getDeDetailList;
import static com.gzhc365.spider.distributed._4_parsePageUrl.parseMedicineUrl;
import static com.gzhc365.spider.distributed._5_parseMedDetail.parseMedicineDetail;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 15:12 2019/7/15 0015
 * @Modified by:
 */
public class medlive_main {

    //调度中心、控制爬虫爬取速度
    private static final int DeDetailList_ThreadNum = 5;//getDeDetailList线程个数
    private static final int MedicineUrl_ThreadNum = 5;//parseMedicineUrl线程个数
    private static final int MedicineDetail_ThreadNum = 5;//parseMedicineDetail线程个数

    //在medlive中使用四个分类提取url、但是在多线程使用ip代理的情况下，访问3情况会发送多次请求去轮询page=?这些页面，所以将3拆分出来
    //1.全部药物分类：http://drugs.medlive.cn/drugref/drugCateIndex.do
    //2.抗微生物药(大类)->β内酰胺类(中类)：http://drugs.medlive.cn/drugref/drugCate2nd.do?treeCode=H02#H02
    //3.青霉素类药(小类)：http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H010101
    //4.药品详情：http://drugs.medlive.cn/drugref/html/18096.shtml

    public static void main(String[] args) {

        //程序入口URL
        String enterUrl = "http://drugs.medlive.cn/drugref/drugCateIndex.do";
        getBigClassiUrl(enterUrl);
        getSmallClassiUrl();//获取中小类url
        getDeDetailList(DeDetailList_ThreadNum);//获取所有药品page=?
        parseMedicineUrl(MedicineUrl_ThreadNum);//获取药品详情页url
        parseMedicineDetail(MedicineDetail_ThreadNum);//解析药品详情页

    }

}

