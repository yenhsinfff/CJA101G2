package com.camp.controller;

import java.sql.Date;
import java.util.List;

import com.camp.model.CampService;
import com.camp.model.CampVO;

public class TestCamp {

	public static void main(String[] args) {
		CampService campSvc = new CampService();
		// CampVO campVO = new CampVO();
		// // 新增營地
		// // campSvc.addCamp(0, null, null, null, null, null, 0, null, null, null,
		// null,
		// // 0, 0, null)
		// // 建立假圖片資料
		byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料
		// // campVO = campSvc.addCamp(10000005, "JAVA營地", "JAVA營地內容設定", "桃園市", "復興鄉",
		// // "464號", 0, dummyPic, dummyPic, null, null, 12,
		// // 60, "2025-05-12");

		// // Date sqlDate = new Date(System.currentTimeMillis());

		//新增營地
//		CampVO campVO1 = new CampVO();
//
//		campVO1.setOwnerId(20000008);
//		campVO1.setCampName("陽明山小木屋");
//		campVO1.setCampContent("擁有絕佳山景與溫泉的營地");
//		campVO1.setCampCity("台北市");
//		campVO1.setCampDist("北投區");
//		campVO1.setCampAddr("泉源路123號");
//		campVO1.setCampReleaseStatus((byte) 1);
//		campVO1.setCampPic1(dummyPic);
//		campVO1.setCampPic2(dummyPic);
//		campVO1.setCampPic3(null);
//		campVO1.setCampPic4(null);
//		campVO1.setCampCommentNumberCount(0);
//		campVO1.setCampCommentSumScore(0);
//		campVO1.setCampRegDate(Date.valueOf("2025-03-15"));
//		campVO1.setCampId(1008);
//
//		CampVO newCampVO1 = campSvc.updateCamp(campVO1);
//
//		// 更新資料
//		CampVO campVO2 = new CampVO();
//
//		campVO2.setOwnerId(20000008);
//		campVO2.setCampName("陽明山小木屋");
//		campVO2.setCampContent("擁有絕佳山景與溫泉的營地");
//		campVO2.setCampCity("台北市");
//		campVO2.setCampDist("北投區");
//		campVO2.setCampAddr("泉源路123號");
//		campVO2.setCampReleaseStatus((byte) 1);
//		campVO2.setCampPic1(dummyPic);
//		campVO2.setCampPic2(dummyPic);
//		campVO2.setCampPic3(null);
//		campVO2.setCampPic4(null);
//		campVO2.setCampCommentNumberCount(0);
//		campVO2.setCampCommentSumScore(0);
//		campVO2.setCampRegDate(Date.valueOf("2025-03-15"));
//		campVO2.setCampId(1008);
//
//		CampVO newCampVO2 = campSvc.updateCamp(campVO2);
//		
		//刪除資料
//		campSvc.deleteCamp(1008);

//         CampVO newCamp = campSvc.addCamp(
//         10000005, // ownerId
//         "陽明山小木屋", // campName
//         "擁有絕佳山景與溫泉的營地", // campContent
//         "台北市", // campCity
//         "北投區", // campDist
//         "泉源路123號", // campAddr
//         (byte) 1, // campReleaseStatus (1 = 上架)
//         dummyPic, // campPic1
//         dummyPic, // campPic2
//         null, // campPic3
//         null, // campPic4
//         0, // campCommentNumberCount
//         0, // campCommentSumScore
//         Date.valueOf("2025-03-15") // campRegDate
//         );
		System.out.println("OKK");

		// 查詢所有營地
		List<CampVO> campVOList = campSvc.getAll();
		for (CampVO campVO : campVOList) {
			System.out.println(campVO.getCampId() + " " + campVO.getCampName() + " " + campVO.getOwnerId() + " "
					+ campVO.getCampCity() + " " + campVO.getCampAddr());
		}
	}

}
