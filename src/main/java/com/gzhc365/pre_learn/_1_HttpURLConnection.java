package com.gzhc365.pre_learn;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 10:51 2019/7/17 0017
 * @Modified by:
 */
public class _1_HttpURLConnection {

    @Test
    public void getHtmlByGet() throws IOException {

        //1、准备要访问的URL
        String url = "http://www.gzhc365.com/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //2、设置Http协议的参数
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        //3、发起HTTP请求，获取状态码
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        //4、如果是正常相应，读取数据
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //打印页面信息
            System.out.println(response.toString());
        }

    }

    @Test
    public void getHtmlByPost() throws IOException {//用于ajax传入username和password登陆和post参数传入

        String url = "http://www.baidu.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

//        String urlParameters = "username=username&password=password";
//        String urlParameters = "username=username&password=password&reURL=http://www.gzhc365.com/";//并且重定向

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        if (responseCode == 200) {//如何请求正确，则获取html页面
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print the html
            System.out.println(response.toString());
        }

    }

}
