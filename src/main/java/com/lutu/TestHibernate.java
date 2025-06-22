package com.lutu;

import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lutu.campsite.model.CampsiteService;
import com.lutu.camptracklist.model.CampTrackListService;
import com.lutu.camptracklist.model.CampTrackListVO;
import com.lutu.member.model.MemberService;
import com.lutu.member.model.MemberVO;

@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu")  //掃描           table
public class TestHibernate {


	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestHibernateCampsiteOrder.class, args);
		SpringApplication app = new SpringApplication(TestHibernate.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		ConfigurableApplicationContext context = app.run(args);
		
		

// ================================ 營地房間明細=======================================
		CampsiteService campsiteSvc = context.getBean(CampsiteService.class);

//				List<CampsiteVO> list = campsiteSvc.getAll();
//				for (CampsiteVO vo : list) {
//					System.out.print(vo.getCampsiteId() + ",");
//					System.out.print(vo.getCampsiteType().getId() + ",");
//					System.out.print(vo.getCampsiteIdName() + ",");
//					System.out.print(vo.getCamperName() + ",");
//					System.out.println();
//				}

		// 查詢-findByPrimaryKey BundleItemVO_getOneBundleItem
		// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//		        CampsiteVO vo = campsiteSvc.getOneCampsite(3007);
//				System.out.print(vo.getCampsiteId() + ",");
//				System.out.print(vo.getCampsiteType().getId() + ",");
//				System.out.print(vo.getCampsiteIdName() + ",");
//				System.out.print(vo.getCamperName());




		// 刪除 --> 自訂的刪除方法
//				campsiteSvc.deleteCampsite(3016);
				
		
//========================== 測試關聯===========================================

		// 透過營地查詢營地房型

//		CampService svc = context.getBean(CampService.class);
//		CampVO campVO = svc.getOneCamp(1006);
//		Set<CampsiteTypeVO> campsiteTypes = campVO.getCampsiteTypes();
//		for (CampsiteTypeVO campsiteType : campsiteTypes) {
//			System.out.println("營地房型編號+營地編號：" + campsiteType.getId() + ", 營地房型名稱：" + campsiteType.getCampsiteName() + ", 房型價格："
//					+ campsiteType.getCampsitePrice());
//		}
		
		// 透過營地房型查詢營地房間

//		CampsiteTypeService svc = context.getBean(CampsiteTypeService.class);
//		CampsiteTypeVO campsiteTypeVO = svc.getOneCampsiteType(2006, 1004);
//		Set<CampsiteVO> campsites = campsiteTypeVO.getCampsites();
//		for (CampsiteVO campsite : campsites) {
//				System.out.println("營地房間編號：" + campsite.getCampsiteId() + ", 營地房間名稱：" + campsite.getCampsiteIdName() + ", 露營者姓名："
//				+ campsite.getCamperName());
//		}	
		
		// 透過營地收藏查詢營地資訊

//		CampTrackListService svc = context.getBean(CampTrackListService.class);
//		CampTrackListVO campTrackListVO = svc.getOneCampTrackList(1002, 10000001); // memId, campId
//
//		if (campTrackListVO != null) {
//		    CampVO camp = campTrackListVO.getCamp();
//		    System.out.println("營地編號：" + camp.getCampId());
//		    System.out.println("營地名稱：" + camp.getCampName());
//		    System.out.println("營地縣市：" + camp.getCampCity());
//		}	
		
		
		//透過會員查詢收藏營地
//		MemberService svc = context.getBean(MemberService.class);
//		MemberVO memberVO = svc.getOneMember(10000001);
//		Set<CampTrackListVO> campTrackLists = memberVO.getCampTrackLists();
//		for(CampTrackListVO campTrackList : campTrackLists) {
//			System.out.println("營地編號：" + campTrackList.getId());
//		}
				
		

// ================================營地型別(複合主鍵)==========================================

//		CampsiteTypeService campsiteTypeSvc = context.getBean(CampsiteTypeService.class);

//				List<CampsiteTypeVO> list = campsiteTypeSvc.getAll();
//				for (CampsiteTypeVO vo : list) {
//					System.out.print(vo.getId() + ",");
//					System.out.print(vo.getCampsiteName() + ",");
//					System.out.print(vo.getCampsitePeople() + ",");
//					System.out.print(vo.getCampsiteNum() + ",");
//					System.out.print(vo.getCampsitePrice() + ",");
//					System.out.print(vo.getCampsitePic1() + ",");
//					System.out.print(vo.getCampsitePic2() + ",");
//					System.out.print(vo.getCampsitePic3() + ",");
//					System.out.print(vo.getCampsitePic4() + ",");
//					System.out.println();
//				}

		// 查詢-findByPrimaryKey
		// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//		        CampsiteTypeVO vo = campsiteTypeSvc.getOneCampsiteType(2001, 1001);
//		        System.out.print(vo.getCampsiteName() + ",");
//				System.out.print(vo.getCampsitePeople() + ",");
//				System.out.print(vo.getCampsiteNum() + ",");
//				System.out.print(vo.getCampsitePrice() + ",");
//				System.out.print(vo.getCampsitePic1() + ",");
//				System.out.print(vo.getCampsitePic2() + ",");
//				System.out.print(vo.getCampsitePic3() + ",");
//				System.out.print(vo.getCampsitePic4());	
//				System.out.println();

		// 修改
//		        CampsiteTypeVO vo = campsiteTypeSvc.getOneCampsiteType(2013,1008);
//		        vo.setCampsiteName("非常豪華帳棚");
//		        vo.setCampsitePeople(88);
//		        vo.setCampsiteNum((byte)3);
//		        vo.setCampsitePrice(6666);
////		        vo.setCampsitePic1(null);
//				campsiteTypeSvc.updateCampsiteType(vo);

		// 新增
//		CampsiteTypeVO vo = new CampsiteTypeVO();
//		CampsiteTypeVO.CompositeDetail id = new CampsiteTypeVO.CompositeDetail();
//		
//		// 設定關聯的 camp（重點）
//		CampVO camp = new CampVO();
//		camp.setCampId(1009);
//		vo.setCamp(camp);	
//		id.setCampsiteTypeId(2015); //複合主鍵無法自動遞增，務必手動設定
//		vo.setId(id);
//		
//        vo.setCampsiteName("非常豪華帳棚");
//        vo.setCampsitePeople(88);
//        vo.setCampsiteNum((byte)3);
//        vo.setCampsitePrice(6666);
//        vo.setCampsitePic1("fake-image-data".getBytes());
//        campsiteTypeSvc.addCampsiteType(vo);

		// 刪除 --> 自訂的刪除方法
//		CampsiteTypeVO.CompositeDetail id = new CampsiteTypeVO.CompositeDetail(2014, 1006);
//		campsiteTypeSvc.deleteCampsiteType(id);


		// ================================ 營地加購項目明細=======================================
//		BundleItemDetailsService bundleItemDetailsSvc = context.getBean(BundleItemDetailsService.class);

//				BundleItemDetailsVO_getAllBundleItemDetails
//				List<BundleItemDetailsVO> list = bundleItemDetailsSvc.getAll();
//				for (BundleItemDetailsVO vo : list) {
//					System.out.print(vo.getBundleDetailsId() + ",");
//					System.out.print(vo.getCampsiteDetailsId() + ",");
//					System.out.print(vo.getBundleId() + ",");
//					System.out.print(vo.getBundleBuyNum() + ",");
//					System.out.print(vo.getBundleBuyAmount() + ",");
//					System.out.println();
//				}

		// 查詢-findByPrimaryKey BundleItemVO_getOneBundleItem
		// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//				BundleItemDetailsVO vo = bundleItemDetailsSvc.getOneBundleItemDetails(8001);
//				System.out.print(vo.getBundleDetailsId() + ",");
//				System.out.print(vo.getCampsiteDetailsId() + ",");
//				System.out.print(vo.getBundleId()+ ",");
//				System.out.print(vo.getBundleBuyNum()+ ",");
//				System.out.print(vo.getBundleBuyAmount());

		// 修改
//				BundleItemDetailsVO vo = bundleItemDetailsSvc.getOneBundleItemDetails(8003);
//				vo.setBundleDetailsId(8004);
//				vo.setCampsiteDetailsId(1000000007);
//				vo.setBundleId(3999);
//				vo.setBundleBuyNum(3);
//				vo.setBundleBuyAmount(6000);
//				bundleItemDetailsSvc.addBundleItemDetails(vo);

		// 新增
//				BundleItemDetailsVO vo = new BundleItemDetailsVO();
//				vo.setCampsiteDetailsId(1000000010);
//				vo.setBundleId(3999);
//				vo.setBundleBuyNum(3);
//				vo.setBundleBuyAmount(6000);
//				bundleItemDetailsSvc.addBundleItemDetails(vo);

		// 刪除 --> 自訂的刪除方法
//				bundleItemSvc.deleteBundleItem(8);

		// ● 刪除 //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
//		 System.out.println("--------------------------------");
//		 repository.deleteById(7001);
//		 System.out.println("--------------------------------");

//================================ 營地加購項目 =======================================
//		BundleItemService bundleItemSvc = context.getBean(BundleItemService.class);

//		BundleItemVO_getAllBundleItem
//		List<BundleItemVO> list = bundleItemSvc.getAll();
//		for (BundleItemVO bundleItemVO : list) {
//			System.out.print(bundleItemVO.getBundleId() + ",");
//			System.out.print(bundleItemVO.getCampId() + ",");
//			System.out.print(bundleItemVO.getBundleName()+ ",");
//			System.out.print(bundleItemVO.getBundleAddDate()+ ",");
//			System.out.print(bundleItemVO.getBundlePrice()+ ",");
//			System.out.println();
//		}

		// 查詢-findByPrimaryKey BundleItemVO_getOneBundleItem
		// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//		BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(1);
//		System.out.print(bundleItemVO.getBundleId() + ",");
//		System.out.print(bundleItemVO.getCampId() + ",");
//		System.out.print(bundleItemVO.getBundleName()+ ",");
//		System.out.print(bundleItemVO.getBundleAddDate()+ ",");
//		System.out.print(bundleItemVO.getBundlePrice());

		// 修改
//		BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(13);
//		bundleItemVO.setCampId(1008);
//		bundleItemVO.setBundleName("手作課程");
//		bundleItemVO.setBundlePrice(3999);
//		LocalDate localDate = LocalDate.parse("2025-05-29");
//		bundleItemVO.setBundleAddDate(localDate);
//		bundleItemSvc.addBundleItem(bundleItemVO);
//		
		// 新增
//		BundleItemVO bundleItemVO = new BundleItemVO();
//		bundleItemVO.setCampId(1003);
//		bundleItemVO.setBundleName("手作地毯課程");
//		bundleItemVO.setBundlePrice(3600);
//		String dateStr = "2025-01-01";
//		java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
//		bundleItemVO.setBundleAddDate(sqlDate);
//		bundleItemSvc.addBundleItem(bundleItemVO);

		// 刪除 --> 自訂的刪除方法
//		bundleItemSvc.deleteBundleItem(8);

		// ● 刪除 //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
		// System.out.println("--------------------------------");
		// repository.deleteById(7001);
		// System.out.println("--------------------------------");

//================================ 營地收藏 =======================================
		CampTrackListService campTrackListSvc = context.getBean(CampTrackListService.class);

		// CampTrackListVO _ getAllCampTrackList
//		      		List<CampTrackListVO> list = campTrackListSvc.getAll();
//		      		for (CampTrackListVO campTrackListVO : list) {
//		      			System.out.print(campTrackListVO.getCompositeKey().getCampId()+ ",");
//		      			System.out.print(campTrackListVO.getCompositeKey().getMemId() + ",");
//		      			System.out.print(campTrackListVO.getMemTrackDate());
//		      			System.out.println();
//		      		}

		// 查詢-findByPrimaryKey CampTrackListVO_getOneCampTrackList
		// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//				CampTrackListVO campTrackListVO = campTrackListSvc.getOneCampTrackList(1001, 10000001);
//				System.out.print(campTrackListVO.getCompositeKey().getCampId()+ ",");
//				System.out.print(campTrackListVO.getCompositeKey().getMemId() + ",");
//				System.out.print(campTrackListVO.getMemTrackDate());

		// 新增
//				CampTrackListVO campTrackListVO = new CampTrackListVO();
//				CampTrackListVO.CompositeDetail id = new CampTrackListVO.CompositeDetail();
//				id.setCampId(1005);
//				id.setMemId(10000001);
//				campTrackListVO.setCompositeKey(id);
//				String dateStr = "2025-05-30";
//				java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
//				campTrackListVO.setMemTrackDate(sqlDate);
//				campTrackListSvc.addCampTrackList(campTrackListVO);

//===================================================================================================				

//        CampSiteOrderService campsiteOrdSvc = context.getBean(CampSiteOrderService.class);
//
//        	//CampSiteOrderVO _ getAllCampsiteOrder
//      		List<CampSiteOrderVO> list = campsiteOrdSvc.getAllCampsiteOrder();
//      		for (CampSiteOrderVO campSiteOrder : list) {
//      			System.out.print(campSiteOrder.getCampsiteOrderId() + ",");
//      			System.out.print(campSiteOrder.getCampId() + ",");
//      			System.out.print(campSiteOrder.getMemId() + ",");
//      			System.out.println();
//      		}

		// getALLCamp
//		CampService campService = context.getBean(CampService.class);
//      		List<com.lutu.camp_hibernate.model.CampVO> list = campService.getAllCamp();
//  		for (com.lutu.camp_hibernate.model.CampVO campVO : list) {
//			System.out.print(campVO.getCampId() + ",");
//			System.out.print(campVO.getCampName() + ",");
//			System.out.print(campVO.getCampContent() + ",");
//			System.out.println();
//		}

		// GetOneCamp
//  		com.lutu.camp_hibernate.model.CampVO campVO = campService.getOneCamp(1001);
//  		System.out.print(campVO.getCampId() + ",");
//		System.out.print(campVO.getCampName() + ",");
//		System.out.print(campVO.getCampContent() + ",");

		// 測試關聯

//		CampService campService = context.getBean(CampService.class);

//		CampVO campVO = campService.getOneCamp(1001);
//		Set<CampSiteOrderVO> orders = campVO.getCampsiteOrders();
//		for (CampSiteOrderVO order : orders) {
//			System.out.println("訂單編號：" + order.getCampsiteOrderId() + ", 會員ID：" + order.getMemId() + ", content："
//					+ order.getCampVO().getCampContent());
//		}

		// 透過訂單查詢
//		CampSiteOrderService campSiteOrderService = context.getBean(CampSiteOrderService.class);
//		CampSiteOrderVO campSiteOrderVO = campSiteOrderService.getOneCampsiteOrder("ORD20250124001");
//		Set<CampSiteOrderDetailsVO> campsiteOrderDetails = campSiteOrderVO.getCampSiteOrderDetails();
//		for (CampSiteOrderDetailsVO detail : campsiteOrderDetails) {
//			System.out.println("營地訂單明細：" + detail.getCampsiteDetailsId() + ", 明細總價：" + detail.getCampsiteAmount() + ", content："
//					+ detail.getcampSiteOrderVO().getCommentContent());
//		}

		// 測試取消訂單
// 				CampsiteCancellationService campsiteCancellationSvc = context.getBean(CampsiteCancellationService.class);
// 				List<CampsiteCancellationVO> campsiteCancellationList = campsiteCancellationSvc.getAllCampsiteCancellation();
// 				for (CampsiteCancellationVO campsiteCancellationVO : campsiteCancellationList) {
// 				System.out.println("營地訂單明細：" + campsiteCancellationVO.getCampsiteCancelId() + ", 明細總價：" + campsiteCancellationVO.getCampsiteCancelReason() + ", content："
// 						+ campsiteCancellationVO.getCampsiteCancelStatus());
// 			}

		context.close();
	}

}
