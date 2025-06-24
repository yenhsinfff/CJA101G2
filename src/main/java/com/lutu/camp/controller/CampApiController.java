package com.lutu.camp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lutu.ApiResponse;
import com.lutu.camp.model.CampService;
import com.lutu.camp.model.CampVO;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.util.HmacUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*")
public class CampApiController {
	@Autowired
	CampSiteOrderService campsiteOrdSvc;

	@Autowired
	CampService campService;

	// 取得營地訂單編號
	@GetMapping("/api/campsite/newordernumber")
	public ApiResponse<String> getNewCampsiteOrderNum() {
		String newOrderNum = campsiteOrdSvc.generateCampsiteOrderId();
		return new ApiResponse<>("success", newOrderNum, "查詢成功");
	}

	// 取得所有營地訂單，回傳 JSON
	@GetMapping("/api/getallcamps1")
	public ResponseEntity<String> getAllCamps1(@RequestParam boolean withOrders) throws Exception {

		// 1. 獲取資料
		List<CampVO> camps = campService.getAllCamp();

		// 2. 設定動態過濾器
		ObjectMapper mapper = new ObjectMapper();
		SimpleFilterProvider filters = new SimpleFilterProvider();

		if (withOrders) {
			filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAll());
		} else {
			filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAllExcept("campsiteOrders"));
		}
		mapper.setFilterProvider(filters);

		// 3. 包裝回應並序列化
		ApiResponse<List<CampVO>> response = new ApiResponse<>("success", camps, "查詢成功");
		String json = mapper.writeValueAsString(response);

		// 4. 回傳 JSON 回應
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
	}

	@PostMapping("/api/camps/createonecamp")
	public ResponseEntity<MappingJacksonValue> createOneCamp1(@RequestParam boolean withOrders,
			@RequestParam("ownerId") Integer ownerId, @RequestParam("campName") String campName,
			@RequestParam("campContent") String campContent, @RequestParam("campCity") String campCity,
			@RequestParam("campDist") String campDist, @RequestParam("campAddr") String campAddr,
			@RequestParam("campReleaseStatus") Byte campReleaseStatus,
			@RequestParam("campCommentNumberCount") Integer campCommentNumberCount,
			@RequestParam("campCommentSumScore") Integer campCommentSumScore,
			@RequestParam("campRegDate") String campRegDate, // yyyy-MM-dd
			@RequestPart("campPic1") MultipartFile campPic1, @RequestPart("campPic2") MultipartFile campPic2,
			@RequestPart(value = "campPic3", required = false) MultipartFile campPic3,
			@RequestPart(value = "campPic4", required = false) MultipartFile campPic4) {
		CampVO camp = new CampVO();
		try {
			camp.setOwnerId(ownerId);
			camp.setCampName(campName);
			camp.setCampContent(campContent);
			camp.setCampCity(campCity);
			camp.setCampDist(campDist);
			camp.setCampAddr(campAddr);
			camp.setCampReleaseStatus(campReleaseStatus);
			camp.setCampCommentNumberCount(campCommentNumberCount);
			camp.setCampCommentSumScore(campCommentSumScore);
			camp.setCampRegDate(java.sql.Date.valueOf(campRegDate));
			camp.setCampPic1(campPic1.getBytes());
			camp.setCampPic2(campPic2.getBytes());
			if (campPic3 != null)
				camp.setCampPic3(campPic3.getBytes());
			if (campPic4 != null)
				camp.setCampPic4(campPic4.getBytes());

			// 2. 設定動態過濾器
			CampVO newCampVO = campService.createOneCamp(camp);
			ApiResponse<CampVO> response = new ApiResponse<>("success", newCampVO, "查詢成功");

			// 設定動態過濾器
			SimpleFilterProvider filters = new SimpleFilterProvider();
			if (withOrders) {
				filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAll());
			} else {
				filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAllExcept("campsiteOrders"));
			}

			MappingJacksonValue mapping = new MappingJacksonValue(response);
			mapping.setFilters(filters);

			return ResponseEntity.ok().body(mapping);

		} catch (Exception e) {
			ApiResponse<CampVO> failResponse = new ApiResponse<>("fail", camp, "查詢失敗");
			MappingJacksonValue mapping = new MappingJacksonValue(failResponse);
			// 失敗時通常不用過濾，但為了保險也設一個 filter
			SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("campFilter",
					SimpleBeanPropertyFilter.serializeAll());
			mapping.setFilters(filters);
			return ResponseEntity.ok().body(mapping);
		}

	}
