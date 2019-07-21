package com.gzhc365.pre_learn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Author: ZhouYuling
 * @Description: Jsoup -- Java Soup
 * @Date: Created in 上午 11:17 2019/7/17 0017
 * @Modified by:
 */
public class _3_Jsoup {

    @Test
    public void parseHtmlString(){//一般情况下使用httpClient下载得到html的页面String后使用这个方法进行解析
        String html = "<html><head><title class=ndo>First parse</title></head>"
                + "<body><p id=desc>Parsed HTML into a doc.</p><p id=desc>Parsed HTML p2</p><p id=desc1>Parsed HTML p2</p>"
                + "<div class=text><strong>周末:</strong>放松一下</div>"
                + "</body></html>";
        Document doc = Jsoup.parse(html);//构建Document对象
        //方式一：jsoup中根据dom方法来解析
        Elements body = doc.getElementsByTag("body");//jsoup中根据dom方法来遍历Document对象
        System.out.println(body);

        Elements append = body.append("<p>我是新增加的</p>");
        System.out.println(append);

        //方式二：通过选择器语法来解析
        Elements head_title = doc.select("head title");
        System.out.println(head_title);
        Elements selectById = doc.select("p#desc");
        System.out.println(selectById);
        Elements selectByClass = doc.select("title.ndo");
        System.out.println(selectByClass);
        Elements select = doc.select("[p=desc]");
        System.out.println(select);
        Elements selectByAttr1 = doc.select("[id=desc]");//选择id=desc的属性
        System.out.println(selectByAttr1);
        Elements selectByAttr2 = doc.select("[id$=1]");//id后缀开始为1
        System.out.println(selectByAttr2);//还可以有包含属性值、属性开头、属性值匹配正则表达式来查找元素 等方式
        //更多选择器，请查看网页、多试试多总结多回顾
        // https://www.open-open.com/jsoup/selector-syntax.htm有详细讲解
        //https://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/index.html

        //Elements的遍历方式
        //方式一：
        Elements body_p = doc.select("body p");//组合选择器
        for (Element element : body_p) {
            System.out.println(element);
        }

        //方式二：
        for (int i = 0; i < body_p.size(); i++) {
            System.out.println(body_p.get(i));//body_p.get(i)获取Elements中第一个Element，注意这个操作可能会导致越界异常，处理好异常避免爬取工作异常退出
        }

        Elements texts = doc.select("body div.text");
        String text = texts.text();//获取元素内的文本内容
        String textGetTag = texts.html();//获取元素内的HTML内容
        String textGetTagOuter = texts.outerHtml();//获取元素外的HTML内容
        System.out.println(text + " " + textGetTag + " " + textGetTagOuter);
        String[] split = text.split(":");//获取标签外内容方式一
        System.out.println(split[0] + " " + split[1]);
        String newText = text.replace("周末:","");//获取标签外内容方式二
        System.out.println(newText);

    }

    @Test
    public void parseFragment(){
        String html = "<div><p>Lorem ipsum.</p>";
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();
        System.out.println(body);
        /**
         * <body>
         <div>
         <p>Lorem ipsum.</p>
         </div>
         </body>
         */
    }

    @Test
    public void parseHtml() throws IOException {
        Document doc = Jsoup.connect("https://www.baidu.com/").get();
        String title = doc.title();
        System.out.println(title);
    }

    @Test
    public void parseHtmlFile() throws IOException {
        File input = new File( System.getProperty("user.dir") + "\\src\\main\\resources\\index.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        System.out.println(doc);
    }

    //更多信息请参看https://www.open-open.com/jsoup/
    //对于xpath的解析请查看 _11_JsoupXpath


}
