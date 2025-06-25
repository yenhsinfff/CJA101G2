package com.lutu.cart.model;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lutu.cart.model.CartVO.CartKey;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService implements CartService_Interface {

	@Autowired
	CartRepository cr;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	ObjectMapper objectMapper;

	public static final String REDIS_KEY_PREFIX = "cart:";

	@Override
	public void addCart(HttpSession session, Integer memId, CartList item) {
		// TODO Auto-generated method stub

	}

	@Override
	public CartList getCart(HttpSession session, Integer memId) {

		// 判斷是否已登入會員
		if (memId == null) {
			CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);

			// 判斷是否有現有的購物車
			if (cart1 == null) {
				cart1 = new CartList();

				// 存入session
				session.setAttribute(REDIS_KEY_PREFIX, cart1);
			}

			return cart1;

		} else {
			String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

			// 從Redis Hash取得所有field-value
			Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);

			CartList cart2 = new CartList();

			cart2.setMemId(memId);

			if (entries != null && !entries.isEmpty()) {

				ObjectMapper map = new ObjectMapper();

				for (Object value : entries.values()) {

					try {
						// value是JSON字串，反序列化成CartVO
						CartVO cartVO = objectMapper.readValue(value.toString(), CartVO.class);

						if (cartVO.getMemId() == null) {
							cartVO.setMemId(memId); // 補充 memId
						}

						cart2.getCartList().add(cartVO);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

			return cart2;
		}

	}

	@Override
	public void updateCart(HttpSession session, Integer memId, CartVO item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCart(HttpSession session, Integer memId, CartKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearCart(HttpSession session, Integer memId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mergeCart(HttpSession session, Integer memId) {
		// TODO Auto-generated method stub

	}

}
