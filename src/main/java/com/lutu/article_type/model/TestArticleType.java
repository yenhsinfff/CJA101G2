package com.lutu.article_type.model;

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

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描你的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描 Repository
//@EntityScan(basePackages = "com.lutu")  //掃描 table
public class TestArticleType {
    public static void main(String[] args) {
        // 啟動 Spring Boot 並取得 ApplicationContext
//        ConfigurableApplicationContext context = SpringApplication.run(TestArticleType.class, args);
        SpringApplication app = new SpringApplication(TestArticleType.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
        ConfigurableApplicationContext context = app.run(args);
        
// ===================================================================================================
// ================================ 文章類別=======================================
        ArticleTypeService articleTypeSvc = context.getBean(ArticleTypeService.class);
        
        // 查詢全部文章類別
        List<ArticleTypeVO> list = articleTypeSvc.getAll();
        for (ArticleTypeVO vo : list) {
            System.out.print(vo.getAcTypeId() + ",");
            System.out.print(vo.getAcTypeKind() + ",");
            System.out.print(vo.getAcTypeText() + ",");
            System.out.println();
        }
        
        // 查詢-findByPrimaryKey ArticleTypeVO_getOneArticleType
        // (多方emp2.hbm.xml必須設為lazy="false")(優!)
//        ArticleTypeVO vo = articleTypeSvc.getOneArticleType(1);
//        System.out.print(vo.getAcTypeId() + ",");
//        System.out.print(vo.getAcTypeKind() + ",");
//        System.out.print(vo.getAcTypeText());
        
        // 刪除 --> 自訂的刪除方法
//        articleTypeSvc.deleteArticleType(999);
        
// ===================================================================================================
        context.close();
    }
}