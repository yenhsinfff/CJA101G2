package com.lutu.article_report.model;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu")  //掃描 table
public class TestArticleReport {
    public static void main(String[] args) {
        // 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestArticleReport.class, args);
        SpringApplication app = new SpringApplication(TestArticleReport.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
        ConfigurableApplicationContext context = app.run(args);
        
// ===================================================================================================
// ================================ 文章檢舉=======================================
        ArticleReportService articleReportSvc = context.getBean(ArticleReportService.class);
        
        // 查詢全部文章檢舉
        List<ArticleReportVO> list = articleReportSvc.getAll();
        for (ArticleReportVO vo : list) {
            System.out.print(vo.getAcReportId() + ",");
            System.out.print(vo.getMemberVO().getMemId() + ",");
            System.out.print(vo.getArticlesVO().getAcId() + ",");
            System.out.print(vo.getRpContent() + ",");
            System.out.print(vo.getRpStatus() + ",");
            System.out.println();
        }
        
        // 查詢-findByPrimaryKey ArticleReportVO_getOneArticleReport
        // (多方emp2.hbm.xml必須設為lazy="false")(優!)
//        ArticleReportVO vo = articleReportSvc.getOneArticleReport(1);
//        System.out.print(vo.getAcReportId() + ",");
//        System.out.print(vo.getMemberVO().getMemId() + ",");
//        System.out.print(vo.getArticlesVO().getAcId() + ",");
//        System.out.print(vo.getRpContent() + ",");
//        System.out.print(vo.getRpStatus());
        
        // 刪除 --> 自訂的刪除方法
//        articleReportSvc.deleteArticleReport(999);
        
// ===================================================================================================
        context.close();
    }
}
