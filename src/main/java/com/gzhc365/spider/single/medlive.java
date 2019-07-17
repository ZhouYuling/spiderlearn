package com.gzhc365.spider.single;

import com.gzhc365.utils.JavaSpider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 11:38 2019/7/15 0015
 * @Modified by:
 */
public class medlive {

    public static void main(String[] args) {

        //四级
        //1.全部药物分类：http://drugs.medlive.cn/drugref/drugCateIndex.do
        //2.抗微生物药(大类)->β内酰胺类(中类)：http://drugs.medlive.cn/drugref/drugCate2nd.do?treeCode=H02#H02
        //3.青霉素类药(小类)：http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=H010101
        //4.药品详情：http://drugs.medlive.cn/drugref/html/18096.shtml

        //1.全部药物分类：http://drugs.medlive.cn/drugref/drugCateIndex.do
        try {
            String htmlGBK = JavaSpider.getHtmlGBK("http://drugs.medlive.cn/drugref/drugCateIndex.do");
            Document parse = Jsoup.parse(htmlGBK);
            Elements select = parse.select("div.table1 table");
            String[] big = {"西药","中药"};
            for (int i = 0; i < select.size(); i++) {
                System.out.println("=========");
//                System.out.println(element);
                Elements tbody_tr_td_a = select.get(i).select("tbody tr td a");
                for (Element element1 : tbody_tr_td_a) {
                    String href = element1.attr("href");
                    if("".equals(href)){
                        continue;
                    }

                    String name = element1.text();
                    String url = "http://drugs.medlive.cn/" + href;
//                    System.out.println(big[i] + "   " + name + "   " + url);
                    String htmlGBK1 = JavaSpider.getHtmlGBK(url);
                    Document parse1 = Jsoup.parse(htmlGBK1);
                    Elements select1 = parse1.select("div.drug_title");
                    Elements select2 = parse1.select("div.drug_list");
                    //select1.size() != select2.size()这个是不正常的
                    for (int j = 0; j < select1.size(); j++) {
                        String smallName = select1.get(j).text();
                        //首先判断其是否有子类
                        String text = select2.get(j).text();
                        if(!"".equals(text)){//有子类则直接根据子类的url访问，并且存储树形结构
                            Elements span = select2.get(j).select("span a");
                            for (Element element : span) {

                                String zlName = element.text();
                                String zlUrl = "http://drugs.medlive.cn/" + element.attr("href");
                                System.out.println(name + "  " + smallName + "   " + zlName + "   " + zlUrl);

                                Thread.sleep(1000);
                                System.out.println("----111---");

                                //TODO:解析列表、获取药品详细信息
                                String htmlGBK2 = JavaSpider.getHtmlGBK(zlUrl.replace("drugCate","drugCateLast"));
                                Document parse2 = Jsoup.parse(htmlGBK2);
                                Elements box_list = parse2.select("div.box-list div.box1");
                                for (Element element2 : box_list) {
                                    System.out.println("-------");
                                    String tempName = element2.select("div.medince-name a").text();
                                    String href1 = "http://drugs.medlive.cn/" + element2.select("div.medince-name a").attr("href");
                                    System.out.println(tempName + "  " + href1);

                                    String htmlGBK3 = JavaSpider.getHtmlUTF8(href1);
                                    Document parse3 = Jsoup.parse(htmlGBK3);
                                    Elements select3 = parse3.select("div.info-left div");
                                    for (Element element3 : select3) {
                                        String title1 = element3.select("div div.title").text().replace("：","").replace(":","");
                                        String cont = element3.select("div div.more-infomation").text();
                                        if("".equals(title1) || "".equals(cont)){
                                            continue;
                                        }
                                        System.out.println("******");
                                        System.out.println(title1  + "\r\n" + cont);
                                    }
                                    break;//TODO:实际运行是去掉
                                }
                                break;//TODO:实际运行是去掉
                            }
                        }else {
                            String code = "";
                            if(j < 10){
                                j++;
                                code = "0" + j;
                            }else{
                                j++;
                                code = "" + j;
                            }

                            String zlUrl = "http://drugs.medlive.cn/drugref/drugCateLast.do?treeCode=" + url.substring("http://drugs.medlive.cn/drugref/drugCate.do?treeCode=".length()) + code;
                            System.out.println(name + "   " + smallName + "   " + zlUrl);

                            //TODO:

                        }
                        //如果没有子类更具code和j的值进行访问
                        break;//TODO:实际运行是去掉
                    }
                    break;//TODO:实际运行是去掉
                }
                break;//TODO:实际运行是去掉
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
