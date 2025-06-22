package com.lutu.reply_report.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.administrator.model.AdministratorVO;
import com.lutu.article.model.ArticlesVO;
import com.lutu.member.model.MemberVO;
import com.lutu.reply.model.ReplyVO;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reply_report")
public class ReplyReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer replyReportId;		 		// 留言案件編號 (PK)
    private MemberVO memberVO;				 	// 露營者編號(FK)
    private Integer acId;			         	// 文章編號(FK)
    private ReplyVO replyVO;					// 留言編號(FK)
    private AdministratorVO administratorVO;    // 管理員編號(FK)	
    private LocalDateTime rpTime;		 		// 檢舉時間
    private String rpContent;					// 檢舉文字內容
    private String adminReply;			        // 管理員回覆
    private LocalDateTime rpDoneTime;	        // 處理完成時間
    private Byte rpStatus;				 		// 處理狀態
    private Byte rpSresult;				 		// 處理結果
    private String rpNote; 						// 處理註記

    public ReplyReportVO() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_report_id")
    public Integer getReplyReportId() {
        return replyReportId;
    }

    public void setReplyReportId(Integer replyReportId) {
        this.replyReportId = replyReportId;
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
    

    @Column(name = "ac_id", nullable = false)
    @NotNull(message = "文章ID: 不能為空")
    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "replyId", nullable = false)
    @NotNull(message = "被檢舉留言ID: 不能為空")
    public ReplyVO getReplyVO() {
        return replyVO;
    }

    public void setReplyVO(ReplyVO replyVO) {
        this.replyVO = replyVO;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId", nullable = false)
    @NotNull(message = "管理員ID: 不能為空")
    public AdministratorVO getAdministratorVO() {
        return administratorVO;
    }

    public void setAdministratorVO(AdministratorVO administratorVO) {
        this.administratorVO = administratorVO;
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

    @Column(name = "admin_reply", length = 800)
    @Size(max = 800, message = "管理員回覆: 長度不能超過{max}個字元")
    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
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

    @Column(name = "rp_sresult", nullable = false)
    @NotNull(message = "處理結果: 不能為空")
    @Min(value = 0, message = "處理結果: 值必須為0或1")
    @Max(value = 1, message = "處理結果: 值必須為0或1")
    public Byte getRpSresult() {
        return rpSresult;
    }

    public void setRpSresult(Byte rpSresult) {
        this.rpSresult = rpSresult;
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
