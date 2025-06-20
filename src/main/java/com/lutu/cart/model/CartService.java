package com.lutu.cart.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	
	@Autowired
	CartRepository cr;
	
	
	// 依會員編號查詢
	public List<CartVO> getAll(Integer memId) {
		return cr.findByMember(memId);
	}

}
