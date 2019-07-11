package com.gzhc365.utils;

import com.gzhc365.vo.MyException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;


public class JavaSpider {

	private static HttpEntity getHttpEntity(String html) throws Exception {
		/** get请求带参数、方式一 **/
//		ArrayList<BasicNameValuePair> list2 = new ArrayList<BasicNameValuePair>();
//		list2.add(new BasicNameValuePair("login_access_token", "Java"));
//		list2.add(new BasicNameValuePair("subSource", "0"));
//		String params = EntityUtils.toString(new UrlEncodedFormEntity(list2));
//		HttpGet httpGet = new HttpGet(html + "?" + params);
		/** get请求带参数、方式二 **/
//		HttpPost httpPost = new HttpPost(html + "?login_access_token=Java&subSource=0");

		HttpGet indexHttpGet = new HttpGet(html);
		//设置User-Agent
		indexHttpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
		//设置其他参数
		indexHttpGet.addHeader(new BasicHeader("Content-Type", "text/plain"));
		//设置cookie
		String cookie = "ASP.NET_SessionId=bzpfihvzxpiehxipx0zavouu";
		indexHttpGet.addHeader(new BasicHeader("Cookie", cookie));
		CloseableHttpClient indexHttpClient = HttpClients.createDefault();
		CloseableHttpResponse indexResponse = indexHttpClient.execute(indexHttpGet);
		int statusCode = indexResponse.getStatusLine().getStatusCode();
		if(200 != statusCode) {//
//			throw new Exception(statusCode + "错误，没有正确爬取网页");
			throw new MyException(html,statusCode + "错误，没有正确爬取网页");
		}
		return indexResponse.getEntity();
	}

	/**
	 * 爬取网页信息
	 * @param html 需要爬取的网页
	 * @return 返回GBK编码的文本
	 */
	public static String getHtmlGBK(String html) throws Exception {
		return EntityUtils.toString(getHttpEntity(html),Charset.forName("GBK"));
	}

	/**
	 * 爬取网页信息
	 * @param html 需要爬取的网页
	 * @return 返回UTF8编码的文本
	 */
	public static String getHtmlUTF8(String html) throws Exception {
		return EntityUtils.toString(getHttpEntity(html),Charset.forName("utf-8"));
	}

	//===========================================================
//					post请求和get请求相似
	//===========================================================
	public static String postHtml(String html) throws Exception {

		HttpPost indexHttpPost = new HttpPost(html);
		//设置post参数
//		ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
//		arrayList.add(new BasicNameValuePair("login_access_token", "Java"));
//		arrayList.add(new BasicNameValuePair("subSource", "0"));
//		indexHttpPost.setEntity(new UrlEncodedFormEntity(arrayList));

		//设置User-Agent
		indexHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
		//设置其他参数
//		indexHttpPost.addHeader(new BasicHeader("Content-Type", "text/plain"));
		indexHttpPost.setHeader("Content-Type", "text/plain");
		//设置cookie
		String cookie = "UM_distinctid=16b8848151f302-0e4dd4ebfb9c77-37c143e-1fa400-16b8848152046b;";
		indexHttpPost.addHeader(new BasicHeader("Cookie", cookie));
		CloseableHttpClient indexHttpClient = HttpClients.createDefault();
		CloseableHttpResponse indexResponse = indexHttpClient.execute(indexHttpPost);
		int statusCode = indexResponse.getStatusLine().getStatusCode();
		if(200 != statusCode) {//
			throw new MyException(html,statusCode + "错误，没有正确爬取网页");
		}
		HttpEntity indexEntity = indexResponse.getEntity();
//		System.out.println(indexEntity);
		// 4.将entity转换成字符串(html)
		String htmlText = null;
		try {
			htmlText = EntityUtils.toString(indexEntity,Charset.forName("GBK"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException(html,"html文件转换格式错误");
		}
//		return EncodeUtil.unicodeToString(html);
		return htmlText;
	}
	
	
	

}
