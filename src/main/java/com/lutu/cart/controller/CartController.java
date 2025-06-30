package com.lutu.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.cart.model.CartService;
import com.lutu.cart.model.dto.CartDTO_merge_req;
import com.lutu.cart.model.dto.CartDTO_req;
import com.lutu.cart.model.dto.CartDTO_res;

@RestController
@CrossOrigin(origins = "*")
public class CartController {

	@Autowired
	CartService cs;

	// 新增購物車
	@PostMapping("/api/addCart")
	public ApiResponse<CartDTO_res> addCart(@RequestBody CartDTO_req cartData) {
		try {
			CartDTO_res result = cs.addCart(cartData.getMemId(), cartData.getProdId(), cartData.getProdColorId(),
					cartData.getProdSpecId(), cartData.getCartProdQty());

			return new ApiResponse<CartDTO_res>("success", result, "新增成功");
		} catch (Exception e) {
			return new ApiResponse<CartDTO_res>("fail", null, "新增失敗");
		}

	}

	// 查詢購物車內容
	@GetMapping("/api/getCart")
	public ApiResponse<List<CartDTO_res>> getCart(@RequestParam(required = false) Integer memId) {

		try {
			List<CartDTO_res> cartList = cs.getCart(memId);

			return new ApiResponse<>("success", cartList, "查詢成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "查詢失敗" + e.getMessage());
		}

	}

	// 修改購物車
	@PostMapping("/api/updateCart")
	public ApiResponse<CartDTO_res> updateCart(@RequestBody CartDTO_req cartData) {
		try {
			CartDTO_res result = cs.updateCart(cartData.getMemId(), cartData.getProdId(), cartData.getProdColorId(),
					cartData.getProdSpecId(), cartData.getCartProdQty());

			return new ApiResponse<CartDTO_res>("success", result, "修改成功");
		} catch (Exception e) {
			return new ApiResponse<CartDTO_res>("fail", null, "修改失敗" + e.getMessage());
		}

	}

	// 移除購物車細項
	@PostMapping("/api/removeCart")	
	public ApiResponse<List<CartDTO_res>> removeCart(@RequestBody CartDTO_req cartData) {
		try {
			List<CartDTO_res> newList = cs.removeCart(cartData.getMemId(), cartData.getProdId(), cartData.getProdColorId(), cartData.getProdSpecId());

			return new ApiResponse<>("success", newList, "移除成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "移除失敗" + e.getMessage());
		}

	}

	// 清空物車
	@PostMapping("/api/clearCart")
	public ApiResponse<List<CartDTO_res>> clearCart(@RequestParam(required = false) Integer memId) {
		try {
			List<CartDTO_res> clearList = cs.clearCart(memId);

			return new ApiResponse<>("success", clearList, "清空成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "清空失敗" + e.getMessage());
		}

	}

	// 合併購物車
	@PostMapping("/api/mergeCart")
	public ApiResponse<List<CartDTO_res>> mergeCart(@RequestBody CartDTO_merge_req MergeCartReq) {

		try {
			List<CartDTO_res> mergeCart = cs.mergeCart(MergeCartReq.getMemId(), MergeCartReq.getCartList());

			return new ApiResponse<>("success", mergeCart, "合併成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "合併失敗" + e.getMessage());
		}

	}

}
