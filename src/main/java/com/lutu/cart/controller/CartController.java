package com.lutu.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.cart.model.CartList;
import com.lutu.cart.model.CartService;
import com.lutu.cart.model.CartVO;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
public class CartController {

	@Autowired
	CartService cs;

	// 查詢購物車內容
	@GetMapping("/api/getCart")
	public ApiResponse<List<CartVO>> getCart(HttpSession session, @RequestParam(required = false) Integer memId) {

		CartList cartList = cs.getCart(session, memId);
		
		  // 直接回傳CartVO清單
		List<CartVO> list = new ArrayList<CartVO>();
		
		if (cartList != null && cartList.getCartList() != null) {
			list = cartList.getCartList();
		} 
		
		
		return new ApiResponse<>("success", list, "查詢成功");  
	}
	
	
}
