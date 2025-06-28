package com.lutu.cart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import jakarta.servlet.http.HttpSession;

@Transactional
@Service
public class CartService implements CartService_Interface {

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
		
		ProdSpecListVO.CompositeDetail2 key = new ProdSpecListVO.CompositeDetail2(cartVO.getProdId(), cartVO.getProdSpecId());
		// 價格
		dto.setProdPrice(psr.findById(key).map(ProdSpecListVO::getProdSpecPrice).orElse(0));

		return dto;
	}

	@Override
	public CartDTO_res addCart(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId,
			Integer cartProdQty) {
		System.out.println("test1");
		// 確認數量> 0
		checkQtyValid(cartProdQty);
		System.out.println("test2");

		// 組成cartKey和cartVO
		CartVO.CartKey cartKey = new CartVO.CartKey(memId, prodId, prodColorId, prodSpecId);
		CartVO item = new CartVO(memId, prodId, prodColorId, prodSpecId, cartProdQty);
		System.out.println("test3");

		item.setCartKey(cartKey);

		System.out.println("test4");
		// 新增購物車
		if (memId != null) {
			System.out.println("test5");
			return addCartForMember(memId, cartKey, item, cartProdQty);

		} else {// 已登入
			System.out.println("test6");
//			throw new RuntimeException("未登入儲存在前端");
			return addCartForMember(memId, cartKey, item, cartProdQty);

		}
	}

	// 處理未登入（Session）購物車
//	private CartDTO_res addCartForGuest(HttpSession session, CartVO.CartKey cartKey, CartVO item, int cartProdQty) {
//		CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);
//		System.out.println("test7");
//		if (cart1 == null) {
//			cart1 = new CartList();
//			System.out.println("test8");
//			// 存入session
//			session.setAttribute(REDIS_KEY_PREFIX, cart1);
//			System.out.println("test9");
//		}
//		// 如果已經有同樣商品（同key），就數量累加
//		// .findFirst()會回傳Optional<CartVO>，如果有找到就包在Optional裡
//		Optional<CartVO> exist = cart1.getCartList().stream().filter(vo -> vo.getCartKey().equals(cartKey)).findFirst();
//		// 判斷購物車裡面有沒有該商品
//		System.out.println("test10");
//		if (exist.isPresent()) {
//			exist.get().setCartProdQty(exist.get().getCartProdQty() + cartProdQty);
//		} else {// 沒有找到該商品，直接加入
//			cart1.getCartList().add(item);
//			System.out.println("test11");
//		}
//
//		// 再 setAttribute確保 session 內容為最新
//		session.setAttribute(REDIS_KEY_PREFIX, cart1);
//		System.out.println("test12");
//
//		return toCartDTO(exist.isPresent() ? exist.get() : item);
//	}

	// 處理已登入（Redis）購物車
	private CartDTO_res addCartForMember(Integer memId, CartVO.CartKey cartKey, CartVO item, int qty) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		String hashKey = item.getProdId() + ":" + item.getProdColorId() + ":" + item.getProdSpecId();

		try {// 檢查購物車有無相同商品
			Object existItem = redisTemplate.opsForHash().get(redisKey, hashKey);
			if (existItem instanceof CartVO exist) {
				item.setCartProdQty(item.getCartProdQty() + exist.getCartProdQty());
			}
			// 直接存 CartVO 物件
			redisTemplate.opsForHash().put(redisKey, hashKey, item);

		} catch (Exception e) {
			throw new RuntimeException("新增購物車時發生錯誤：" + e.getMessage(), e);
		}

		return toCartDTO(item);

	}

	@Override
	public List<CartDTO_res> getCart(Integer memId) {
		List<CartDTO_res> dtoList = new ArrayList<>();

		// 判斷是否已登入會員
		if (memId == null) {
//			CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);
//
//			// 檢查session裡有沒有購物車（CartList）物件
//			if (cart1 == null) {
//				cart1 = new CartList();
//				// 存入session
//				session.setAttribute(REDIS_KEY_PREFIX, cart1);
//			}
//
//			for (CartVO cartVO : cart1.getCartList()) {
//				dtoList.add(toCartDTO(cartVO));
//
//			}
			return dtoList;
		} else {
			String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

			try {
				// 從Redis Hash取得所有field-value
				Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);

				if (entries != null && !entries.isEmpty()) {

					for (Object value : entries.values()) {

						if (value instanceof CartVO cartVO) {
							if (cartVO.getMemId() == null) {
								cartVO.setMemId(10000007); // 補充 memId

							}

							dtoList.add(toCartDTO(cartVO));

						} else {
							System.out.println("資料格式錯誤，非CartVO：" + value);

						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("redis error!");
			}
			return dtoList;
		}

	}

	@Override
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

			return updateCartForMember(memId, cartKey, item);
//			return updateCartForGuest(session, cartKey, item, cartProdQty);
		} else {// 已登入

			return updateCartForMember(memId, cartKey, item);
		}

	}

	// 修改未登入（Session）購物車
