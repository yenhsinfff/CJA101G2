package com.lutu.cart.model.dto;

import java.util.List;

public class CartDTO_merge_req {
	
	private Integer memId;
	
	private List<CartDTO_req> cartList;		// 接收前端送來的購物車

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public List<CartDTO_req> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartDTO_req> cartList) {
		this.cartList = cartList;
	}
	

}
