package com.gzhc365.pre_learn;

import com.gzhc365.utils.JavaSpider;
import org.junit.Test;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 10:11 2019/7/18 0018
 * @Modified by:
 */
public class _11_JsoupXpath {

    @Test
    public void test() throws Exception {

        String htmlGBK = JavaSpider.getHtmlGBK("http://www.dataguru.cn/");
        JXDocument jxd = JXDocument.create(htmlGBK);
        List<JXNode> jxNodeText = jxd.selN("//*[@class=\"topnews\"]/text()");
        System.out.println(jxNodeText);

        List<JXNode> jxNodeHtml = jxd.selN("//*[@class=\"topnews\"]");
        System.out.println(jxNodeHtml);

        //更多请查看http://jsoupxpath.wanghaomiao.cn/#cf99f59a58a55e22717859ea1916c42
        //https://blog.csdn.net/weixin_42022555/article/details/82775916

    }

}
