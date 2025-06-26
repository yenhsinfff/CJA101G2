package com.lutu.reply_image.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.lutu.reply_image.model.ReplyImageService;
import com.lutu.reply_image.model.ReplyImageVO;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu")  //掃描 table
public class TestReplyImage {
	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
		SpringApplication app = new SpringApplication(TestReplyImage.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		ConfigurableApplicationContext context = app.run(args);
		
// ===================================================================================================
// ================================ 留言圖片=======================================
		ReplyImageService replyImageSvc = context.getBean(ReplyImageService.class);
		
		// 查詢全部留言圖片
		List<ReplyImageVO> list = replyImageSvc.getAll();
		for (ReplyImageVO vo : list) {
			System.out.print(vo.getReplyImgId() + ",");
			System.out.print(vo.getReplyVO() != null ? vo.getReplyVO().getReplyId() : "null" + ",");
			System.out.print(vo.getReplyImg() != null ? "圖片長度:" + vo.getReplyImg().length + "bytes" : "null" + ",");
			System.out.println();
		}
		
		// 查詢-findByPrimaryKey 根據主鍵查詢單一留言圖片
		// (多方必須設為lazy="false")(優!)
//		ReplyImageVO vo = replyImageSvc.getOneReplyImage(1);
//		System.out.print(vo.getReplyImgId() + ",");
//		System.out.print(vo.getReplyVO() != null ? vo.getReplyVO().getReplyId() : "null" + ",");
//		System.out.print(vo.getReplyImg() != null ? "圖片長度:" + vo.getReplyImg().length + "bytes" : "null");
		
		// 刪除 --> 自訂的刪除方法
//		replyImageSvc.deleteReplyImage(999);
		
// ===================================================================================================
		context.close();
	}
}
