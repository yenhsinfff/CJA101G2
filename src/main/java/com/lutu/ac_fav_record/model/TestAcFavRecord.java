
package com.lutu.ac_fav_record.model;

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
import com.lutu.article.model.ArticlesService;
import com.lutu.article.model.ArticlesVO;
import com.lutu.bundleitem.model.BundleItemVO;
import com.lutu.campsite.model.CampsiteService;
import com.lutu.ac_fav_record.model.AcFavRecordService;
import com.lutu.ac_fav_record.model.AcFavRecordVO;
import com.lutu.ac_fav_record.model.AcFavRecordVO.AcFavRecordId;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.lutu")  // 掃描所有 com.lutu 包下的 Service 等 component
//@EnableJpaRepositories(basePackages = "com.lutu")  // 掃描所有 com.lutu 包下的 Repository
//@EntityScan(basePackages = "com.lutu")  // 掃描所有 com.lutu 包下的 Entity
public class TestAcFavRecord {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestAcFavRecord.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 禁用 Web 模式
        ConfigurableApplicationContext context = app.run(args);
        
        // ===================================================================================================
        // ================================ 收藏記錄明細 =======================================
        AcFavRecordService acFavRecordSvc = context.getBean(AcFavRecordService.class);
        
        try {
            // 查詢所有收藏記錄
            List<AcFavRecordVO> list = acFavRecordSvc.getAll();
            System.out.println("=== 所有收藏記錄 ===");
            for (AcFavRecordVO vo : list) {
                System.out.print("文章ID: " + vo.getAcId() + ", ");
                System.out.print("會員ID: " + vo.getMemId() + ", ");
                System.out.print("收藏時間: " + vo.getAcFavTime());
                System.out.println();
            }
            
            // 其他測試代碼...
            // 查詢單一記錄 - 使用複合主鍵
            /*
            AcFavRecordId compositeKey = new AcFavRecordId(1001, 2001);
            AcFavRecordVO vo = acFavRecordSvc.getOneAcFavRecord(compositeKey);
            if (vo != null) {
                System.out.println("=== 單一記錄查詢 ===");
                System.out.print("文章ID: " + vo.getAcId() + ", ");
                System.out.print("會員ID: " + vo.getMemId() + ", ");
                System.out.print("收藏時間: " + vo.getAcFavTime());
                System.out.println();
            }
            */
            
            // 根據會員ID查詢收藏記錄
            /*
            List<AcFavRecordVO> memFavList = acFavRecordSvc.getByMemId(2001);
            System.out.println("=== 會員 2001 的收藏記錄 ===");
            for (AcFavRecordVO vo : memFavList) {
                System.out.print("文章ID: " + vo.getAcId() + ", ");
                System.out.print("會員ID: " + vo.getMemId() + ", ");
                System.out.print("收藏時間: " + vo.getAcFavTime());
                System.out.println();
            }
            */
            
            // 檢查是否已收藏
            /*
            boolean isFav = acFavRecordSvc.isFavorited(1001, 2001);
            System.out.println("文章 1001 是否被會員 2001 收藏: " + isFav);
            */
            
        } catch (Exception e) {
            System.err.println("執行過程中發生錯誤: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }
}