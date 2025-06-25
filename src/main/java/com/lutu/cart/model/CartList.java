package com.lutu.cart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("cartList")
public class CartList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer memId;
	private List<CartVO> cartList = new ArrayList<>();	//購物車明細
	
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public List<CartVO> getCartList() {
		return cartList;
	}
	public void setCartList(List<CartVO> cartList) {
		this.cartList = cartList;
	}

}
