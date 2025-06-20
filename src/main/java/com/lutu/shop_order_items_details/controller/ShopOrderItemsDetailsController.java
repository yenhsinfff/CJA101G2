package com.lutu.shop_order_items_details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsService;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsVO;

@RestController
@CrossOrigin(origins = "*")
public class ShopOrderItemsDetailsController {
	
	@Autowired
	ShopOrderItemsDetailsService soids;
	
	// 取得所有商品訂單明細，回傳 JSON
	@GetMapping("/api/getAllShopOrdersDetails")
	public ApiResponse<List<ShopOrderItemsDetailsVO>> getAllShopOrdersDetails() {
		List<ShopOrderItemsDetailsVO> details = soids.getAll();
		
		return new ApiResponse<> ("success", details, "查詢成功");
	}
	
	// 依訂單編號取得明細資料
	@GetMapping("/api/getByShopOrderId")
	public ApiResponse<List<ShopOrderItemsDetailsVO>> getByShopOrderId(@RequestParam("shopOrderId") Integer shopOrderId) {
		List<ShopOrderItemsDetailsVO> detailsById = soids.getAll(shopOrderId);
		
		return new ApiResponse<>("success", detailsById, "查詢成功");
		
	}

}
