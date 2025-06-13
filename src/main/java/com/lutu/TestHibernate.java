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
import com.lutu.camp_hibernate.model.CampService;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;

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
		com.lutu.camp_hibernate.model.CampVO campVO = campService.getOneCamp(1001);
		Set<CampSiteOrderVO> orders = campVO.getCampsiteOrders();
		for (CampSiteOrderVO order : orders) {
			System.out.println("訂單編號：" + order.getCampsiteOrderId() + ", 會員ID：" + order.getMemId() + ", content："
					+ order.getCampVO().getCampContent());
		}
		context.close();
	}

}
