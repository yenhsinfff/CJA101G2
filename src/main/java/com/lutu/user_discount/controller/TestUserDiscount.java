package com.lutu.user_discount.controller;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.lutu.administrator.model.AdministratorVO;
import com.lutu.camp.model.CampVO;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;
import com.lutu.camptracklist.model.CampTrackListVO;
import com.lutu.discount_code.model.DiscountCodeService;
import com.lutu.discount_code.model.DiscountCodeVO;
import com.lutu.member.model.MemberVO;
import com.lutu.owner.model.OwnerVO;
import com.lutu.user_discount.model.UserDiscountService;
import com.lutu.user_discount.model.UserDiscountVO;

@SpringBootApplication
@ComponentScan(basePackages = "com.lutu") // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
@EntityScan(basePackages = "com.lutu") // 掃描 table
public class TestUserDiscount {

	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestHibernateCampsiteOrder.class, args);
		SpringApplication app = new SpringApplication(TestUserDiscount.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		ConfigurableApplicationContext context = app.run(args);

		
		UserDiscountService svc = context.getBean(UserDiscountService.class);

		
		// ================================ 會員折價券(getall) =======================================//
//		List<UserDiscountVO> voList= svc.getByMemId(10000001);
//		for(UserDiscountVO vo:voList) {
//			System.out.println("Id:"+vo.getDiscountCodeType());
//			System.out.println("DISCOUNT_CODE:"+vo.getDiscountCodeVO().getDiscountCodeId());
//		}
		
		// ================================ 建立/分發會員折價券(產生新折價券) =======================================//
//		UserDiscountVO vo = new UserDiscountVO();
////		MemberVO memberVO = new MemberVO();
////		DiscountCodeVO discountCodeVO = new DiscountCodeVO();
////		memberVO.setMemId(10000001);
////		discountCodeVO.setDiscountCodeId("S00001");
//		UserDiscountVO.CompositeDetail id = new UserDiscountVO.CompositeDetail();
//		id.setDiscountCodeId("S00001");;
//		id.setMemId(10000001);
////		vo.setMemberVO(memberVO);
//		vo.setId(id);
//		vo.setDiscountCodeType((byte)1);
//		vo.setUsedAt(java.time.LocalDateTime.of(2025, 6, 27, 12, 0, 0));
//		
//		svc.addUserDiscount(vo);
		
		 // 1. 複合主鍵
	    UserDiscountVO.CompositeDetail id = new UserDiscountVO.CompositeDetail();
	    id.setDiscountCodeId("A00001");
	    id.setMemId(10000005);

	    // 2. 主物件
	    UserDiscountVO userDiscountVO = new UserDiscountVO();
	    userDiscountVO.setId(id);
	    userDiscountVO.setDiscountCodeType((byte) 0);
	    userDiscountVO.setUsedAt(null);
	    
	    svc.addUserDiscount(userDiscountVO);
        
		// ================================ 折價券(GetALL) =======================================//
//		List<DiscountCodeVO> discountCodeVOList = svc.getAll();
//
//		for(DiscountCodeVO vo:discountCodeVOList) {
//			System.out.println("Id:"+vo.getDiscountCodeId());
//			System.out.println("DISCOUNT_CODE:"+vo.getDiscountCode());
//		}
       

		// 測試取消訂單
//				CampsiteCancellationService campsiteCancellationSvc = context.getBean(CampsiteCancellationService.class);
//				List<CampsiteCancellationVO> campsiteCancellationList = campsiteCancellationSvc.getAllCampsiteCancellation();
//				for (CampsiteCancellationVO campsiteCancellationVO : campsiteCancellationList) {
//				System.out.println("營地訂單明細：" + campsiteCancellationVO.getCampsiteCancelId() + ", 明細總價：" + campsiteCancellationVO.getCampsiteCancelReason() + ", content："
//						+ campsiteCancellationVO.getCampsiteCancelStatus());
//			}

		context.close();
	}

	private static CampSiteOrderDetailsVO createOrderDetail(int typeId, int num, int amount, CampSiteOrderVO order) {

		CampSiteOrderDetailsVO detail = new CampSiteOrderDetailsVO();
		detail.setCampsiteTypeId(typeId);
		detail.setCampsiteNum(num);
		detail.setCampsiteAmount(amount);
		detail.setcampSiteOrderVO(order);
		return detail;
	}

}
