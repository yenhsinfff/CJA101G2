package com.lutu.cart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lutu.cart.model.dto.CartDTO_req;
import com.lutu.cart.model.dto.CartDTO_res;
import com.lutu.colorList.model.ColorListRepository;
import com.lutu.colorList.model.ColorListVO;
import com.lutu.prodSpecList.model.ProdSpecListRepository;
import com.lutu.prodSpecList.model.ProdSpecListVO;
import com.lutu.shopProd.model.ShopProdRepository;
import com.lutu.shopProd.model.ShopProdVO;
import com.lutu.specList.model.SpecListRepository;
import com.lutu.specList.model.SpecListVO;

import jakarta.annotation.Resource;

@Transactional
@Service
public class CartService {

	@Autowired
	CartRepository cr;

	@Resource(name = "redisTemplate")
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ShopProdRepository spr;

	@Autowired
	ColorListRepository clr;

	@Autowired
	SpecListRepository slr;

	@Autowired
	ProdSpecListRepository psr;

	public static final String REDIS_KEY_PREFIX = "cart:";

	// 驗證數量必須大於0
	private void checkQtyValid(Integer cartProdQty) {
		if (cartProdQty == null || cartProdQty <= 0)
			throw new IllegalArgumentException("商品數量必須大於0");
	}

	// VO轉DTO
	public CartDTO_res toCartDTO(CartVO cartVO) {
		CartDTO_res dto = new CartDTO_res();

		dto.setMemId(cartVO.getMemId() != null ? cartVO.getMemId() : cartVO.getCartKey().getMemId());
		dto.setProdId(cartVO.getProdId());
		dto.setProdColorId(cartVO.getProdColorId());
		dto.setProdSpecId(cartVO.getProdSpecId());
		dto.setCartProdQty(cartVO.getCartProdQty());

		// 名稱
		dto.setProdName(spr.findById(cartVO.getProdId()).map(ShopProdVO::getProdName).orElse("未知的商品"));
		dto.setColorName(clr.findById(cartVO.getProdColorId()).map(ColorListVO::getColorName).orElse("未知的顏色"));
		dto.setSpecName(slr.findById(cartVO.getProdSpecId()).map(SpecListVO::getSpecName).orElse("未知的規格"));

		ProdSpecListVO.CompositeDetail2 key = new ProdSpecListVO.CompositeDetail2(cartVO.getProdId(),
				cartVO.getProdSpecId());
		// 價格
		dto.setProdPrice(psr.findById(key).map(ProdSpecListVO::getProdSpecPrice).orElse(0));

		return dto;
	}

	public CartDTO_res addCart(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId,
			Integer cartProdQty) {

		// 確認數量> 0
		checkQtyValid(cartProdQty);

		// 組成cartKey和cartVO
		CartVO.CartKey cartKey = new CartVO.CartKey(memId, prodId, prodColorId, prodSpecId);
		CartVO item = new CartVO(memId, prodId, prodColorId, prodSpecId, cartProdQty);

		item.setCartKey(cartKey);

		// 新增購物車
		if (memId != null) {

			return addCartForMember(memId, cartKey, item, cartProdQty);

		} else {// 已登入

			throw new RuntimeException("未登入儲存在前端");

		}
	}

	// 處理已登入（Redis）購物車
	private CartDTO_res addCartForMember(Integer memId, CartVO.CartKey cartKey, CartVO item, int qty) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		String hashKey = item.getProdId() + ":" + item.getProdColorId() + ":" + item.getProdSpecId();

		try {// 檢查購物車有無相同商品
			Object value = redisTemplate.opsForHash().get(redisKey, hashKey);
			CartVO existItem = null;
			if (value instanceof CartVO) {
				existItem = (CartVO) value;
			} else if (value instanceof Map) {
				existItem = objectMapper.convertValue(value, CartVO.class);
			}
			if (existItem != null) {
				item.setCartProdQty(item.getCartProdQty() + existItem.getCartProdQty());
			}

			// 直接存 CartVO 物件
			redisTemplate.opsForHash().put(redisKey, hashKey, item);

		} catch (

		Exception e) {
			throw new RuntimeException("新增購物車時發生錯誤：" + e.getMessage(), e);
		}

