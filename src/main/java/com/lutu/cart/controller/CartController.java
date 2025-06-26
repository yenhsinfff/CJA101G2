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
			return new ApiResponse<>("fail", null, "查詢失敗");
		}

	}

	// 修改購物車
	@PostMapping("/api/updateCart")
	public ApiResponse<CartDTO_res> updateCart(HttpSession session, @RequestParam(required = false) Integer memId,
			@RequestParam Integer prodId, @RequestParam Integer prodColorId, @RequestParam Integer prodSpecId,
			@RequestParam Integer cartProdQty) {
		try {
			CartDTO_res result = cs.updateCart(session, memId, prodId, prodColorId, prodSpecId, cartProdQty);

			return new ApiResponse<CartDTO_res>("success", result, "修改成功");
		} catch (Exception e) {
			return new ApiResponse<CartDTO_res>("fail", null, "修改失敗");
		}

	}

	// 移除購物車細項
	@PostMapping("/api/removeCart")
	public ApiResponse<List<CartDTO_res>> removeCart(HttpSession session, @RequestParam(required = false) Integer memId,
			@RequestParam Integer prodId, @RequestParam Integer prodColorId, @RequestParam Integer prodSpecId) {
		try {
			List<CartDTO_res> newList = cs.removeCart(session, memId, prodId, prodColorId, prodSpecId);

			return new ApiResponse<>("success", newList, "移除成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "移除失敗");
		}

	}

	// 清空物車
	@PostMapping("/api/clearCart")
	public ApiResponse<List<CartDTO_res>> clearCart(HttpSession session, @RequestParam(required = false) Integer memId) {
		try {
			List<CartDTO_res> clearList = cs.clearCart(session, memId);

			return new ApiResponse<>("success", clearList, "清空成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "清空失敗");
		}

	}

	// 合併購物車
	@PostMapping("/api/mergeCart")
	public ApiResponse<List<CartDTO_res>> mergeCart(HttpSession session, Integer memId) {

		try {
			List<CartDTO_res> mergeCart = cs.mergeCart(session, memId);

			return new ApiResponse<>("success", mergeCart, "合併成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "合併失敗");
		}

	}

}
