package com.lutu.shop_order.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.discount_code.model.DiscountCodeService;
import com.lutu.member.model.MemberRepository;
import com.lutu.member.model.MemberService;
import com.lutu.member.model.MemberVO;
import com.lutu.shop_order.model.ShopOrderDTO_insert;
import com.lutu.shop_order.model.ShopOrderDTO_res;
import com.lutu.shop_order.model.ShopOrderDTO_update_req;
import com.lutu.shop_order.model.ShopOrderService;
import com.lutu.shop_order.model.ShopOrderVO;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class ShopOrderController {

	@Autowired
	ShopOrderService sos;

	@Autowired
	MemberService ms;
	
	@Autowired
	MemberRepository mr;

	@Autowired
	DiscountCodeService dcs;

	// 取得所有商品訂單，回傳 JSON
	@GetMapping("/api/getAllShopOrders")
	public ApiResponse<List<ShopOrderDTO_res>> getAllShopOrders() {
		List<ShopOrderVO> shopOrders = sos.getAll();

		// 使用dto回傳至前端
		List<ShopOrderDTO_res> resList = new ArrayList<>();
		for (ShopOrderVO sovo : shopOrders) {
			ShopOrderDTO_res res = new ShopOrderDTO_res();
			BeanUtils.copyProperties(sovo, res);
			res.setMemId(sovo.getMemId().getMemId()); // 將會員編號轉為Integer推到前端
			res.setDiscountCodeId(
					sovo.getDiscountCodeId() != null ? sovo.getDiscountCodeId().getDiscountCodeId() : null);// 將折扣碼編號轉為String推到前端

			resList.add(res);
		}

		return new ApiResponse<>("success", resList, "查詢成功");
	}

	// 新增
	@PostMapping("/api/addShopOrder")
	public ApiResponse<ShopOrderDTO_res> addShopOrder(@RequestBody JSONObject orderJson) {

//		ShopOrderVO sovo = new ShopOrderVO();
		try {
		    ShopOrderDTO_insert dto = sos.jsonToDTO(orderJson);
			ShopOrderVO newSOVO = sos.addShopOrder(dto);

			// 將 VO 轉成DTO_res
			ShopOrderDTO_res res = new ShopOrderDTO_res();
			BeanUtils.copyProperties(newSOVO, res);
			try {
		        MemberVO mvo = mr.findById(dto.getMemId()).orElseThrow(() -> new RuntimeException("資料庫中無此會員編號: " + dto.getMemId()));
		        // ... (後續邏輯)
		    } catch (RuntimeException e) {
		        throw new RuntimeException("訂單新增失敗: " + e.getMessage());
		    }
			
			res.setMemId(newSOVO.getMemId().getMemId()); // 將會員編號轉為Integer推到前端
			res.setDiscountCodeId(
					newSOVO.getDiscountCodeId() != null ? newSOVO.getDiscountCodeId().getDiscountCodeId() : null);// 將折扣碼編號轉為String推到前端

			return new ApiResponse<>("success", res, "新增成功");

		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "新增失敗" + e.getMessage());
		}
	}
	
	@PostMapping("/api/addShopOrderCOD")
	public ApiResponse<ShopOrderDTO_res> addShopOrderCOD(@Valid @RequestBody ShopOrderDTO_insert dto) {
	    try {
	        System.out.println("接收的 dto.memId: " + dto.getMemId()); // 確認接收
	        ShopOrderVO newSOVO = sos.addShopOrder(dto);

	        // 將 VO 轉成 DTO_res
	        ShopOrderDTO_res res = new ShopOrderDTO_res();
	        BeanUtils.copyProperties(newSOVO, res);
	        res.setMemId(newSOVO.getMemId().getMemId());
	        res.setDiscountCodeId(newSOVO.getDiscountCodeId() != null ? newSOVO.getDiscountCodeId().getDiscountCodeId() : null);

	        return new ApiResponse<>("success", res, "新增成功 (COD)");
	    } catch (Exception e) {
	        System.out.println("新增失敗 (COD), 錯誤: " + e.getMessage());
	        return new ApiResponse<>("fail", null, "新增失敗 (COD): " + e.getMessage());
	    }
	}

	@PostMapping("/api/updateShopOrder")
	public ApiResponse<ShopOrderDTO_res> updateShopOrder(@Valid @RequestBody ShopOrderDTO_update_req dtoUpdate) {

		try {

			ShopOrderVO updateSOVO = sos.updateShopOrder(dtoUpdate);

			// 將 VO 轉成DTO_res
			ShopOrderDTO_res res = new ShopOrderDTO_res();
			BeanUtils.copyProperties(updateSOVO, res);
			res.setMemId(updateSOVO.getMemId().getMemId()); // 將會員編號轉為Integer推到前端
			res.setDiscountCodeId(
					updateSOVO.getDiscountCodeId() != null ? updateSOVO.getDiscountCodeId().getDiscountCodeId() : null);// 將折扣碼編號轉為String推到前端

			return new ApiResponse<>("success", res, "修改成功");

		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "修改失敗" + e.getMessage());
		}

	}

	@PostMapping("/api/updateShopOrderByMember")
	public ApiResponse<ShopOrderDTO_res> updateShopOrderByMember(
			@Valid @RequestBody ShopOrderDTO_update_req dtoUpdate) {

		try {

			ShopOrderVO updateSOVO = sos.updateShopOrderByMember(dtoUpdate);

			// 將vo轉dto_res
			ShopOrderDTO_res dto_res = new ShopOrderDTO_res();
			BeanUtils.copyProperties(updateSOVO, dto_res);
			dto_res.setMemId(updateSOVO.getMemId().getMemId()); // 將會員編號轉為Integer推到前端

			return new ApiResponse<>("success", dto_res, "修改成功");

		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "修改失敗" + e.getMessage());
		}

	}

	// 依訂單編號單筆查詢
	@GetMapping("/api/getOneById")
	public ApiResponse<ShopOrderDTO_res> getOneById(@RequestParam("shopOrderId") Integer shopOrderId) {
		ShopOrderVO sovo = sos.getOneShopOrder(shopOrderId);

		// 將 VO 轉成DTO_res
		ShopOrderDTO_res res = new ShopOrderDTO_res();
		BeanUtils.copyProperties(sovo, res);
		res.setMemId(sovo.getMemId().getMemId()); // 將會員編號轉為Integer推到前端
		res.setDiscountCodeId(sovo.getDiscountCodeId() != null ? sovo.getDiscountCodeId().getDiscountCodeId() : null);// 將折扣碼編號轉為String推到前端

		return new ApiResponse<>("success", res, "查詢成功");
	}

	// 依訂單編號單筆查詢
	@GetMapping("/api/getOneByMemId")
	public ApiResponse getOneByMemId(@RequestParam("memId") MemberVO memId) {
		List<ShopOrderVO> memOrders = sos.getAll(memId);

		// 使用dto回傳至前端
		List<ShopOrderDTO_res> resList = new ArrayList<>();
		for (ShopOrderVO sovo : memOrders) {
			ShopOrderDTO_res res = new ShopOrderDTO_res();
			BeanUtils.copyProperties(sovo, res);
			res.setMemId(sovo.getMemId().getMemId()); // 將會員編號轉為Integer推到前端
			res.setDiscountCodeId(
					sovo.getDiscountCodeId() != null ? sovo.getDiscountCodeId().getDiscountCodeId() : null);// 將折扣碼編號轉為String推到前端

			resList.add(res);
		}

		return new ApiResponse<>("success", resList, "查詢成功");
	}

}
