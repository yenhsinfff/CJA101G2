package com.lutu.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.cart.model.CartList;
import com.lutu.cart.model.CartService;
import com.lutu.cart.model.CartVO;
import com.lutu.cart.model.dto.CartDTO_res;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
public class CartController {

	@Autowired
	CartService cs;

	// 新增購物車
	@PostMapping("/api/addCart")
	public ApiResponse<CartDTO_res> addCart(HttpSession session, @RequestParam(required = false) Integer memId,
			@RequestParam Integer prodId, @RequestParam Integer prodColorId, @RequestParam Integer prodSpecId,
			@RequestParam Integer cartProdQty) {
		try {
			CartDTO_res result = cs.addCart(session, memId, prodId, prodColorId, prodSpecId, cartProdQty);

			return new ApiResponse<CartDTO_res>("success", result, "新增成功");
		} catch (Exception e) {
			return new ApiResponse<CartDTO_res>("fail", null, "新增失敗");
		}

	}

	// 查詢購物車內容
	@GetMapping("/api/getCart")
	public ApiResponse<List<CartDTO_res>> getCart(HttpSession session, @RequestParam(required = false) Integer memId) {

		try {
			List<CartDTO_res> cartList = cs.getCart(session, memId);

			return new ApiResponse<>("success", cartList, "查詢成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "新增失敗");
		}

	}

}
