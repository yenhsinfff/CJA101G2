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
@ComponentScan(basePackages = "com.lutu") // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu") // 掃描 Repository
@EntityScan(basePackages = "com.lutu") // 掃描 table
public class TestArticles {
	public static void main(String[] args) {
		System.out.println("開始啟動 Spring Boot 應用程式...");

			SpringApplication app = new SpringApplication(TestArticles.class);
			app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
			ConfigurableApplicationContext context = app.run(args);

			System.out.println("✅ Spring Boot 應用程式啟動成功！");

			// ===================================================================================================
			// ================================ 討論區文章=======================================
			ArticlesService articlesSvc = context.getBean(ArticlesService.class);
			System.out.println("ArticlesService 注入成功！");

			// 查詢全部文章
			System.out.println("正在查詢所有文章...");
			List<ArticlesVO> list = articlesSvc.getAll();
			System.out.println("查詢到 " + list.size() + " 篇文章");

			// 查詢單一文章
			System.out.println("正在查詢文章");
			ArticlesVO vo = articlesSvc.getOneArticles(30000004);
			if (vo != null) {
				System.out.println("成功查詢到文章: " + vo.getAcTitle());
			} else {
				System.out.println("文章 ID=1 不存在");
			}

			// 測試刪除文章功能
//			System.out.println("正在測試刪除文章 ");
//			try {
//				articlesSvc.deleteArticles(30000002);
//				System.out.println("文章刪除成功！");
//			} catch (Exception e) {
//				System.out.println("刪除文章時發生錯誤: " + e.getMessage());
//				e.printStackTrace();
//			}
			
			// 測試新增文章功能
			System.out.println("正在測試新增文章功能...");
			try {
				ArticlesVO VO = new ArticlesVO();
				VO.setAcTitle("星冰樂一定要配溫開水");
				VO.setAcContext("大家不知道，星冰樂不是涼涼的就有效");

				// 建立 MemberVO 物件
				com.lutu.member.model.MemberVO memberVO = new com.lutu.member.model.MemberVO();

				memberVO.setMemId(10000007);
				VO.setMemberVO(memberVO);

				// 建立 ArticleTypeVO 物件
				com.lutu.article_type.model.ArticleTypeVO articleTypeVO = new com.lutu.article_type.model.ArticleTypeVO();
				articleTypeVO.setAcTypeId(30001);
				VO.setArticleTypeVO(articleTypeVO);

				VO.setAcTime(java.time.LocalDateTime.parse("2024-02-21T17:44:11"));
				VO.setAcStatus((byte) 0);

				// 設定所有關聯集合（初始化為空集合）
				VO.setArticleImages(java.util.Collections.emptySet());
				VO.setReplies(java.util.Collections.emptySet());
				VO.setNiceArticle(java.util.Collections.emptySet());
				VO.setArticleReport(java.util.Collections.emptySet());
				VO.setAcFavRecord(java.util.Collections.emptySet());

				articlesSvc.addArticles(VO);
				System.out.println("文章新增成功！");
				System.out.println("新增的文章標題: " + VO.getAcTitle());
				System.out.println("新增的文章ID: " + VO.getAcId());
			} catch (Exception e) {
				System.out.println("新增文章時發生錯誤: " + e.getMessage());
				e.printStackTrace();
			}

			// ===================================================================================================
			context.close();
			System.out.println("測試完成！");

		}
	}
