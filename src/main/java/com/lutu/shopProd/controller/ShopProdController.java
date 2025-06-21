package com.lutu.shopProd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.shopProd.model.ShopProdDTO;
import com.lutu.shopProd.model.ShopProdService;

//api格式 「http://localhost:8081/CJA101G02/api/campsite_orders」

@RestController
@CrossOrigin(origins = "*") //允許所有網域跨域存取
public class ShopProdController {

	@Autowired
	ShopProdService shopProdService;

	/**
     * 查詢所有商品（DTO），回傳 JSON 格式
     * GET http://localhost:8081/CJA101G02/api/productslist
     */
	@GetMapping("/api/productslist")
	public ApiResponse<List<ShopProdDTO>> getAllProds() {
	    List<ShopProdDTO> dtoList = shopProdService.getAllProdsByDTO();
	    return new ApiResponse<>("success", dtoList, "查詢成功");
	}
	
	/**
     * 查詢單一商品（DTO）by 商品 ID
     * GET http://localhost:8081/CJA101G02/api/products/{id}
     */
	@GetMapping("/api/products/{id}") //{id} 是路徑變數，要與 @PathVariable 名稱一致
	public ApiResponse<ShopProdDTO> getProdById(@PathVariable Integer id) { // 這裡的 id 就是從 URL 裡面的 {id} 抓來的
		ShopProdDTO dto = shopProdService.getProdDTOById(id);
	    
	    if (dto != null) {
	        return new ApiResponse<>("success", dto, "查詢成功");
	    } else {
	        return new ApiResponse<>("fail", null, "查無此商品");
	    }
	}

	
	@GetMapping("/api/products/keyword")
	public ApiResponse<List<ShopProdDTO>> getByKeyword(@RequestParam String keyword) {
	    return new ApiResponse<>("success", shopProdService.getByKeyword(keyword), "查詢成功");
	}

	@GetMapping("/api/products/type/{typeId}")
	public ApiResponse<List<ShopProdDTO>> getByType(@PathVariable Integer typeId) {
	    return new ApiResponse<>("success", shopProdService.getByType(typeId), "查詢成功");
	}

	@GetMapping("/api/products/latest")
	public ApiResponse<List<ShopProdDTO>> getLatest() {
	    return new ApiResponse<>("success", shopProdService.getLatestProds(), "查詢成功");
	}

	@GetMapping("/api/products/discount")
	public ApiResponse<List<ShopProdDTO>> getDiscounted() {
	    return new ApiResponse<>("success", shopProdService.getDiscountProds(), "查詢成功");
	}

	@GetMapping("/api/products/random")
	public ApiResponse<List<ShopProdDTO>> getRandom(@RequestParam(defaultValue = "6") int limit) {
	    return new ApiResponse<>("success", shopProdService.getRandomProds(limit), "推薦成功");
	}

	//新增商品（接收 DTO）
	@PostMapping("/api/addprod")
	public ApiResponse<ShopProdDTO> addProd(@RequestBody ShopProdDTO dto) {
	    ShopProdDTO saved = shopProdService.addProdByDTO(dto);
	    return new ApiResponse<>("success", saved, "新增成功");
	}

	//修改商品（接收 DTO）
	@PutMapping("/api/updateprod")
	public ApiResponse<ShopProdDTO> updateProd(@RequestBody ShopProdDTO dto) {
	    ShopProdDTO updated = shopProdService.updateProdByDTO(dto);
	    if (updated != null) {
	        return new ApiResponse<>("success", updated, "修改成功");
	    } else {
	        return new ApiResponse<>("fail", null, "查無此商品，無法修改");
	    }
	}
	
//	@PostMapping("/addoneprod")
//	public ApiResponse<CampVO> createOneCamp(@RequestParam("ownerId") Integer ownerId,
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
//			CampVO newCampVO = campService.createOneCamp(camp);
//			return new ApiResponse<>("success", newCampVO, "查詢成功");
//		} catch (Exception e) {
//			return new ApiResponse<>("fail", camp, "查詢失敗");
//		}
//
//	}
//
//	@GetMapping("/api/camps/{campId}/pic1")
//	public void getCampPic1(@PathVariable Integer campId, HttpServletResponse response) throws IOException {
//
//		byte[] img = (campService.getOneCamp(campId)).getCampPic1(); // 從資料庫取得
//
//		response.setContentType("image/jpeg");
//		response.getOutputStream().write(img);
//	}
//
//	// 抓取資料庫的營地圖片，提供給前端
//	@GetMapping("/api/camps/{campId}/{num}")
//	public void getCampPic3(@PathVariable Integer campId, @PathVariable Integer num, HttpServletResponse response)
//			throws IOException {
//		byte[] img = null;
//		try {
//			switch (num) {
//			case 1:
//
//				img = (campService.getOneCamp(campId)).getCampPic1();
//				break;
//
//			case 2:
//
//				img = (campService.getOneCamp(campId)).getCampPic2();
//				break;
//
//			case 3:
//
//				img = (campService.getOneCamp(campId)).getCampPic3();
//				break;
//
//			case 4:
//
//				img = (campService.getOneCamp(campId)).getCampPic4();
//				break;
//
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + num);
//			}
//			response.setContentType("image/jpeg");
//			response.getOutputStream().write(img);
//		} catch (Exception e) {
//			System.out.println("營地編號：" + campId + "||第" + num + "張圖片無法讀取");// TODO: handle exception
//			img = (campService.getOneCamp(campId)).getCampPic1();
//			response.setContentType("image/jpeg");
//			response.getOutputStream().write(img);
//		}
//	}
}
