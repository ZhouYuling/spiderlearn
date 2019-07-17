package com.gzhc365.utils;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ProxyIpPoolUpdate {

	public static void main(String[] args) {
		//创建定时器
		Timer timer = new Timer();
		System.out.println("程序开始 现在是："+ new Date().toString());
		//表示在3秒之后开始执行，并且每15秒执行一次
		timer.schedule(new MyTask(),3000,15000);
	}
}

class MyTask extends TimerTask{

//	private static Jedis jedis = new Jedis("127.0.0.1", 6379);
	private static Jedis jedis = RedisUtil.getJedis();

	//在run方法中的语句就是定时任务执行时运行的语句。
	public void run() {
		try {
			String htmlGBK = JavaSpider.getHtmlGBK("http://www.zdopen.com/ShortProxy/GetIP/?api=201907161031493930&akey=d4904b0850d2a16a&order=2&type=1");
			//站大爷短效优质代理、25块钱一天5个ip、每隔5秒中更新ip、ip存活时间未3-6分钟
			String[] ipPorts = htmlGBK.split(",");//可以设置返回格式，这里设置为以逗号分开，每个代理ip为ip:port形式。
			// 比如110.83.183.173:54957,120.35.200.102:39941,140.250.89.23:19799,112.47.113.90:20302,58.22.177.214:36073

			for (String ipPort : ipPorts) {//将代理ip存入redis中
				jedis.lpush("key2",ipPort);
			}

			while(jedis.llen("key2") > 10){//清除失效ip，保留10个有效
				jedis.rpop("key2");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			RedisUtil.returnResource(jedis);
		}

	}
}