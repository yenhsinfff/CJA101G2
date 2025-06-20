package com.lutu.cart.connectionpool;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	// Singleton
	private static JedisPool pool = null;

	
	public JedisUtil() {
	}

	public static JedisPool getJedisPool() {

		synchronized (JedisUtil.class) {
			if (pool == null) {

				JedisPoolConfig config = new JedisPoolConfig();

				config.setMaxTotal(8);
				config.setMaxIdle(8);
				config.setMaxWaitMillis(10000);

				pool = new JedisPool(config, "220.134.26.206", 6379, 2000, "password", 1);
			}
		}
		return pool;
	}

	public static void shutdownJedisPool() {
		if (pool != null) {
			pool.destroy();
		}

	}

}
