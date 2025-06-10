package com.lutu.cart.connectionpool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

public class JedisPool {
	private static Pool<Jedis> pool = null;
	
	public static void main(String[] args) {
		pool = JedisUtil.getJedisPool();
		Jedis jedis = pool.getResource();
		System.out.println(jedis.ping());
		
		jedis.close();
		JedisUtil.shutdownJedisPool();
		
	}

}
