package com.lutu.campsite_order.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
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

import com.lutu.bundleitemdetails.model.BundleItemDetailsVO;
import com.lutu.camp.model.CampVO;
import com.lutu.campsite_available.model.CampsiteAvailableService;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;
import com.lutu.campsitetype.model.CampsiteTypeVO;
import com.lutu.member.model.MemberVO;

@SpringBootApplication
@ComponentScan(basePackages = "com.lutu") // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
@EntityScan(basePackages = "com.lutu") // 掃描 table
public class TestCampsiteOrder {

	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestHibernateCampsiteOrder.class, args);
		SpringApplication app = new SpringApplication(TestCampsiteOrder.class);
		//app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		//ConfigurableApplicationContext context = app.run(args);
		ConfigurableApplicationContext context = SpringApplication.run(TestCampsiteOrder.class, args);

		// ================================ 建立剩餘房型
		// =======================================
//		CampsiteAvailableService svc = context.getBean(CampsiteAvailableService.class);
//		List<Integer> campIds = Arrays.asList(1001, 1002, 1003);
//		List<CampsiteTypeVO> campsiteTypeVOList = svc.ensureAndQuery(campIds,3, Date.valueOf("2025-08-02"), Date.valueOf("2025-08-05"));
//		System.out.println("開啟");
//		for(CampsiteTypeVO vo:campsiteTypeVOList) {
//			System.out.println(vo.getCampsiteName());
//			System.out.println(vo.getCampsiteNum());
//			System.out.println("=================");
//		}

		// ================================
		// 剩餘房型增加減少=======================================
//		CampsiteAvailableService svc = context.getBean(CampsiteAvailableService.class);
//		Boolean response = svc.deductRoomsByDateRange(Date.valueOf("2025-08-02"),Date.valueOf("2025-08-04"), 2014);
//		Boolean response = svc.refundRoomsByDateRange(Date.valueOf("2025-08-02"),Date.valueOf("2025-08-04"), 2014);
//		System.out.println("response"+response);

		// ================================ 營地訂單 =======================================
		CampSiteOrderService svc = context.getBean(CampSiteOrderService.class);
		String orderIdString = svc.generateCampsiteOrderId();
		System.out.println("orderIdString:" + orderIdString);

		// 新增訂單
		CampSiteOrderVO order = new CampSiteOrderVO();

		// 基本訂單資訊
		order.setCampsiteOrderId("ORD20250705008");
		order.setOrderDate(Timestamp.valueOf("2025-06-24 14:30:00"));
		order.setCampsiteOrderStatus((byte) 3); // 訂單狀態: 0=露營者為付款 3=訂單結案
		order.setPayMethod((byte) 2); // 支付方式: 1=信用卡

		// 金額相關
		order.setBundleAmount(1200); // 加購項目總金額
		order.setCampsiteAmount(8600); // 營地總金額
		order.setBefAmount(9800); // 折價前總金額
		order.setDisAmount(200); // 折價金額
		order.setAftAmount(9600); // 實付金額

		// 日期相關
		order.setCheckIn(Date.valueOf("2025-07-15"));
		order.setCheckOut(Date.valueOf("2025-07-17"));

		// 評價資訊（可選）
		order.setCommentSatisfaction(5);
		order.setCommentContent("營地設施完善，服務周到");
		order.setCommentDate(Timestamp.valueOf("2025-07-20 10:00:00"));
		order.setCampId(1001);
		order.setMemId(10000001);

		// 折價券資訊
		order.setDiscountCodeId("C00001"); // 對應 discount_code 表

		// 訂單明細
		Set<CampSiteOrderDetailsVO> details = new HashSet<>();
		details.add(createOrderDetail(2001, 2, 10400, order)); // 小木屋四人房 x2
		details.add(createOrderDetail(2002, 1, 3400, order)); // 小木屋雙人房 x1
		order.setCampSiteOrderDetails(details);

		// 訂單明細
		Set<BundleItemDetailsVO> bDetails = new HashSet<>();
		BundleItemDetailsVO vo = new BundleItemDetailsVO();
		vo.setCampsiteOrderId("ORD20250624001");
		vo.setBundleId(1);
		vo.setBundleBuyNum(2);
		vo.setBundleBuyAmount(7000);
		bDetails.add(vo);
		order.setBundleitemDetails(bDetails);

		svc.createOneCampOrder(order);
		System.out.println("FINISH");

		// ================================ 取消營地訂單
		// =======================================
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
