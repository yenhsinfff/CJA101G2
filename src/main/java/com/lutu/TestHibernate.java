package com.lutu;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.lutu.camp.model.CampVO;
import com.lutu.camp.model.CampService;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.camptracklist.model.CampTrackListService;
import com.lutu.camptracklist.model.CampTrackListVO;

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
				//(多方emp2.hbm.xml必須設為lazy="false")(優!)
//				CampTrackListVO campTrackListVO = campTrackListSvc.getOneCampTrackList(1001, 10000001);
//				System.out.print(campTrackListVO.getCompositeKey().getCampId()+ ",");
//				System.out.print(campTrackListVO.getCompositeKey().getMemId() + ",");
//				System.out.print(campTrackListVO.getMemTrackDate());
				
				
				//新增
				CampTrackListVO campTrackListVO = new CampTrackListVO();
//				CampTrackListVO.CompositeDetail id = new CampTrackListVO.CompositeDetail();
//				id.setCampId(1005);
//				id.setMemId(10000001);
//				campTrackListVO.setCompositeKey(id);
//				String dateStr = "2025-05-30";
//				java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
//				campTrackListVO.setMemTrackDate(sqlDate);
//				campTrackListSvc.addCampTrackList(campTrackListVO);
				
				// 刪除CampTrackListVO_deleteCampTrackList
				CampTrackListVO.CompositeDetail id2 = new CampTrackListVO.CompositeDetail();
				id2.setCampId(1005);
				id2.setMemId(10000001);
				campTrackListSvc.deleteCampTrackList(id2.getCampId(), id2.getMemId());
				
				//=========================================================================	
		
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
		CampService campService = context.getBean(CampService.class);
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
		CampVO campVO = campService.getOneCamp(1001);
		Set<CampSiteOrderVO> orders = campVO.getCampsiteOrders();
		for (CampSiteOrderVO order : orders) {
			System.out.println("訂單編號：" + order.getCampsiteOrderId() + ", 會員ID：" + order.getMemId() + ", content："
					+ order.getCampVO().getCampContent());
		}
		
		//透過訂單查詢
		
		//
		context.close();
	}

}
