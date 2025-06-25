package com.lutu.cart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lutu.cart.model.CartVO.CartKey;
import com.lutu.cart.model.dto.CartDTO_res;
import com.lutu.colorList.model.ColorListRepository;
import com.lutu.colorList.model.ColorListVO;
import com.lutu.shopProd.model.ShopProdRepository;
import com.lutu.shopProd.model.ShopProdVO;
import com.lutu.specList.model.SpecListRepository;
import com.lutu.specList.model.SpecListVO;

import jakarta.servlet.http.HttpSession;



@Transactional
@Service
public class CartService implements CartService_Interface {

	@Autowired
	CartRepository cr;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ShopProdRepository spr;

	@Autowired
	ColorListRepository clr;

	@Autowired
	SpecListRepository slr;

	public static final String REDIS_KEY_PREFIX = "cart:";

	// 驗證數量必須大於0
	private void checkQtyValid(Integer cartProdQty) {
		if (cartProdQty == null || cartProdQty <= 0)
			throw new IllegalArgumentException("商品數量必須大於0");
	}

	// VO轉DTO
	public CartDTO_res toCartDTO(CartVO cartVO) {
		CartDTO_res dto = new CartDTO_res();

		dto.setMemId(cartVO.getMemId());
		dto.setProdId(cartVO.getProdId());
		dto.setProdColorId(cartVO.getProdColorId());
		dto.setProdSpecId(cartVO.getProdSpecId());
		dto.setCartProdQty(cartVO.getCartProdQty());

		// 名稱
		dto.setProdName(spr.findById(cartVO.getProdId()).map(ShopProdVO::getProdName).orElse("未知的商品"));
		dto.setColorName(clr.findById(cartVO.getProdColorId()).map(ColorListVO::getColorName).orElse("未知的顏色"));
		dto.setSpecName(slr.findById(cartVO.getProdSpecId()).map(SpecListVO::getSpecName).orElse("未知的規格"));

		return dto;
	}

	@Override
	public CartDTO_res addCart(HttpSession session, Integer memId, Integer prodId, Integer prodColorId,
			Integer prodSpecId, Integer cartProdQty) {

		// 確認數量> 0
		checkQtyValid(cartProdQty);

		// 組成cartKey和cartVO
		CartVO.CartKey cartKey = new CartVO.CartKey(memId, prodId, prodColorId, prodSpecId);
		CartVO item = new CartVO(memId, prodId, prodColorId, prodSpecId, cartProdQty);

		item.setCartKey(cartKey);

		// 新增購物車
		if (memId == null) {

			return addCartForGuest(session, cartKey, item, cartProdQty);
		} else {// 已登入
			return addCartForMember(memId, cartKey, item, cartProdQty);
		}
	}

	// 處理未登入（Session）購物車
	private CartDTO_res addCartForGuest(HttpSession session, CartVO.CartKey cartKey, CartVO item, int cartProdQty) {
		CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);

		if (cart1 == null) {
			cart1 = new CartList();

			// 存入session
			session.setAttribute(REDIS_KEY_PREFIX, cart1);
		}
		// 如果已經有同樣商品（同key），就數量累加
		// .findFirst()會回傳Optional<CartVO>，如果有找到就包在Optional裡
		Optional<CartVO> exist = cart1.getCartList().stream().filter(vo -> vo.getCartKey().equals(cartKey)).findFirst();
		// 判斷購物車裡面有沒有該商品
		if (exist.isPresent()) {
			exist.get().setCartProdQty(exist.get().getCartProdQty() + cartProdQty);
		} else {// 沒有找到該商品，直接加入
			cart1.getCartList().add(item);
		}

		// 再 setAttribute確保 session 內容為最新
		session.setAttribute(REDIS_KEY_PREFIX, cart1);

		return toCartDTO(item);
	}

	// 處理已登入（Redis）購物車
	private CartDTO_res addCartForMember(Integer memId, CartVO.CartKey cartKey, CartVO item, int qty) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		String hashKey = item.getProdId() + ":" + item.getProdColorId() + ":" + item.getProdSpecId();

		try {// 檢查購物車有無相同商品
			Object existItem = redisTemplate.opsForHash().get(redisKey, hashKey);
			if (existItem != null && existItem instanceof String) {
				String existJson = (String) existItem;
				// 用objectMapper把json字串轉回CartVO物件
				CartVO exist = objectMapper.readValue(existJson, CartVO.class);
				item.setCartProdQty(item.getCartProdQty() + exist.getCartProdQty());
			}
			// 把item（CartVO物件）轉成json字串，方便存進Redis
			String itemJson = objectMapper.writeValueAsString(item);
			// 用redisKey和hashKey，把最新的itemJson存進Redis
			redisTemplate.opsForHash().put(redisKey, hashKey, itemJson);

		} catch (Exception e) {
			throw new RuntimeException("新增購物車時發生錯誤：" + e.getMessage(), e);
		}

		return toCartDTO(item);

	}

	@Override
	public List<CartDTO_res> getCart(HttpSession session, Integer memId) {
		List<CartDTO_res> dtoList = new ArrayList<>();

		// 判斷是否已登入會員
		if (memId == null) {
			CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);

			// 檢查session裡有沒有購物車（CartList）物件
			if (cart1 == null) {
				cart1 = new CartList();

				// 存入session
				session.setAttribute(REDIS_KEY_PREFIX, cart1);
			}

			for (CartVO cartVO : cart1.getCartList()) {
				dtoList.add(toCartDTO(cartVO));
			}

		} else {
			String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

			// 從Redis Hash取得所有field-value
			Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);

			if (entries != null && !entries.isEmpty()) {

				ObjectMapper map = new ObjectMapper();

				for (Object value : entries.values()) {

					try {
						// value是JSON字串，反序列化成CartVO
						CartVO cartVO = objectMapper.readValue(value.toString(), CartVO.class);

						if (cartVO.getMemId() == null) {
							cartVO.setMemId(memId); // 補充 memId
						}

						dtoList.add(toCartDTO(cartVO));

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		}

		return dtoList;

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
