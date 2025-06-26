package com.lutu.reply.model;

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
public class TestReply {

    public static void main(String[] args) {
        // 啟動 Spring Boot 並取得 ApplicationContext
        SpringApplication app = new SpringApplication(TestReply.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
        ConfigurableApplicationContext context = app.run(args);
        
// ===================================================================================================
// ================================ 留言明細=======================================
        ReplyService replySvc = context.getBean(ReplyService.class);
        
        // 查詢全部
        List<ReplyVO> list = replySvc.getAll();
        for (ReplyVO vo : list) {
            System.out.print(vo.getReplyId() + ",");
            System.out.print(vo.getMemberVO().getMemId() + ",");
            System.out.print(vo.getArticlesVO().getAcId() + ",");
            System.out.print(vo.getReplyTime() + ",");
            System.out.print(vo.getReplyContext() + ",");
            System.out.print(vo.getReplyStatus() + ",");
            System.out.println();
        }
        
        // 查詢-findByPrimaryKey (優!)
//        ReplyVO vo = replySvc.getOneReply(1);
//        System.out.print(vo.getReplyId() + ",");
//        System.out.print(vo.getMemberVO().getMemId() + ",");
//        System.out.print(vo.getArticlesVO().getAcId() + ",");
//        System.out.print(vo.getReplyTime() + ",");
//        System.out.print(vo.getReplyContext() + ",");
//        System.out.print(vo.getReplyStatus());
        
        // 刪除 --> 自訂的刪除方法
//        replySvc.deleteReply(1);
        
// ===================================================================================================
        context.close();
    }
}
