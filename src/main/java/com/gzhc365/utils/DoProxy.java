package com.gzhc365.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.charset.Charset;

public class DoProxy {

	//USER_AGENT池，可以自己添加
	private final static String[] USER_AGENT = {
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
			"Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.9.168 Version/11.50",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; Tablet PC 2.0; .NET4.0E)",
			"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
			"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16",
			"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 UBrowser/4.0.3214.0 Safari/537.36",
			"Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36" ,
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.33 Safari/534.3 SE 2.X MetaSr 1.0",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)",
			"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1 QQBrowser/6.9.11079.201",
			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E) QQBrowser/6.9.11079.201",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
			"Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.9.168 Version/11.50"
	};

	//Redis中代理IP的数量，默认为5
	private static final int IP_COUNT = 5;

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

	/**
	 * 代理执行
	 * @param ip 代理ip
	 * @param port 代理端口
	 * @param url 请求url
	 * @return 返回url的html页面
	 * @throws IOException
	 */
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

	/**
	 * 使用Redis中的代理池
	 * @param url 访问的url
	 * @return url的html页面
	 * @throws Exception 自定义异常，如果Redis代理池的数量小于IP_COUNT的时候，抛异常
	 */
	public static String doProxy(String url) throws Exception{

		Jedis jedis = RedisUtil.getJedis();
		String ip = "";
		int port = 0;
		if (jedis != null) {
			String ipProxy = jedis.lindex("key2", (int) (Math.random() * IP_COUNT));//使用存储在Redis里面的代理IP池，这个地方为使用0-4序号的代理ip
			ip = ipProxy.split(":")[0];
			port = Integer.parseInt(ipProxy.split(":")[1]);
		}
		RedisUtil.returnResource(jedis);
		if(!"".equals(ip) && port != 0){
			return doProxy(ip,port,url);
		}else {
			throw new Exception("Redis中代理ip的数量少于：" + IP_COUNT);
		}
	}


}
