package com.gzhc365.spider;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/**
 * 更多信息请参看http://ai.baidu.com/docs#/OCR-Java-SDK/top
 */
public class BaiduOrcSample {
    //设置APPID/AK/SK
    //这个可以自己去百度上申请、每天都有免费次数的
    private static final String APP_ID = "16438814";
    private static final String API_KEY = "7zWsfy40f2hImD2Cm22FIn1R";
    private static final String SECRET_KEY = "7xfzDAyCOZmk4d9ovGrr1H33DFh2S1d1";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", System.getProperty("user.dir") + "src\\main\\resources\\log4j.properties");

        // 调用接口
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\pic\\test.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}
