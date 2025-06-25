package com.lutu.cart.controller;

import java.util.HashMap;
import java.util.Map;

import com.lutu.cart.connectionpool.JedisUtil;
import com.lutu.cart.model.CartVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

public class TestCart {

	private static Pool<Jedis> pool = null;

	public static void main(String[] args) {
		pool = JedisUtil.getJedisPool();
		Jedis jedis = pool.getResource();
		
	
		

//		CartVO cart1 = new CartVO(10000001, 1, 3, 3, 2);
//		CartVO cart2 = new CartVO(10000001, 2, 1, 2, 1);
//		CartVO cart3 = new CartVO(10000004, 3, 2, 1, 3);
//		CartVO cart4 = new CartVO(10000008, 1, 3, 3, 1);
		
		Map<String, String> cartMap = new HashMap<>();
		Map<String, String> cartMap4 = new HashMap<>();
		Map<String, String> cartMap8 = new HashMap<>();
		cartMap.put("1:3:3", "{\"memId\":10000001,\"prodId\":1,\"prodColorId\":3,\"prodSpecId\":3,\"cartProdQty\":2}");
		cartMap.put("2:1:2", "{\"memId\":10000001,\"prodId\":2,\"prodColorId\":1,\"prodSpecId\":2,\"cartProdQty\":1}");
		cartMap4.put("3:2:1", "{\"memId\":10000004,\"prodId\":3,\"prodColorId\":2,\"prodSpecId\":1,\"cartProdQty\":5}");
		cartMap8.put("1:3:3", "{\"memId\":10000008,\"prodId\":1,\"prodColorId\":3,\"prodSpecId\":3,\"cartProdQty\":10}");

		
		jedis.hset("cart:10000001", cartMap);
		jedis.hset("cart:10000004", cartMap4);
		jedis.hset("cart:10000008", cartMap8);


//		List<CartVO> cartMem1 = new ArrayList();
//		List<CartVO> cartMem4 = new ArrayList();
//		List<CartVO> cartMem8 = new ArrayList();
//
//
//		cartMem1.add(cart1);
//		cartMem1.add(cart2);
//		cartMem4.add(cart3);
//		cartMem8.add(cart4);
//
//
//		String Array1 = new JSONArray(cartMem1).toString();
//		String Array4 = new JSONArray(cartMem4).toString();
//		String Array8 = new JSONArray(cartMem8).toString();
//		jedis.set("member1", Array1);
//		jedis.set("member4", Array4);
//		jedis.set("member8", Array8);

//		System.out.println(jedis.get("member1"));
//		System.out.println(jedis.get("member4"));
//		System.out.println(jedis.get("member8"));

		jedis.close();
		JedisUtil.shutdownJedisPool();

	}

}
