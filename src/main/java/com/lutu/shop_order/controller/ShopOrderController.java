package com.lutu.shop_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.shop_order.model.ShopOrderDTO_insert;
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
	@PostMapping("/api/addShopOrder")								
	public ApiResponse<ShopOrderVO> addShopOrder(@Valid @RequestBody ShopOrderDTO_insert dto) {
		
		ShopOrderVO sovo = new ShopOrderVO();
		try {
//			System.out.println("aaaaaa");
			
			ShopOrderVO newSOVO = sos.addShopOrder(dto);
			return new ApiResponse<>("success", newSOVO, "新增成功");
			
		} catch (Exception e) {
			return new ApiResponse<>("fail", sovo, "新增失敗");
		}
	}
	
	
//	@PostMapping("/api/updateShopOrder")
//	public String updateShopOrder(@Valid ShopOrderVO sovo, BindingResult result, ModelMap model) {
//		
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		if (result.hasErrors()) {
//			model.addAttribute("error2", "資料填寫格式錯誤，請重新確認");
//			return "back-end/shop_order/update_ShopOrder_input";
//		}
//		
//		/*************************** 2.開始修改資料 *****************************************/
//		sos.updateShopOrder(sovo);
//		
//		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("success", "- (修改成功)");
//		sovo = sos.getOneShopOrder(Integer.valueOf(sovo.getShopOrderId()));
//		model.addAttribute("sovo", sovo);
//		return "back-end/shop_order/listOneShopOrder";
//		
//	}

}
