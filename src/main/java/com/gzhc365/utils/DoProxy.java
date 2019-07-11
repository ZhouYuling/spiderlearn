package com.gzhc365.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class DoProxy {

	public static void main(String[] args) {

		//西刺代理：https://www.xicidaili.com/
		//站大爷：http://ip.zdaye.com/
		try {
			String s = doProxy("111.230.196.209", 8080, "https://www.baidu.com/");
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String doProxy(String ip, int port, String url) throws IOException {

		// 1.创建POST请求对象
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Connection", "keep-alive");

		// 2.创建代理HTTP请求
		HttpHost proxy = new HttpHost(ip, port);
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setBufferSize(4128).build();
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient hc = HttpClients.custom().setDefaultConnectionConfig(connectionConfig)
				.setRoutePlanner(routePlanner).build();
		// 3.使用代理HttpClient发起请求
		CloseableHttpResponse res = hc.execute(httpPost);
		int statusCode = res.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		// 4.页面html
		return EntityUtils.toString(res.getEntity(), Charset.forName("utf-8"));

	}

}
