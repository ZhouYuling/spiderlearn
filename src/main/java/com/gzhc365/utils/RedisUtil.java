package com.gzhc365.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class RedisUtil {

	// Redis服务器IP
	private static String ADDR = "127.0.0.1";

	// Redis的端口号
	private static int PORT = 6379;//默认端口

	// 访问密码
//	private static String AUTH = "";

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
//			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);//没有设置密码
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 *
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 *
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 返回
	 * @param listName 需要备份的list的key
	 * @return 返回备份好的list的新key
	 */
	public static String backupList(String listName){
		Jedis jedis = RedisUtil.getJedis();
		List<String> lrange = jedis.lrange(listName, 0, jedis.llen(listName));
		int flag = 1;
		while(jedis.exists(listName + "_bakup_" + flag)){
			flag ++ ;
		}
		String backupName = listName + "_bakup_" + flag;
		for (String string : lrange) {
			jedis.rpush(backupName, string);
		}
		return backupName;
	}

	/**
	 * 设置
	 * @param setName 需要备份的set的key值
	 * @return 返回备份好的新的set的值
	 */
	public static String backupSet(String setName){
		Jedis jedis = RedisUtil.getJedis();
		Set<String> smem = jedis.smembers(setName);
		int flag = 1;
		while(jedis.exists(setName + "_bakup_" + flag)){
			flag ++ ;
		}
		String backupName = setName + "_bakup_" + flag;
		for (String string : smem) {
			jedis.sadd(backupName,string);
		}
		return backupName;
	}

	public static String listRemoveDupcate(String listName){
		Jedis jedis = RedisUtil.getJedis();
		List<String> lrange = jedis.lrange(listName, 0, jedis.llen(listName));
		HashSet<String> hashSet = new HashSet<>(lrange);
		int flag = 1;
		while(jedis.exists(listName + "_set_" + flag)){
			flag ++ ;
		}
		String setName = listName + "_set_" + flag;
		for (String s : hashSet) {
			jedis.lpush(setName,s);
		}
		return setName;
	}



}
