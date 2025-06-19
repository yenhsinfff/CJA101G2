package com.lutu;




import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lutu.bundleitem.model.BundleItemService;
import com.lutu.bundleitem.model.BundleItemVO;
import com.lutu.camp.model.CampService;
import com.lutu.camptracklist.model.CampTrackListService;

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

//================================ 營地加購項目 =======================================
		BundleItemService bundleItemSvc = context.getBean(BundleItemService.class);

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
		BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(13);
		bundleItemVO.setCampId(1008);
		bundleItemVO.setBundleName("手作課程");
		bundleItemVO.setBundlePrice(3999);
		LocalDate localDate = LocalDate.parse("2025-05-29");
		bundleItemVO.setBundleAddDate(localDate);
		bundleItemSvc.addBundleItem(bundleItemVO);
		
		// 新增
//		BundleItemVO bundleItemVO = new BundleItemVO();
//		bundleItemVO.setCampId(1003);
//		bundleItemVO.setBundleName("手作地毯課程");
//		bundleItemVO.setBundlePrice(3600);
//		String dateStr = "2025-01-01";
//		java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
//		bundleItemVO.setBundleAddDate(sqlDate);
//		bundleItemSvc.addBundleItem(bundleItemVO);

		//刪除   --> 自訂的刪除方法
		bundleItemSvc.deleteBundleItem(8);
		
		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
		//System.out.println("--------------------------------");
		//repository.deleteById(7001);      
		//System.out.println("--------------------------------");

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

		// 刪除CampTrackListVO_deleteCampTrackList
//				CampTrackListVO.CompositeDetail id2 = new CampTrackListVO.CompositeDetail();
//				id2.setCampId(1005);
//				id2.setMemId(10000001);
//				campTrackListSvc.deleteCampTrackList(id2.getCampId(), id2.getMemId());

		// =========================================================================

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

		
		//透過訂單查詢
		CampSiteOrderService campSiteOrderService = context.getBean(CampSiteOrderService.class);
		CampSiteOrderVO campSiteOrderVO = campSiteOrderService.getOneCampsiteOrder("ORD20250124001");
		Set<CampSiteOrderDetailsVO> campsiteOrderDetails = campSiteOrderVO.getCampSiteOrderDetails();
		for (CampSiteOrderDetailsVO detail : campsiteOrderDetails) {
			System.out.println("營地訂單明細：" + detail.getCampsiteDetailsId() + ", 明細總價：" + detail.getCampsiteAmount() + ", content："
					+ detail.getcampSiteOrderVO().getCommentContent());
		}
		

		context.close();
	}

}
