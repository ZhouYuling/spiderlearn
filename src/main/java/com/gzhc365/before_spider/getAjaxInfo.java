package com.gzhc365.before_spider;

import com.gzhc365.utils.TextUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 9:30 2019/7/11 0011
 * @Modified by:
 */
public class getAjaxInfo {

    /**步骤**/
    //1.通过抓包、获取ajax请求url
    //2.以谷歌浏览器为例，F12->Network->clear->点击异步链接以后找到异步包DrugUtils.showDetail.dwr
    // ->点击，Preview查看返回结果->Headers中General Request URL异步请求URL->并在Request中找到ajax请求参数
    //3.F12选择点击按钮、查看是否有相应的参数

    /**相当于调用了公司核心平台的接口、为了希望有正确的返回值，那么必须有正确的请求参数、
     * 怎么获取这些请求参数？自己去找规律了**/

    //丁香园的例子
    public static void main(String[] args) {

        String dxyInfo = getDxyInfo();
        System.out.println(dxyInfo);

    }

    /**除了ajax异步请求、有些网站还是js加载，如果对js不是特别熟悉，还是使用selenium这种插件吧、
     * 同样使用selenium也是可以绕过ajax、直接加载页面**/

    private static String getDxyInfo() {
        //异步请求URL
        String ajaxRequestHtml = "http://drugs.dxy.cn/dwr/call/plaincall/DrugUtils.showDetail.dwr";
        //showDrugDetail(this,89790,4)
        //配置异步请求参数、确定哪些是重要的参数
        HttpPost indexHttpPost = new HttpPost(ajaxRequestHtml);
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        arrayList.add(new BasicNameValuePair("callCount", "1"));
        arrayList.add(new BasicNameValuePair("page", ""));
        arrayList.add(new BasicNameValuePair("httpSessionId", ""));
        arrayList.add(new BasicNameValuePair("scriptSessionId", ""));//这个参数可以保持空缺
        arrayList.add(new BasicNameValuePair("c0-scriptName", "DrugUtils"));
        arrayList.add(new BasicNameValuePair("c0-methodName", "showDetail"));
        arrayList.add(new BasicNameValuePair("c0-id", "0"));
        arrayList.add(new BasicNameValuePair("c0-param0", "number:" + 89790));//***重要参数***
        arrayList.add(new BasicNameValuePair("c0-param1", "number:" + 4));//***重要参数***
        arrayList.add(new BasicNameValuePair("batchId", "2"));//这个值可以随便取，但是不能空缺
        try {
            //将参数塞入post中
            indexHttpPost.setEntity(new UrlEncodedFormEntity(arrayList));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //添加Post请求头信息、防止被症断为爬虫、User-Agent可以设置为一个数据池、轮询或者随机抽取不同的User-Agent
        indexHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
        //设置cookies,如下可以继续在分号后面加其他cookie信息
        String cookie = "DRUGSSESSIONID=3736A94D6A22C4686E16A83F34282E6F-n1;";//***跳过登陆***，
        indexHttpPost.addHeader(new BasicHeader("Cookie", cookie));
        indexHttpPost.setHeader("Content-Type", "text/plain");
        HttpEntity entity = indexHttpPost.getEntity();
//		System.out.println(entity);
        CloseableHttpClient indexHttpClient = HttpClients.createDefault();
        CloseableHttpResponse indexResponse = null;

        try {
            //发送Post请求
            indexResponse = indexHttpClient.execute(indexHttpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查看请求返回结果、200表示正常
        int statusCode = indexResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity indexEntity = indexResponse.getEntity();
        //获取页面html文本数据
        String html = null;
        try {
            //将文本数据转换成GBK编码
            html = EntityUtils.toString(indexEntity, Charset.forName("GBK"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将unicode编码转换成String
        return TextUtils.unicodeToString(html);
    }

}