//	http://localhost:8081/CJA101G02/api/camps/createonecamp?withOrders=false
	@PostMapping("/api/camps/updateonecamp")
	public ResponseEntity<MappingJacksonValue> updateOneCamp(@RequestParam boolean withOrders,
			@RequestParam("campId") Integer campId, @RequestParam("ownerId") Integer ownerId,
			@RequestParam("campName") String campName, @RequestParam("campContent") String campContent,
			@RequestParam("campCity") String campCity, @RequestParam("campDist") String campDist,
			@RequestParam("campAddr") String campAddr, @RequestParam("campReleaseStatus") Byte campReleaseStatus,
			@RequestParam("campCommentNumberCount") Integer campCommentNumberCount,
			@RequestParam("campCommentSumScore") Integer campCommentSumScore,
			@RequestParam("campRegDate") String campRegDate, // yyyy-MM-dd
			@RequestPart("campPic1") MultipartFile campPic1, @RequestPart("campPic2") MultipartFile campPic2,
			@RequestPart(value = "campPic3", required = false) MultipartFile campPic3,
			@RequestPart(value = "campPic4", required = false) MultipartFile campPic4) {
		CampVO camp = new CampVO();
		try {
			camp.setCampId(campId);
			camp.setOwnerId(ownerId);
			camp.setCampName(campName);
			camp.setCampContent(campContent);
			camp.setCampCity(campCity);
			camp.setCampDist(campDist);
			camp.setCampAddr(campAddr);
			camp.setCampReleaseStatus(campReleaseStatus);
			camp.setCampCommentNumberCount(campCommentNumberCount);
			camp.setCampCommentSumScore(campCommentSumScore);
			camp.setCampRegDate(java.sql.Date.valueOf(campRegDate));
			camp.setCampPic1(campPic1.getBytes());
			camp.setCampPic2(campPic2.getBytes());
			if (campPic3 != null)
				camp.setCampPic3(campPic3.getBytes());
			if (campPic4 != null)
				camp.setCampPic4(campPic4.getBytes());

			// 2. 設定動態過濾器
			CampVO newCampVO = campService.createOneCamp(camp);
			ApiResponse<CampVO> response = new ApiResponse<>("success", newCampVO, "查詢成功");

			// 設定動態過濾器
			SimpleFilterProvider filters = new SimpleFilterProvider();
			if (withOrders) {
				filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAll());
			} else {
				filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAllExcept("campsiteOrders"));
			}

			MappingJacksonValue mapping = new MappingJacksonValue(response);
			mapping.setFilters(filters);

			return ResponseEntity.ok().body(mapping);

		} catch (Exception e) {
			ApiResponse<CampVO> failResponse = new ApiResponse<>("fail", camp, "查詢失敗");
			MappingJacksonValue mapping = new MappingJacksonValue(failResponse);
			// 失敗時通常不用過濾，但為了保險也設一個 filter
			SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("campFilter",
					SimpleBeanPropertyFilter.serializeAll());
			mapping.setFilters(filters);
			return ResponseEntity.ok().body(mapping);
		}

	}

