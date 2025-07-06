package com.lutu.campsite_available.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lutu.ApiResponse;
import com.lutu.campsite_available.model.CampsiteAvailableService;
import com.lutu.campsite_available.model.CampsiteTypeAvailableDTO;
import com.lutu.campsitetype.model.CampsiteTypeDTO;
import com.lutu.campsitetype.model.CampsiteTypeVO;

@RestController
@RequestMapping("/api/ca")
public class CampsiteAvailApiController {

	@Autowired
	private CampsiteAvailableService service;

	@PostMapping("/available")
	public ApiResponse<List<CampsiteTypeDTO>> searchAvailable(@RequestParam(required = false) List<Integer> campIds,
			@RequestParam(required = false) Integer people,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut)
			throws JsonProcessingException {

		if (!checkIn.isBefore(checkOut)) {
			throw new IllegalArgumentException("checkIn 必須早於 checkOut");
		}
		List<CampsiteTypeVO> campsiteTypeVOList = service.ensureAndQuery(campIds, people,
				java.sql.Date.valueOf(checkIn), java.sql.Date.valueOf(checkOut));
		System.out.println("開啟");
		for (CampsiteTypeVO vo : campsiteTypeVOList) {
			vo.setCampsitePic1(null);
			vo.setCampsitePic2(null);
			vo.setCampsitePic3(null);
			vo.setCampsitePic4(null);
			System.out.println(vo.getCampsiteName());
			System.out.println(vo.getCampsiteNum());
			System.out.println("=================");
		}
		List<CampsiteTypeDTO> dtoList = campsiteTypeVOList.stream()
				.map(vo -> new CampsiteTypeDTO(vo.getId().getCampsiteTypeId(), vo.getId().getCampId(),
						vo.getCampsiteName(), vo.getCampsitePeople(), vo.getCampsiteNum(), vo.getCampsitePrice()))
				.collect(Collectors.toList());

		ApiResponse<List<CampsiteTypeDTO>> resp = new ApiResponse<>("success", dtoList, "查詢成功");
		System.out.println(new ObjectMapper().writeValueAsString(resp));

		return resp;
	}

	@PostMapping("/available/Remaing")
	public ApiResponse<List<CampsiteTypeAvailableDTO>> searchAvailableRemaing(
			@RequestParam(required = false) List<Integer> campIds, @RequestParam(required = false) Integer people,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
		
		System.out.println("CHECKIN:"+checkIn);
		System.out.println("CHECKOUT:"+checkOut);

//	     // 這裡根據 campIds 迴圈呼叫 repository，合併結果
//	     List<CampsiteTypeAvailableDTO> result = new ArrayList<>();
//	     if (campIds == null || campIds.isEmpty()) {
//	         result = service.ensureAndQueryRemaing(null, people, Date.valueOf(checkIn), Date.valueOf(checkOut));
//	     } else {
//	         for (Integer campId : campIds) {
//	             result.addAll(service.ensureAndQueryRemaing(campId, people, Date.valueOf(checkIn), Date.valueOf(checkOut)));
//	         }
//	     }
		if (!checkIn.isBefore(checkOut)) {
			throw new IllegalArgumentException("checkIn 必須早於 checkOut");
		}
		List<CampsiteTypeAvailableDTO> campsiteTypeVOList = service.ensureAndQueryRemaing(campIds, people,
				java.sql.Date.valueOf(checkIn), java.sql.Date.valueOf(checkOut));
		return new ApiResponse<>("success", campsiteTypeVOList, "查詢成功");
	}
	
	//訂單成立 剩餘房型數量-1
//	@PostMapping("/deduct")
//	public ApiResponse<Boolean> deductRooms(
//	        @RequestParam Integer campsiteTypeId,
//	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
//	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
//	    try {
//	        service.deductRoomsByDateRange(
//	                java.sql.Date.valueOf(checkIn),
//	                java.sql.Date.valueOf(checkOut),
//	                campsiteTypeId
//	        );
//	        return new ApiResponse<>("success", true, "訂單成立，房量已扣減");
//	    } catch (Exception ex) {
//	        return new ApiResponse<>("fail", false, ex.getMessage());
//	    }
//	}
//	
//	//訂單取消，剩餘房型+1
//	@PostMapping("/add")
//	public ApiResponse<Boolean> refundRooms(
//	        @RequestParam Integer campsiteTypeId,
//	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
//	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
//	    try {
//	        service.refundRoomsByDateRange(
//	                java.sql.Date.valueOf(checkIn),
//	                java.sql.Date.valueOf(checkOut),
//	                campsiteTypeId
//	        );
//	        return new ApiResponse<>("success", true, "訂單取消，房量已回補");
//	    } catch (Exception ex) {
//	        return new ApiResponse<>("fail", false, ex.getMessage());
//	    }
//	}



}
