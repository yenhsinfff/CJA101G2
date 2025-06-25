package com.lutu.cart.model;

import java.util.List;

import com.lutu.cart.model.dto.CartDTO_res;

import jakarta.servlet.http.HttpSession;

public interface CartService_Interface {
	
	// 定義購物車可以提供哪些服務
	
	// 加入購物車
	CartDTO_res addCart(HttpSession session, Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId, Integer cartProdQty);
	
	
	// 查詢購物車
	List<CartDTO_res> getCart(HttpSession session, Integer memId);

	// 修改購物車內容
	void updateCart(HttpSession session, Integer memId, CartVO item);
	
	// 移除購物車內容
	void removeCart(HttpSession session, Integer memId, CartVO.CartKey key);
	
	// 清空購物車
	void clearCart(HttpSession session, Integer memId); 
	
	// 合併購物車
	void mergeCart(HttpSession session, Integer memId);
	
}
