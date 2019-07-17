package com.gzhc365.before_spider;

import com.gzhc365.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 16:31 2019/7/11 0011
 * @Modified by:
 */
public class removeDuplicatedUrl {

    //1.Set集合

    //2.Redis中的set
    public void removeDupUrlByRedis(){//取出key为medUrl_other中的所有值、放入key为medUrl_other_bak1的set中
        Jedis jedis = RedisUtil.getJedis();
        List<String> lrange = jedis.lrange("medUrl_other", 0, jedis.llen("medUrl_other"));
        for (String string : lrange) {
            jedis.sadd("medUrl_other_bak1", string);
        }
    }

}
