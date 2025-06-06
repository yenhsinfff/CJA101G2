package com.lutu.campsite_order.controller;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;


@SpringBootApplication
@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
@EntityScan(basePackages = "com.lutu")  //掃描           table
public class TestHibernateCampsiteOrder {

	
	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestHibernateCampsiteOrder.class, args);
        SpringApplication app = new SpringApplication(TestHibernateCampsiteOrder.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
        ConfigurableApplicationContext context = app.run(args);

        CampSiteOrderService campsiteOrdSvc = context.getBean(CampSiteOrderService.class);

      //GetAll
      		List<CampSiteOrderVO> list = campsiteOrdSvc.getAllCampsiteOrder();
      		for (CampSiteOrderVO campSiteOrder : list) {
      			System.out.print(campSiteOrder.getCampsiteOrderId() + ",");
      			System.out.print(campSiteOrder.getCampId() + ",");
      			System.out.print(campSiteOrder.getMemId() + ",");
//      			System.out.print(emp.getHiredate() + ",");
//      			System.out.print(emp.getSal() + ",");
//      			System.out.print(emp.getComm() + ",");
//      			System.out.print(emp.getEmpdeptno());
      			System.out.println();
      		}
      		 context.close();
    }

}
