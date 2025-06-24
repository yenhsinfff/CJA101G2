package com.lutu;


import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lutu.article.model.ArticlesService;
import com.lutu.article.model.ArticlesVO;
import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;

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
		

		//================================ 營地型別項目明細 =======================================
//		CampsiteTypeService campsiteTypeSvc = context.getBean(CampsiteTypeService.class);
//
////				CampsiteTypeVO_getAllCampsiteType
//				List<CampsiteTypeVO> list = campsiteTypeSvc.getAll();
//				for (CampsiteTypeVO vo : list) {
//					System.out.print(vo.getCompositeKey() + ",");
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

				//刪除   --> 自訂的刪除方法
//				bundleItemSvc.deleteBundleItem(8);
				
				//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
				//System.out.println("--------------------------------");
				//repository.deleteById(7001);      
				//System.out.println("--------------------------------");
	
		
				
		
		
		//================================ 營地加購項目明細 =======================================
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

				//刪除   --> 自訂的刪除方法
//				bundleItemSvc.deleteBundleItem(8);
				
				//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
				//System.out.println("--------------------------------");
				//repository.deleteById(7001);      
				//System.out.println("--------------------------------");
	
		
		
		//================================ 文章 =======================================
		ArticlesService articlesSvc = context.getBean(ArticlesService.class);

//				BundleItemVO_getAllBundleItem
//				List<ArticlesVO> list = articlesSvc.getAll();
//				for (ArticlesVO VO : list) {
//					System.out.print(VO.getAcId() + ",");
//					System.out.print(VO.getAcTitle() + ",");
//					System.out.print(VO.getMemberVO()+ ",");
//					System.out.print(VO.getArticleTypeVO()+ ",");
//					System.out.print(VO.getAcTime()+ ",");
//					System.out.print(VO.getAcContext()+ ",");
//					System.out.print(VO.getAcStatus()+ ",");
//					System.out.print(VO.getArticleImages()+ ",");
//					System.out.print(VO.getReplies()+ ",");
//					System.out.print(VO.getNiceArticle()+ ",");
//					System.out.print(VO.getArticleReport()+ ",");
//					System.out.print(VO.getAcFavRecord()+ ",");
//					System.out.println();
//				}

				//-------------------------------------------------------------------------------		
				
			    // 只顯示基本資料（不包含延遲載入的集合）
		        List<ArticlesVO> list = articlesSvc.getAll();
		        for (ArticlesVO vo : list) {
		            System.out.print(vo.getAcId() + ",");
		            System.out.print(vo.getAcTitle() + ",");
		            System.out.print(vo.getMemberVO() + ",");
		            System.out.print(vo.getArticleTypeVO() + ",");
		            System.out.print(vo.getAcTime() + ",");
		            System.out.print(vo.getAcContext() + ",");
		            System.out.print(vo.getAcStatus());
		            System.out.println(); // 不包含延遲載入的集合
		        }
		        
				//-------------------------------------------------------------------------------	
		        
				
//				System.out.print(VO.()+ ",");

				// 查詢-findByPrimaryKey BundleItemVO_getOneBundleItem
				// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//				BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(1);
//				System.out.print(bundleItemVO.getBundleId() + ",");
//				System.out.print(bundleItemVO.getCampId() + ",");
//				System.out.print(bundleItemVO.getBundleName()+ ",");
//				System.out.print(bundleItemVO.getBundleAddDate()+ ",");
//				System.out.print(bundleItemVO.getBundlePrice());

				// 修改
//		        ArticlesVO VO = articlesSvc.getOneBundleItem(13);
//				bundleItemVO.setCampId(1008);
//				bundleItemVO.setBundleName("手作課程");
//				bundleItemVO.setBundlePrice(3999);
//				LocalDate localDate = LocalDate.parse("2025-05-29");
//				bundleItemVO.setBundleAddDate(localDate);
//				bundleItemSvc.addBundleItem(bundleItemVO);
				
				// 新增
//				BundleItemVO bundleItemVO = new BundleItemVO();
//				bundleItemVO.setCampId(1003);
//				bundleItemVO.setBundleName("手作地毯課程");
//				bundleItemVO.setBundlePrice(3600);
//				String dateStr = "2025-01-01";
//				java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
//				bundleItemVO.setBundleAddDate(sqlDate);
//				bundleItemSvc.addBundleItem(bundleItemVO);

				//刪除   --> 自訂的刪除方法
//				bundleItemSvc.deleteBundleItem(8);
				
				//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
				//System.out.println("--------------------------------");
				//repository.deleteById(7001);      
				//System.out.println("--------------------------------");



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
//		bundleItemSvc.deleteBundleItem(8);
		
		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
		//System.out.println("--------------------------------");
		//repository.deleteById(7001);      
		//System.out.println("--------------------------------");

//================================ 營地收藏 =======================================
//				CampTrackListService campTrackListSvc = context.getBean(CampTrackListService.class);		

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
//		CampSiteOrderService campSiteOrderService = context.getBean(CampSiteOrderService.class);
//		CampSiteOrderVO campSiteOrderVO = campSiteOrderService.getOneCampsiteOrder("ORD20250124001");
//		Set<CampSiteOrderDetailsVO> campsiteOrderDetails = campSiteOrderVO.getCampSiteOrderDetails();
//		for (CampSiteOrderDetailsVO detail : campsiteOrderDetails) {
//			System.out.println("營地訂單明細：" + detail.getCampsiteDetailsId() + ", 明細總價：" + detail.getCampsiteAmount() + ", content："
//					+ detail.getcampSiteOrderVO().getCommentContent());
//		}

		
				//測試取消訂單
// 				CampsiteCancellationService campsiteCancellationSvc = context.getBean(CampsiteCancellationService.class);
// 				List<CampsiteCancellationVO> campsiteCancellationList = campsiteCancellationSvc.getAllCampsiteCancellation();
// 				for (CampsiteCancellationVO campsiteCancellationVO : campsiteCancellationList) {
// 				System.out.println("營地訂單明細：" + campsiteCancellationVO.getCampsiteCancelId() + ", 明細總價：" + campsiteCancellationVO.getCampsiteCancelReason() + ", content："
// 						+ campsiteCancellationVO.getCampsiteCancelStatus());
// 			}
		
	

		context.close();
	}

}
