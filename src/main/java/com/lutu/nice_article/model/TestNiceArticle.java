package com.lutu.nice_article.model;

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
import com.lutu.nice_article.model.NiceArticleService;
import com.lutu.nice_article.model.NiceArticleVO;
import com.lutu.nice_article.model.NiceArticleId;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu")  //掃描 table
public class TestNiceArticle {
	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
		SpringApplication app = new SpringApplication(TestNiceArticle.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		ConfigurableApplicationContext context = app.run(args);
		
// ===================================================================================================
// ================================ 文章按讚記錄=======================================
		NiceArticleService niceArticleSvc = context.getBean(NiceArticleService.class);
		
		// 查詢全部按讚記錄
		List<NiceArticleVO> list = niceArticleSvc.getAll();
		for (NiceArticleVO vo : list) {
			System.out.print(vo.getAcId() + ",");
			System.out.print(vo.getMemId() + ",");
			System.out.print(vo.getLikeTime() + ",");
			System.out.println();
		}
		
		// 查詢-findByPrimaryKey 根據複合主鍵查詢單一按讚記錄
		// (多方必須設為lazy="false")(優!)
//		NiceArticleId compositeKey = new NiceArticleId(1, 1001);
//		NiceArticleVO vo = niceArticleSvc.getOneNiceArticle(compositeKey);
//		System.out.print(vo.getAcId() + ",");
//		System.out.print(vo.getMemId() + ",");
//		System.out.print(vo.getLikeTime());
		
		// 查詢-使用 acId 和 memId 查詢單一按讚記錄
//		NiceArticleVO vo2 = niceArticleSvc.getOneNiceArticle(1, 1001);
//		System.out.print(vo2.getAcId() + ",");
//		System.out.print(vo2.getMemId() + ",");
//		System.out.print(vo2.getLikeTime());
		
		// 刪除 --> 自訂的刪除方法
//		NiceArticleId deleteKey = new NiceArticleId(999, 9999);
//		niceArticleSvc.deleteNiceArticle(deleteKey);
		
		// 刪除 --> 使用 acId 和 memId 的刪除方法
//		niceArticleSvc.deleteNiceArticle(999, 9999);
		
// ===================================================================================================
		context.close();
	}
}
