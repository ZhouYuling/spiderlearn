package com.gzhc365.spider.JDspider;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 22:18 2019/7/23 0023
 * @Modified by:
 */
public class Utils {

    /**
     * 专门用来访问httpget请求的方法
     *
     * @param httpGet httpGet对象
     * @return html页面
     * @throws IOException
     */
    public static String getHtml(HttpGet httpGet) throws IOException {
        String html = null;
        CloseableHttpClient hc = HttpClients.createDefault();
        CloseableHttpResponse res = hc.execute(httpGet);
        if (res.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = res.getEntity();
            html = EntityUtils.toString(entity, Charset.forName("utf-8"));
        }
        return html;
    }

}
