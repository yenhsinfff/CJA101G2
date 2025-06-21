package com.lutu.article_report.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.article.model.ArticlesVO;
import com.lutu.member.model.MemberVO;
import com.lutu.user_discount.model.UserDiscountVO;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "article_report")
public class ArticleReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer acReportId;					// 文章案件編號 (PK)
    private MemberVO memberVO;			 		// 露營者編號(FK)
    private ArticlesVO articlesVO;				// 文章編號(FK)
    private LocalDateTime rpTime;				// 檢舉時間
    private String rpContent;					// 檢舉文字內容
    private Integer adminId;					// 管理員編號(FK)
    private String adminMemCustomer;			// 管理員回覆
    private LocalDateTime rpDoneTime;			// 處理完成時間
    private Byte rpStatus;					    // 處理狀態   Not Null   0: 未處理	1: 已處理
    private Byte rpResult;					    // 處理結果   Not Null   0: 通過	    1: 未通過
    private String rpNote;						// 處理註記

    public ArticleReportVO() {
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_report_id")
    public Integer getAcReportId() {
        return acReportId;
    }

    public void setAcReportId(Integer acReportId) {
        this.acReportId = acReportId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_id", nullable = false)
    @NotNull(message = "會員ID: 不能為空")
    public MemberVO getMemberVO() {
        return memberVO;
    }

    public void setMemberVO(MemberVO memberVO) {
        this.memberVO = memberVO;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acId", nullable = false)
    @NotNull(message = "被檢舉文章ID: 不能為空")
    public ArticlesVO getArticlesVO() {
        return articlesVO;
    }

    public void setArticlesVO(ArticlesVO articlesVO) {
        this.articlesVO = articlesVO;
    }

    @Column(name = "rp_time", nullable = false)
    @NotNull(message = "檢舉時間: 不能為空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getRpTime() {
        return rpTime;
    }

    public void setRpTime(LocalDateTime rpTime) {
        this.rpTime = rpTime;
    }

    @Column(name = "rp_content", nullable = false, length = 800)
    @NotEmpty(message = "檢舉內容: 請勿空白")
    @Size(max = 800, message = "檢舉內容: 長度不能超過{max}個字元")
    public String getRpContent() {
        return rpContent;
    }

    public void setRpContent(String rpContent) {
        this.rpContent = rpContent;
    }

    @Column(name = "admin_id", nullable = false)
    @NotNull(message = "處理管理員ID: 不能為空")
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Column(name = "admin_mem_customer", length = 800)
    @Size(max = 800, message = "管理員回覆: 長度不能超過{max}個字元")
    public String getAdminMemCustomer() {
        return adminMemCustomer;
    }

    public void setAdminMemCustomer(String adminMemCustomer) {
        this.adminMemCustomer = adminMemCustomer;
    }

    @Column(name = "rp_done_time", nullable = false)
    @NotNull(message = "處理完成時間: 不能為空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getRpDoneTime() {
        return rpDoneTime;
    }

    public void setRpDoneTime(LocalDateTime rpDoneTime) {
        this.rpDoneTime = rpDoneTime;
    }

    @Column(name = "rp_status", nullable = false)
    @NotNull(message = "處理狀態: 不能為空")
    @Min(value = 0, message = "處理狀態: 值必須為0或1")
    @Max(value = 1, message = "處理狀態: 值必須為0或1")
    public Byte getRpStatus() {
        return rpStatus;
    }

    public void setRpStatus(Byte rpStatus) {
        this.rpStatus = rpStatus;
    }

    @Column(name = "rp_result", nullable = false)
    @NotNull(message = "處理結果: 不能為空")
    @Min(value = 0, message = "處理結果: 值必須為0或1")
    @Max(value = 1, message = "處理結果: 值必須為0或1")
    public Byte getRpResult() {
        return rpResult;
    }

    public void setRpResult(Byte rpResult) {
        this.rpResult = rpResult;
    }

    @Column(name = "rp_note", length = 800)
    @Size(max = 800, message = "備註: 長度不能超過{max}個字元")
    public String getRpNote() {
        return rpNote;
    }

    public void setRpNote(String rpNote) {
        this.rpNote = rpNote;
    }
}
