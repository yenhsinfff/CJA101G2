package com.lutu.campsitetype.controller;

import java.util.List;

import com.lutu.campsitetype.model.CampsiteTypeDAO;
import com.lutu.campsitetype.model.CampsiteTypeDAO_interface;
import com.lutu.campsitetype.model.CampsiteTypeVO;

public class TestCampsiteType {

	public static void main(String[] args) throws Exception {
		CampsiteTypeDAO_interface dao = new CampsiteTypeDAO();

		// 新增
//		
		byte[] dummyPic = "image bytes".getBytes(); // 模擬圖片資料
//		
//		CampsiteTypeVO campsiteType1 = new CampsiteTypeVO();
//		campsiteType1.setCampId(1000);
//		campsiteType1.setCampsiteName("靠自己的自搭帳營地");
//		campsiteType1.setCampsitePeople(10);
//		campsiteType1.setCampsiteNum((byte) 6);
//		campsiteType1.setCampsitePrice(8888);
//		campsiteType1.setCampsitePic1(dummyPic);
//		campsiteType1.setCampsitePic2(null);
//		campsiteType1.setCampsitePic3(null);
//		campsiteType1.setCampsitePic4(null);
//		dao.insert(campsiteType1);

		// 修改

//		CampsiteTypeVO campsiteType2 = new CampsiteTypeVO();
//		campsiteType2.setCampsiteTypeId(2004);
//		campsiteType2.setCampId(1234);
//		campsiteType2.setCampsiteName("超級豪華帳棚");
//		campsiteType2.setCampsitePeople(10);
//		campsiteType2.setCampsiteNum((byte) 6);
//		campsiteType2.setCampsitePrice(99999);
//		campsiteType2.setCampsitePic1(dummyPic);
//		campsiteType2.setCampsitePic2(dummyPic);
//		campsiteType2.setCampsitePic3(null);
//		campsiteType2.setCampsitePic4(null);
//		dao.update(campsiteType2);

		// 刪除
//		dao.delete(2004);

//		 查詢單筆
//		CampsiteTypeVO campsiteType3 = dao.findByPK(2001);
//		System.out.print(campsiteType3.getCampsiteTypeId() + ",");
//		System.out.print(campsiteType3.getCampId() + ",");
//		System.out.print(campsiteType3.getCampsiteName() + ",");
//		System.out.print(campsiteType3.getCampsitePeople() + ",");
//		System.out.print(campsiteType3.getCampsiteNum() + ",");
//		System.out.print(campsiteType3.getCampsitePrice() + ",");
//		System.out.print(campsiteType3.getCampsitePic1() + ",");
//		System.out.print(campsiteType3.getCampsitePic2() + ",");
//		System.out.print(campsiteType3.getCampsitePic3() + ",");
//		System.out.print(campsiteType3.getCampsitePic4());
//		System.out.println();

		// 查詢多筆

		List<CampsiteTypeVO> list = dao.getAll();
		for(CampsiteTypeVO campsiteVO : list) {
		System.out.print(campsiteVO.getCampsiteTypeId() + ",");
		System.out.print(campsiteVO.getCampId() + ",");
		System.out.print(campsiteVO.getCampsiteName() + ",");
		System.out.print(campsiteVO.getCampsitePeople() + ",");
		System.out.print(campsiteVO.getCampsiteNum() + ",");
		System.out.print(campsiteVO.getCampsitePrice() + ",");
		System.out.print(campsiteVO.getCampsitePic1() + ",");
		System.out.print(campsiteVO.getCampsitePic2() + ",");
		System.out.print(campsiteVO.getCampsitePic3() + ",");
		System.out.print(campsiteVO.getCampsitePic4());
		System.out.println();

		}

	}

}
