package com.lutu.campsite_order.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.camp.model.CampService;
import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsite_available.model.CampsiteTypeAvailableDTO;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.campsite_order.model.CampsiteOrderDTO;
import com.lutu.campsitetype.model.CampsiteTypeDTO;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/campsite/order")
public class CampsiteApiController {
	
	@Autowired
	CampSiteOrderService campsiteOrdSvc;
	
	@Autowired
	CampService campSvc;
	
	@GetMapping("/api/campsite/setorder")
	public void setCampsiteorder(@PathVariable Integer campId, HttpServletResponse response) throws IOException {

		byte[] img = (campSvc.getOneCamp(campId)).getCampPic1(); // 從資料庫取得

		response.setContentType("image/jpeg");
		response.getOutputStream().write(img);
	}
	
	@GetMapping("/all")
	public ApiResponse<List<CampsiteOrderDTO>> getAllCampsiteOrder() throws IOException {
		List<CampsiteOrderDTO> voList = campsiteOrdSvc.getAllDTOOrders();
		  return new ApiResponse<>("success", voList, "查詢成功");
	}
	
	@GetMapping("/getone/{campsiteOrderId}")
	public ApiResponse<CampsiteOrderDTO> getAllCampsiteOrder(@PathVariable String campsiteOrderId) throws IOException {
		CampsiteOrderDTO dto = campsiteOrdSvc.getOneDTOCampsiteOrder(campsiteOrderId);
		  return new ApiResponse<>("success", dto, "查詢成功");
	}
	
	@GetMapping("/cancel/{campsiteOrderId}")
	public ApiResponse<Boolean> cancelOneCampsiteOrder(@PathVariable String campsiteOrderId) throws IOException {
		Boolean cancelRes = campsiteOrdSvc.updatePaymentStatus(campsiteOrderId, (byte)3);
		if(cancelRes) {
			return new ApiResponse<>("success", cancelRes, "取消成功");
		}else {
			return new ApiResponse<>("fail", cancelRes, "取消失敗");
		}
		  
	}
	
	@GetMapping("{campId}/byCampId")
	public ApiResponse<List<CampsiteOrderDTO>> getOrdersByCampId(@PathVariable Integer campId) {
	    List<CampsiteOrderDTO> dtoList = campsiteOrdSvc.getDTOOrdersByCampId(campId);
	    return new ApiResponse<>("success", dtoList, "查詢成功");
	}

	
	@PostMapping("/update")
	public ApiResponse<Boolean> updateStatus(
			@RequestParam String orderId, @RequestParam int status) {
		Boolean response = false;
		try {
			response=campsiteOrdSvc.updatePaymentStatus(orderId,(byte) status);
			return new ApiResponse<>("success", response, "取消成功");
		} catch (Exception e) {
			System.out.println("updateStatus_err:"+e);
			return new ApiResponse<>("fail", response, "取消失敗");
		}
	}
	

}
