package com.gzhc365.pre_learn;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 11:06 2019/7/17 0017
 * @Modified by:
 */
public class _2_HttpClient {

    @Test
    public void getHtmlByGet() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.gzhc365.com/");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        System.out.println(response.getStatusLine());
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, Charset.forName("utf-8"));
            System.out.println(html);
        }
        response.close();
    }

    @Test
    public void getHtmlByPost() throws IOException { //https://blog.csdn.net/pengjunlee/article/details/85257369
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://login.taobao.com/member/login.jhtml");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        httpPost.addHeader("Host", "login.taobao.com");
        httpPost.addHeader("Referer", "https://sycm.taobao.com/custom/login.htm?_target=http://sycm.taobao.com/portal/home.htm");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("TPL_username", "15510331203"));
        nvps.add(new BasicNameValuePair("TPL_password_2", "****"));
        nvps.add(new BasicNameValuePair("TPL_redirect_url", "http://sycm.taobao.com/portal/home.htm"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        System.out.println(response.getStatusLine());
        //如果登录之后需要重定向，获取重定向的值
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header);
        }
        if (response.getStatusLine().getStatusCode() == 302) {
            Header[] locations = response.getHeaders("Location");
            String url = locations[0].getValue();//重定向网址
            System.out.println(url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            if (response1.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response1.getEntity();
                String html = EntityUtils.toString(entity, Charset.forName("utf-8"));
                System.out.println(html);
                //获取cookies
                CookieStore cookieStore = new BasicCookieStore();
                List<Cookie> cookies = cookieStore.getCookies();
                StringBuffer cookie = new StringBuffer();
                for (Cookie c : cookies) {
                    cookie.append(c.getName()).append("=").append(c.getValue()).append(";");
                    if (c.getName().equals("_tb_token_")) {
                        String tokenStr = c.getValue();
                        System.out.println(tokenStr);
                    }
                }
                System.out.println(cookie.toString());
            }
            response1.close();
        }
        response.close();
    }

    @Test
    public void getHtmlByGetEasyWay() throws IOException {
        String html = Request.Get("http://www.gzhc365.com/").execute().returnContent().asString(Charset.forName("UTF-8"));
        System.out.println(html);
    }

    @Test
    public void getHtmlByPostEasyWay() throws IOException {

        HttpResponse response = Request.Post("http://www.gzhc365.com/")
                .bodyForm(Form.form().add("username", "username").add("password", "password").add("reURL","http://www.gzhc365.com/wisdomhis.html").build())
                .execute().returnResponse();
        if (response.getStatusLine().getStatusCode() == 302) {
            Header[] locations = response.getHeaders("Location");
            String url = locations[0].getValue();
            System.out.println(url);
            String html =  Request.Get(url).execute().returnContent().asString(Charset.forName("UTF-8"));
            System.out.println(html);
        }
    }

}
