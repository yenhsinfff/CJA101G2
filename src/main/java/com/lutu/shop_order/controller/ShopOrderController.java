package com.lutu.shop_order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.shop_order.model.ShopOrderDTO_insert;
import com.lutu.shop_order.model.ShopOrderDTO_update;
import com.lutu.shop_order.model.ShopOrderService;
import com.lutu.shop_order.model.ShopOrderVO;

import jakarta.validation.Valid;


@RestController
@Validated
@CrossOrigin(origins = "*")
public class ShopOrderController {
	
	@Autowired
	ShopOrderService sos;
	
//	@Autowired
//	MemberService ms;
//	
//	@Autowired
//	DiscountCodeService dcs;
	
	
	// 取得所有商品訂單，回傳 JSON
	@GetMapping("/api/getAllShopOrders")
	public ApiResponse<List<ShopOrderVO>> getAllShopOrders() {
		List<ShopOrderVO> shopOrders = sos.getAll();
		
		return new ApiResponse<>("success", shopOrders, "查詢成功");	
	}

	
	
	//新增
//	@PostMapping("/api/addShopOrder")								
//	public ApiResponse<ShopOrderVO> addShopOrder(@Valid @RequestBody ShopOrderDTO_insert dto) {
//		
//		ShopOrderVO sovo = new ShopOrderVO();
//		try {
////			System.out.println("aaaaaa");
//			
//			ShopOrderVO newSOVO = sos.addShopOrder(dto);
//			return new ApiResponse<>("success", newSOVO, "新增成功");
//			
//		} catch (Exception e) {
//			return new ApiResponse<>("fail", sovo, "新增失敗");
//		}
//	}
	
	
	@PostMapping("/api/updateShopOrder")
	public ApiResponse updateShopOrder(@Valid @RequestBody ShopOrderDTO_update dtoUpdate) {
		
		ShopOrderVO sovo = new ShopOrderVO();
		try {
			
			ShopOrderVO newSOVO = sos.updateShopOrder(dtoUpdate);
			return new ApiResponse<>("success", newSOVO, "修改成功");
			
		} catch (Exception e) {
			 return new ApiResponse<>("fail", sovo, "修改失敗");
		}
		
	}
	
	
<<<<<<< Upstream, based on branch 'master' of https://github.com/yenhsinfff/CJA101G2.git
	// 依訂單編號單筆查詢
	@GetMapping("/api/getOneById")
	public ApiResponse getOneById(@RequestParam("shopOrderId") Integer shopOrderId) {
		ShopOrderVO sovo = sos.getOneShopOrder(shopOrderId);
		
		return new ApiResponse<>("success", sovo, "查詢成功");
	}
	
	// 依訂單編號單筆查詢
//	@GetMapping("/api/getOneByMemId")
//	public ApiResponse getOneByMemId(@RequestParam("memId") Integer memId) {
//		List<ShopOrderVO> memOrders = sos.getAll(memId);
=======
	@PostMapping("/api/updateShopOrder")
	public ApiResponse updateShopOrder(@Valid @RequestBody ShopOrderDTO_update dtoUpdate) {
		
		ShopOrderVO sovo = new ShopOrderVO();
		try {
			
			ShopOrderVO newSOVO = sos.updateShopOrder(dtoUpdate);
			return new ApiResponse<>("success", newSOVO, "修改成功");
			
		} catch (Exception e) {
			 return new ApiResponse<>("fail", sovo, "修改失敗");
		}
		
	}
	
	
	// 依訂單編號單筆查詢
	@GetMapping("/api/getOneById")
	public ApiResponse getOneById(@RequestParam("shopOrderId") Integer shopOrderId) {
		ShopOrderVO sovo = sos.getOneShopOrder(shopOrderId);
		
		return new ApiResponse<>("success", sovo, "查詢成功");
	}
	
	// 依訂單編號單筆查詢
	@GetMapping("/api/getOneByMemId")
	public ApiResponse getOneByMemId(@RequestParam("memId") Integer memId) {
		List<ShopOrderVO> memOrders = sos.getAll(memId);
		
		return new ApiResponse<>("success", memOrders , "查詢成功");
	}
	
	
//	@GetMapping("/api/compositeQuery")
//	public ApiResponse compositeQuery(@RequestParam Map<String, String[]> params) {
//		List<ShopOrderVO> shopOrders2 = sos.getAll(params);	
>>>>>>> 3196bc9 [feat] 新增商品訂單 修改/依訂單查詢/依會員查詢
//		
<<<<<<< Upstream, based on branch 'master' of https://github.com/yenhsinfff/CJA101G2.git
//		return new ApiResponse<>("success", memOrders , "查詢成功");
=======
//		return new ApiResponse<>("success", shopOrders2 , "查詢成功");
>>>>>>> 3196bc9 [feat] 新增商品訂單 修改/依訂單查詢/依會員查詢
//	}
	
	
//	@GetMapping("/api/compositeQuery")
//	public ApiResponse compositeQuery(@RequestParam Map<String, String[]> params) {
//		List<ShopOrderVO> shopOrders2 = sos.getAll(params);	
//		
//		return new ApiResponse<>("success", shopOrders2 , "查詢成功");


}
