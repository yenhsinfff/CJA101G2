//
//package com.lutu.discount_code.controller;
//
//import java.math.BigDecimal;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//import com.lutu.administrator.model.AdministratorVO;
//import com.lutu.camp.model.CampVO;
//import com.lutu.campsite_order.model.CampSiteOrderService;
//import com.lutu.campsite_order.model.CampSiteOrderVO;
//import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;
//import com.lutu.discount_code.model.DiscountCodeService;
//import com.lutu.discount_code.model.DiscountCodeVO;
//import com.lutu.member.model.MemberVO;
//import com.lutu.owner.model.OwnerVO;
//
//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu") // 掃描你的 Service 等 component
////@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu") // 掃描 table
//public class TestDiscountCode {
//
//	public static void main(String[] args) {
//		// 啟動 Spring Boot 並取得 ApplicationContext
////        ConfigurableApplicationContext context = SpringApplication.run(TestHibernateCampsiteOrder.class, args);
//		SpringApplication app = new SpringApplication(TestDiscountCode.class);
//		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
//		ConfigurableApplicationContext context = app.run(args);
//
//		
//		DiscountCodeService svc = context.getBean(DiscountCodeService.class);
//
//		
//		// ================================ 折價券(產生新折價券Id) =======================================//
////		String newCodeString= svc.getNextDiscountCodeId("S");
////		System.out.println("newcode:"+newCodeString);
//		
//		// ================================ 折價券(產生新折價券) =======================================//
//		DiscountCodeVO a1 = new DiscountCodeVO();
//      
//        a1.setDiscountCode("帶媽媽去露營");
//        a1.setDiscountType((byte) 1);
//        a1.setDiscountValue(new BigDecimal("0.10"));
//        a1.setDiscountExplain("歡慶爸爸節8月份全館優惠活動！全平台露營商品或訂房折扣10%OFF");
//        a1.setMinOrderAmount(new BigDecimal("3000"));
//        a1.setStartDate(LocalDateTime.of(2025, 8, 1, 0, 0));
//        a1.setEndDate(LocalDateTime.of(2025, 8, 31, 0, 0));
//        a1.setCreated(LocalDateTime.of(2025, 6, 27, 11, 14, 3));
//        a1.setUpdate(null);
//        
//        OwnerVO ownerVO= new OwnerVO();
//        ownerVO.setOwnerId(null);
//        AdministratorVO administratorVO= new AdministratorVO();
//        administratorVO.setAdminId(30000002);
//        
//        
//        svc.addDiscountCode("A", a1);
//        
//		// ================================ 折價券(GetALL) =======================================//
//		List<DiscountCodeVO> discountCodeVOList = svc.getAll();
//
//		for(DiscountCodeVO vo:discountCodeVOList) {
//			System.out.println("Id:"+vo.getDiscountCodeId());
//			System.out.println("DISCOUNT_CODE:"+vo.getDiscountCode());
//		}
//       
//
//		// 測試取消訂單
////				CampsiteCancellationService campsiteCancellationSvc = context.getBean(CampsiteCancellationService.class);
////				List<CampsiteCancellationVO> campsiteCancellationList = campsiteCancellationSvc.getAllCampsiteCancellation();
////				for (CampsiteCancellationVO campsiteCancellationVO : campsiteCancellationList) {
////				System.out.println("營地訂單明細：" + campsiteCancellationVO.getCampsiteCancelId() + ", 明細總價：" + campsiteCancellationVO.getCampsiteCancelReason() + ", content："
////						+ campsiteCancellationVO.getCampsiteCancelStatus());
////			}
//
//		context.close();
//	}
//
//	private static CampSiteOrderDetailsVO createOrderDetail(int typeId, int num, int amount, CampSiteOrderVO order) {
//
//		CampSiteOrderDetailsVO detail = new CampSiteOrderDetailsVO();
//		detail.setCampsiteTypeId(typeId);
//		detail.setCampsiteNum(num);
//		detail.setCampsiteAmount(amount);
//		detail.setcampSiteOrderVO(order);
//		return detail;
//	}
//
//}
