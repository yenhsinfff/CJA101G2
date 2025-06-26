package com.lutu.reply_report.model;

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
public class TestReplyReport {

    public static void main(String[] args) {
        // 啟動 Spring Boot 並取得 ApplicationContext
        SpringApplication app = new SpringApplication(TestReplyReport.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 🟢 禁用 Web 模式
        ConfigurableApplicationContext context = app.run(args);
        
// ===================================================================================================
// ================================ 留言檢舉明細=======================================
        ReplyReportService replyReportSvc = context.getBean(ReplyReportService.class);
        
        // 查詢全部
        List<ReplyReportVO> list = replyReportSvc.getAll();
        for (ReplyReportVO vo : list) {
            System.out.print(vo.getReplyReportId() + ",");
            System.out.print(vo.getMemberVO().getMemId() + ",");
            System.out.print(vo.getAcId() + ",");
            System.out.print(vo.getReplyVO().getReplyId() + ",");
            System.out.print(vo.getAdministratorVO().getAdminId() + ",");
            System.out.print(vo.getRpTime() + ",");
            System.out.print(vo.getRpContent() + ",");
            System.out.print(vo.getAdminReply() + ",");
            System.out.print(vo.getRpDoneTime() + ",");
            System.out.print(vo.getRpStatus() + ",");
            System.out.print(vo.getRpSresult() + ",");
            System.out.print(vo.getRpNote());
            System.out.println();
        }
        
        // 查詢-findByPrimaryKey (優!)
//        ReplyReportVO vo = replyReportSvc.getOneReplyReport(1);
//        System.out.print(vo.getReplyReportId() + ",");
//        System.out.print(vo.getMemberVO().getMemId() + ",");
//        System.out.print(vo.getAcId() + ",");
//        System.out.print(vo.getReplyVO().getReplyId() + ",");
//        System.out.print(vo.getAdministratorVO().getAdminId() + ",");
//        System.out.print(vo.getRpTime() + ",");
//        System.out.print(vo.getRpContent() + ",");
//        System.out.print(vo.getAdminReply() + ",");
//        System.out.print(vo.getRpDoneTime() + ",");
//        System.out.print(vo.getRpStatus() + ",");
//        System.out.print(vo.getRpSresult() + ",");
//        System.out.print(vo.getRpNote());
        
        // 刪除 --> 自訂的刪除方法
//        replyReportSvc.deleteReplyReport(1);
        
// ===================================================================================================
        context.close();
    }
}
