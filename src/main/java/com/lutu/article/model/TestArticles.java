package com.lutu.article.model;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.lutu.article.model.ArticlesService;
import com.lutu.article.model.ArticlesVO;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu")  //掃描 table
public class TestArticles {
	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
		SpringApplication app = new SpringApplication(TestArticles.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		ConfigurableApplicationContext context = app.run(args);
		
// ===================================================================================================
// ================================ 討論區文章=======================================
		ArticlesService articlesSvc = context.getBean(ArticlesService.class);
		
		// 查詢全部文章
		List<ArticlesVO> list = articlesSvc.getAll();
		for (ArticlesVO vo : list) {
			System.out.print(vo.getAcId() + ",");
			System.out.print(vo.getAcTitle() + ",");
			System.out.print(vo.getMemberVO() != null ? vo.getMemberVO().getMemId() : "null" + ",");
			System.out.print(vo.getArticleTypeVO() != null ? vo.getArticleTypeVO().getAcTypeId() : "null" + ",");
			System.out.print(vo.getAcTime() + ",");
			System.out.print(vo.getAcContext() != null && vo.getAcContext().length() > 20 ? 
					vo.getAcContext().substring(0, 20) + "..." : vo.getAcContext() + ",");
			System.out.print(vo.getAcStatus());
			System.out.println();
		}
		
		// 查詢-findByPrimaryKey 根據主鍵查詢單一文章
		// (多方必須設為lazy="false")(優!)
//		ArticlesVO vo = articlesSvc.getOneArticles(1);
//		System.out.print(vo.getAcId() + ",");
//		System.out.print(vo.getAcTitle() + ",");
//		System.out.print(vo.getMemberVO() != null ? vo.getMemberVO().getMemId() : "null" + ",");
//		System.out.print(vo.getArticleTypeVO() != null ? vo.getArticleTypeVO().getAcTypeId() : "null" + ",");
//		System.out.print(vo.getAcTime() + ",");
//		System.out.print(vo.getAcContext() != null && vo.getAcContext().length() > 20 ? 
//				vo.getAcContext().substring(0, 20) + "..." : vo.getAcContext() + ",");
//		System.out.print(vo.getAcStatus());
		
		// 刪除 --> 自訂的刪除方法
//		articlesSvc.deleteArticles(999);
		
// ===================================================================================================
		context.close();
	}
}
