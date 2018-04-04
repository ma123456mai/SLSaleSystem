package org.slsale.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisAPI {
	// rdeis的连接池对象
	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	// 保存值到键中
	public boolean set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 把数据对象返回到连接池
			returnResource(jedisPool, jedis);
		}

		return false;
	}

	// 验证某个键是否存在于redis中
	public boolean exist(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 把数据对象返回到连接池
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	// 根据某个键来获取相应的值
	public String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 把数据对象返回到连接池
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static void returnResource(JedisPool jedisPool, Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
}