//	@PostMapping("/api/camps/createonecamp")
//	public ApiResponse<CampVO> createOneCamp1( @RequestParam boolean withOrders,@RequestParam("ownerId") Integer ownerId,
//			@RequestParam("campName") String campName, @RequestParam("campContent") String campContent,
//			@RequestParam("campCity") String campCity, @RequestParam("campDist") String campDist,
//			@RequestParam("campAddr") String campAddr, @RequestParam("campReleaseStatus") Byte campReleaseStatus,
//			@RequestParam("campCommentNumberCount") Integer campCommentNumberCount,
//			@RequestParam("campCommentSumScore") Integer campCommentSumScore,
//			@RequestParam("campRegDate") String campRegDate, // yyyy-MM-dd
//			@RequestPart("campPic1") MultipartFile campPic1, @RequestPart("campPic2") MultipartFile campPic2,
//			@RequestPart(value = "campPic3", required = false) MultipartFile campPic3,
//			@RequestPart(value = "campPic4", required = false) MultipartFile campPic4) {
//		CampVO camp = new CampVO();
//		try {
//			camp.setOwnerId(ownerId);
//			camp.setCampName(campName);
//			camp.setCampContent(campContent);
//			camp.setCampCity(campCity);
//			camp.setCampDist(campDist);
//			camp.setCampAddr(campAddr);
//			camp.setCampReleaseStatus(campReleaseStatus);
//			camp.setCampCommentNumberCount(campCommentNumberCount);
//			camp.setCampCommentSumScore(campCommentSumScore);
//			camp.setCampRegDate(java.sql.Date.valueOf(campRegDate));
//			camp.setCampPic1(campPic1.getBytes());
//			camp.setCampPic2(campPic2.getBytes());
//			if (campPic3 != null)
//				camp.setCampPic3(campPic3.getBytes());
//			if (campPic4 != null)
//				camp.setCampPic4(campPic4.getBytes());
//			
//			// 2. 設定動態過濾器
//		    ObjectMapper mapper = new ObjectMapper();
//		    SimpleFilterProvider filters = new SimpleFilterProvider();
//		    
//		    if (withOrders) {
//		        filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAll());
//		    } else {
//		        filters.addFilter("campFilter", SimpleBeanPropertyFilter.serializeAllExcept("campsiteOrders"));
//		    }
//		    
//		    mapper.setFilterProvider(filters);
//			CampVO newCampVO = campService.createOneCamp(camp);
//			return new ApiResponse<>("success", newCampVO, "查詢成功");
//			
//		} catch (Exception e) {
//			return new ApiResponse<>("fail", camp, "查詢失敗");
//		}
//
//	}

	// 取得所有營地訂單，回傳 JSON
	@GetMapping("/api/camps/campsite_orders")
	public ApiResponse<List<CampSiteOrderVO>> getAllCampsiteOrders1() {
		List<CampSiteOrderVO> orders = campsiteOrdSvc.getAllCampsiteOrder();
		return new ApiResponse<>("success", orders, "查詢成功");
	}

	@GetMapping("/api/camps1/{campId}/pic1")
	public void getCampPic2(@PathVariable Integer campId, HttpServletResponse response) throws IOException {

		byte[] img = (campService.getOneCamp(campId)).getCampPic1(); // 從資料庫取得

		response.setContentType("image/jpeg");
		response.getOutputStream().write(img);
	}

//	http://localhost:8081/CJA101G02/api/camps/1001/3
	// 抓取資料庫的營地圖片，提供給前端
	@GetMapping("/api/camps1/{campId}/{num}")
	public void getCampPic3(@PathVariable Integer campId, @PathVariable Integer num, HttpServletResponse response)
			throws IOException {
		byte[] img = null;
		try {
			switch (num) {
			case 1:

				img = (campService.getOneCamp(campId)).getCampPic1();
				break;

			case 2:

				img = (campService.getOneCamp(campId)).getCampPic2();
				break;

			case 3:

				img = (campService.getOneCamp(campId)).getCampPic3();
				break;

			case 4:

				img = (campService.getOneCamp(campId)).getCampPic4();
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + num);
			}
			response.setContentType("image/jpeg");
			response.getOutputStream().write(img);
		} catch (Exception e) {
			System.out.println("營地編號：" + campId + "||第" + num + "張圖片無法讀取");// TODO: handle exception
			img = (campService.getOneCamp(campId)).getCampPic1();
			response.setContentType("image/jpeg");
			response.getOutputStream().write(img);
		}
	}
}
