package com.lutu.shop_order_items_details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lutu.ApiResponse;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsDTO_res;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsDTO_update;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsService;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsVO;

import jakarta.validation.Valid;

@RestController
public class ShopOrderItemsDetailsController {

	@Autowired
	ShopOrderItemsDetailsService soids;

	// 取得所有商品訂單明細，回傳 JSON
	@GetMapping("/api/getAllShopOrdersDetails")
	public ApiResponse<List<ShopOrderItemsDetailsDTO_res>> getAllShopOrdersDetails() {
		List<ShopOrderItemsDetailsDTO_res> details = soids.getAll();

		return new ApiResponse<>("success", details, "查詢成功");
	}

	// 依訂單編號取得明細資料
	@GetMapping("/api/getDetailsByShopOrderId")
	public ApiResponse<List<ShopOrderItemsDetailsDTO_res>> getByShopOrderId(
			@RequestParam("shopOrderId") Integer shopOrderId) {
		List<ShopOrderItemsDetailsDTO_res> detailsById = soids.getAll(shopOrderId);

		return new ApiResponse<>("success", detailsById, "查詢成功");
	}

	// 單筆查詢
	@GetMapping("/api/getOneDetail")
	public ApiResponse<ShopOrderItemsDetailsDTO_res> getOneDetail(@RequestParam("shopOrderId") Integer shopOrderId,
			@RequestParam("prodId") Integer prodId, @RequestParam("prodColorId") Integer prodColorId,
			@RequestParam("prodSpecId") Integer prodSpecId) {

		// 取得複合主鍵
		ShopOrderItemsDetailsVO.CompositeDetail key = new ShopOrderItemsDetailsVO.CompositeDetail(shopOrderId, prodId,
				prodColorId, prodSpecId);

		ShopOrderItemsDetailsDTO_res details = soids.getOneDetail(key);

		return new ApiResponse<>("success", details, "查詢成功");
	}

	// 修改
	@PostMapping("/api/updateComments")
	public ApiResponse<ShopOrderItemsDetailsDTO_res> updateComments(
			@Valid @RequestBody ShopOrderItemsDetailsDTO_update dtoUpdate) {

		try {

			ShopOrderItemsDetailsDTO_res updateDetails = soids.updateDetails(dtoUpdate);
			
			return new ApiResponse<>("success", updateDetails, "修改成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "修改失敗");
		}

	}

}