//	private CartDTO_res updateCartForGuest(HttpSession session, CartVO.CartKey cartKey, CartVO item, int cartProdQty) {
//
//		// 從Session取得購物車清單
//		CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);
//
//		if (cart1 == null) {
//			throw new IllegalArgumentException("購物車不存在");
//		}
//
//		// 在購物車清單中尋找是否有這個商品
//		// .findFirst()會回傳Optional<CartVO>，如果有找到就包在Optional裡
//		Optional<CartVO> exist = cart1.getCartList().stream().filter(vo -> vo.getCartKey().equals(cartKey)).findFirst();
//
//		// 判斷購物車裡面有沒有該商品
//		if (exist.isPresent()) {
//			exist.get().setCartProdQty(cartProdQty);
//		} else {// 沒有找到該商品，直接加入
//
//			cart1.getCartList().add(item);
//		}
//
//		// 再 setAttribute確保 session 內容為最新
//		session.setAttribute(REDIS_KEY_PREFIX, cart1);
//
//		return toCartDTO(exist.orElse(item));
//	}

	// 會員（Redis）修改購物車
	private CartDTO_res updateCartForMember(Integer memId, CartVO.CartKey cartKey, CartVO item) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		String hashKey = item.getProdId() + ":" + item.getProdColorId() + ":" + item.getProdSpecId();

		try {// 檢查購物車有無相同商品
			
			// 先刪除同一商品但不同顏色/規格的商品
			Map<Object, Object> entries =  redisTemplate.opsForHash().entries(redisKey);
			if (entries != null) {
				for(Object keyObject : entries.keySet()) {
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

	@Override
	public List<CartDTO_res> removeCart(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId) {
		// 組成cartKey
		CartVO.CartKey cartKey = new CartVO.CartKey(memId, prodId, prodColorId, prodSpecId);

		// 判斷會員ID是否為空
		if (memId == null) {

			removeCartForMember(memId, cartKey);
//			removeCartForGuest(session, cartKey);
		} else {

			removeCartForMember(memId, cartKey);
		}

		return getCart(memId);

	}

	// 移除未登入（Session）購物車
//	private CartDTO_res removeCartForGuest(HttpSession session, CartVO.CartKey cartKey) {
//
//		// 從Session取得購物車清單
//		CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);
//
//		// 檢查購物車是否為空
//		if (cart1 == null || cart1.getCartList() == null || cart1.getCartList().isEmpty()) {
//			throw new IllegalArgumentException("購物車無商品");
//		}
//
//		// 在購物車清單中尋找對應的商品（用CartKey比對）
//		Optional<CartVO> exist = cart1.getCartList().stream().filter(vo -> vo.getCartKey().equals(cartKey)).findFirst();
//
//		// 若沒找到該商品，拋出異常
//		if (!exist.isPresent()) {
//			throw new IllegalArgumentException("購物車無該商品");
//		}
//
//		// 找到商品，從購物車清單中移除
//		CartVO removeItem = exist.get();
//		cart1.getCartList().remove(removeItem);
//
//		// 將更新後的購物車清單存回session
//		session.setAttribute(REDIS_KEY_PREFIX, cart1);
//
//		return toCartDTO(removeItem);
//	}

	// 會員（Redis）購物車移除細項
	private CartDTO_res removeCartForMember(Integer memId, CartVO.CartKey cartKey) {
		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		// 組合hashKey（商品唯一識別：商品ID:顏色ID:規格ID
		String hashKey = cartKey.getProdId() + ":" + cartKey.getProdColorId() + ":" + cartKey.getProdSpecId();

		// 從Redis取出對應的商品
		CartVO existItem = (CartVO) redisTemplate.opsForHash().get(redisKey, hashKey);

		// 若商品不存在，拋出異常
		if (existItem == null) {
			throw new IllegalArgumentException("購物車無該商品");
		}

		// 從Redis中刪除該商品
		redisTemplate.opsForHash().delete(redisKey, hashKey);

		return toCartDTO(existItem);

	}

	@Override
	public List<CartDTO_res> clearCart(Integer memId) {

//		if (memId == null) {
//			// 從Session取得購物車清單
//			CartList cart1 = (CartList) session.getAttribute(REDIS_KEY_PREFIX);
//			if (cart1 != null) {
//				cart1.getCartList().clear(); // 清空list
//				session.setAttribute(REDIS_KEY_PREFIX, cart1);
//			}
//		} else {// 有登入會員

		String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

		// 從Redis中刪除所有購物車內容
		redisTemplate.delete(redisKey);

		// 回傳一個新的空List代表購物車已空
		return new ArrayList<>();
	}

//	}

	@Override
	public List<CartDTO_res> mergeCart(HttpSession session, Integer memId) {
		// 取得未登入購物車
		CartList guestCart = (CartList) session.getAttribute(REDIS_KEY_PREFIX);

		try {
			// 如果Session購物車是空的，直接回傳會員購物車DTO清單（已登入購物車）
			if (guestCart == null || guestCart.getCartList().isEmpty()) {
				return getCart(memId);
			}

			// 取得已登入會員的購物車（Redis）
			String redisKey = REDIS_KEY_PREFIX + memId; // Redis的key

			// 合併未登入購物車到已登入購物車
			for (CartVO guestItem : guestCart.getCartList()) {

				// 產生商品唯一key（商品ID:顏色ID:規格ID）
				String hashKey = guestItem.getProdId() + ":" + guestItem.getProdColorId() + ":"
						+ guestItem.getProdSpecId();

				Object existItem = redisTemplate.opsForHash().get(redisKey, hashKey);
				if (existItem instanceof CartVO exist) {
					guestItem.setCartProdQty(guestItem.getCartProdQty() + exist.getCartProdQty());
				}
				// 直接存 CartVO 物件
				redisTemplate.opsForHash().put(redisKey, hashKey, guestItem);

			}
		} catch (Exception e) {

			throw new RuntimeException("新增購物車時發生錯誤：" + e.getMessage(), e);

		} finally {

			// 合併後，清空Session購物車避免重複
			session.removeAttribute(REDIS_KEY_PREFIX);
		}

		// 回傳合併後的購物車DTO清單給前端
		return getCart(memId);

	}

}
