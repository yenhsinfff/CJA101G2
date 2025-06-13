package com.lutu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.camp_hibernate.model.CampService;
import com.lutu.camp_hibernate.model.CampVO;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

//api格式 「http://localhost:8081/CJA101G02/api/campsite_orders」

@RestController
@CrossOrigin(origins = "*")
public class ApiController {

	@Autowired
	CampSiteOrderService campsiteOrdSvc;

	@Autowired
	CampService campService;

	// 取得所有營地訂單，回傳 JSON
	@GetMapping("/api/getallcamps")
	public ApiResponse<List<CampVO>> getAllCamps() {
		List<CampVO> camps = campService.getAllCamp();
		return new ApiResponse<>("success", camps, "查詢成功");
	}

	@PostMapping("/api/createonecamp")
	public ApiResponse<CampVO> createOneCamp(@RequestParam("ownerId") Integer ownerId,
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

			CampVO newCampVO = campService.createOneCamp(camp);
			return new ApiResponse<>("success", newCampVO, "查詢成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", camp, "查詢失敗");
		}

	}

	// 取得所有營地訂單，回傳 JSON
	@GetMapping("/api/campsite_orders")
	public ApiResponse<List<CampSiteOrderVO>> getAllCampsiteOrders() {
		List<CampSiteOrderVO> orders = campsiteOrdSvc.getAllCampsiteOrder();
		return new ApiResponse<>("success", orders, "查詢成功");
	}

	@GetMapping("/api/camps/{campId}/pic1")
	public void getCampPic1(@PathVariable Integer campId, HttpServletResponse response) throws IOException {

		byte[] img = (campService.getOneCamp(campId)).getCampPic1(); // 從資料庫取得

		response.setContentType("image/jpeg");
		response.getOutputStream().write(img);
	}

	// 抓取資料庫的營地圖片，提供給前端
	@GetMapping("/api/camps/{campId}/{num}")
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

//    @GetMapping("/api/campsite_orders")
//    public List<CampSiteOrderVO> getAllCampsiteOrders() {
//        List<CampSiteOrderVO> orders = campsiteOrdSvc.getAllCampsiteOrder();
//        return orders;
//    }
}