		return toCartDTO(item);
	}

	public List<CartDTO_res> getCart(Integer memId) {
		List<CartDTO_res> dtoList = new ArrayList<>();

		// 判斷是否已登入會員
		if (memId == null) {

			throw new RuntimeException("未登入儲存在前端");

		} else {
			String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

			try {
				// 從Redis Hash取得所有field-value
				Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);

				if (entries != null && !entries.isEmpty()) {

					for (Object value : entries.values()) {
						CartVO cartVO;
						if (value instanceof CartVO) {
							cartVO = (CartVO) value;

							// 預設反序列化回來就是 LinkedHashMap，必須用 objectMapper 轉型
						} else if (value instanceof Map) {

							cartVO = objectMapper.convertValue(value, CartVO.class);
						} else {
							System.out.println("資料格式錯誤：" + value);
							continue; // 避免後續出現型態轉換錯誤或 NullPointerException
						}

						dtoList.add(toCartDTO(cartVO));

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("redis error!");
			}
			return dtoList;
		}

	}

	public CartDTO_res updateCart(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId,
			Integer cartProdQty) {
		// 確認數量> 0
		checkQtyValid(cartProdQty);

		// 組成cartKey和cartVO
		CartVO.CartKey cartKey = new CartVO.CartKey(memId, prodId, prodColorId, prodSpecId);
		CartVO item = new CartVO(memId, prodId, prodColorId, prodSpecId, cartProdQty);

		item.setCartKey(cartKey);

		// 新增購物車
		if (memId == null) {

			throw new RuntimeException("未登入儲存在前端");

		} else {// 已登入

			return updateCartForMember(memId, cartKey, item);
		}

	}

	// 會員（Redis）修改購物車
	private CartDTO_res updateCartForMember(Integer memId, CartVO.CartKey cartKey, CartVO item) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		String hashKey = item.getProdId() + ":" + item.getProdColorId() + ":" + item.getProdSpecId();

		try {// 檢查購物車有無相同商品

			// 先刪除同一商品但不同顏色/規格的商品
			Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);
			if (entries != null) {
				for (Object keyObject : entries.keySet()) {
					String key = keyObject.toString();
					String[] parts = key.split(":");
					// 格式正確（有三段）、商品編號等於 prodId的刪除
					if (parts.length == 3 && parts[0].equals((item.getProdId()).toString())) {
						redisTemplate.opsForHash().delete(redisKey, key);
					}
				}
			}

			// 直接把新數量覆蓋到Redis（等於修改購物車內容）
			redisTemplate.opsForHash().put(redisKey, hashKey, item);

		} catch (Exception e) {
			throw new RuntimeException("修改購物車時發生錯誤: " + e.getMessage(), e);
		}

		return toCartDTO(item);

	}

	public List<CartDTO_res> removeCart(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId) {
		// 組成cartKey
		CartVO.CartKey cartKey = new CartVO.CartKey(memId, prodId, prodColorId, prodSpecId);

		// 判斷會員ID是否為空
		if (memId == null) {

			throw new RuntimeException("未登入儲存在前端");

		} else {

			removeCartForMember(memId, cartKey);
		}

		return getCart(memId);
	}
	

	// 會員（Redis）購物車移除細項
	private CartDTO_res removeCartForMember(Integer memId, CartVO.CartKey cartKey) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		// 組合hashKey（商品唯一識別：商品ID:顏色ID:規格ID
		String hashKey = cartKey.getProdId() + ":" + cartKey.getProdColorId() + ":" + cartKey.getProdSpecId();

		// 從Redis取出對應的商品
		Object value = redisTemplate.opsForHash().get(redisKey, hashKey);

		CartVO existItem = null;

		if (value instanceof CartVO) {
			existItem = (CartVO) value;
		} else if (value instanceof Map) {
			existItem = objectMapper.convertValue(value, CartVO.class);
		}

		// 若商品不存在，拋出異常
		if (existItem == null) {
			throw new IllegalArgumentException("購物車無該商品");
		}

		// 從Redis中刪除該商品
		redisTemplate.opsForHash().delete(redisKey, hashKey);

		return toCartDTO(existItem);

	}

	public List<CartDTO_res> clearCart(Integer memId) {

		if (memId == null) {

			throw new RuntimeException("未登入儲存在前端");

		} else {// 有登入會員

			String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

			// 從Redis中刪除所有購物車內容
			redisTemplate.delete(redisKey);

			// 回傳一個新的空List代表購物車已空
			return new ArrayList<>();
		}

	}

	public List<CartDTO_res> mergeCart(Integer memId, List<CartDTO_req> guestCartList) {

		// 取得已登入會員的購物車（Redis）
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		// 合併未登入購物車到已登入購物車
		for (CartDTO_req guestItem : guestCartList) {

			// 產生商品唯一key（商品ID:顏色ID:規格ID）
			String hashKey = guestItem.getProdId() + ":" + guestItem.getProdColorId() + ":" + guestItem.getProdSpecId();

			// 取出購物車已存在的商品
			Object value = redisTemplate.opsForHash().get(redisKey, hashKey);
			CartVO existItem = null;
			if (value instanceof CartVO) {
				existItem = (CartVO) value;
			} else if (value instanceof Map) {
				existItem = objectMapper.convertValue(value, CartVO.class);
			}
			if (existItem != null) {
				existItem.setCartProdQty(existItem.getCartProdQty() + guestItem.getCartProdQty());
				// 直接存 CartVO 物件
				redisTemplate.opsForHash().put(redisKey, hashKey, existItem);
			} else {
				// 購物車沒有該商品，須新增一個存在購物車中
				CartVO.CartKey cartKey = new CartVO.CartKey(memId, guestItem.getProdId(), guestItem.getProdColorId(),
						guestItem.getProdSpecId());
				CartVO newVO = new CartVO();
				newVO.setCartKey(cartKey);
				newVO.setCartProdQty(guestItem.getCartProdQty());

				// 直接存 CartVO 物件
				redisTemplate.opsForHash().put(redisKey, hashKey, newVO);
			}

		}
		// 回傳合併後的購物車DTO清單給前端
		return getCart(memId);
	}

}
