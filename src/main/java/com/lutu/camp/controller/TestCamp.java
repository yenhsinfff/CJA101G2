package com.lutu.camp.controller;

import java.sql.Date;

import java.util.List;

import com.lutu.camp.model.CampService;
import com.lutu.camp.model.CampVO;

public class TestCamp {

	public static void main(String[] args) {
		CampService campSvc = new CampService();
		// // 建立假圖片資料
		byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料

		//新增營地
		CampVO campVO1 = new CampVO();

		campVO1.setOwnerId(20000008);
		campVO1.setCampName("陽明山小木屋");
		campVO1.setCampContent("擁有絕佳山景與溫泉的營地");
		campVO1.setCampCity("台北市");
		campVO1.setCampDist("北投區");
		campVO1.setCampAddr("泉源路123號");
		campVO1.setCampReleaseStatus((byte) 1);
		campVO1.setCampPic1(dummyPic);
		campVO1.setCampPic2(dummyPic);
		campVO1.setCampPic3(null);
		campVO1.setCampPic4(null);
		campVO1.setCampCommentNumberCount(0);
		campVO1.setCampCommentSumScore(0);
		campVO1.setCampRegDate(Date.valueOf("2025-03-15"));

		CampVO newCampVO1 = campSvc.addCamp(campVO1);

		// 更新資料
		CampVO campVO2 = new CampVO();

		campVO2.setOwnerId(20000008);
		campVO2.setCampName("陽明山小木屋");
		campVO2.setCampContent("擁有絕佳山景與溫泉的營地");
		campVO2.setCampCity("桃園市");
		campVO2.setCampDist("北投區");
		campVO2.setCampAddr("泉源路123號");
		campVO2.setCampReleaseStatus((byte) 1);
		campVO2.setCampId(1008);

		CampVO newCampVO2 = campSvc.updateCamp(campVO2);
		
		//刪除資料
		campSvc.deleteCamp(1011);
		
		//查詢單筆資料
		CampVO campVO = campSvc.getOneCamp(1008);
		System.out.println(campVO.getCampId() + " " + campVO.getCampName() + " " + campVO.getOwnerId() + " "
				+ campVO.getCampCity() + " " + campVO.getCampAddr());

		System.out.println("OKK");

		// 查詢所有營地
		List<CampVO> campVOList = campSvc.getAll();
		for (CampVO campVO3 : campVOList) {
			System.out.println(campVO3.getCampId() + " " + campVO3.getCampName() + " " + campVO3.getOwnerId() + " "
					+ campVO3.getCampCity() + " " + campVO3.getCampAddr());
		}
	}

}
