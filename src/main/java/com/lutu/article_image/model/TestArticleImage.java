package com.lutu.article_image.model;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu") // 掃描所有 com.lutu 下的組件
//@EnableJpaRepositories(basePackages = "com.lutu") // 掃描所有 Repository
//@EntityScan(basePackages = "com.lutu") // 掃描所有實體類別
public class TestArticleImage {
	
	public static void main(String[] args) {
		// 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestHibernateArticleImage.class, args);
		SpringApplication app = new SpringApplication(TestArticleImage.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
		ConfigurableApplicationContext context = app.run(args);
		
// ===================================================================================================
// ================================ 文章圖片=======================================
		ArticleImageService articleImageSvc = context.getBean(ArticleImageService.class);
		
//		 查詢全部文章圖片
		List<ArticleImageVO> list = articleImageSvc.getAll();
		for (ArticleImageVO vo : list) {
			System.out.print(vo.getAcImgId() + ",");
			System.out.print(vo.getArticlesVO().getAcId() + ",");
			System.out.print(vo.getAcImg().length + ",");
			System.out.println();
		}
		
		// 查詢-findByPrimaryKey ArticleImageVO_getOneArticleImage
		// (多方emp2.hbm.xml必須設為lazy="false")(優!)
//		ArticleImageVO vo = articleImageSvc.getOneArticleImage(1);
//		System.out.print(vo.getAcImgId() + ",");
//		System.out.print(vo.getArticlesVO().getAcId() + ",");
//		System.out.print(vo.getAcImg().length);
		
		// 刪除 --> 自訂的刪除方法
//		articleImageSvc.deleteArticleImage(999);
		
// ===================================================================================================
		context.close();
	}
}
