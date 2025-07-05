package com.lutu.reply.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.article.model.ArticlesVO;
import com.lutu.member.model.MemberVO;
import com.lutu.reply_image.model.ReplyImageVO;
import com.lutu.reply_report.model.ReplyReportVO;
import com.lutu.user_discount.model.UserDiscountVO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reply")
public class ReplyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer replyId; // 留言編號(PK)
    private MemberVO memberVO; // 露營者編號(FK)
    private ArticlesVO articlesVO; // 討論區文章編號(FK)
    private LocalDateTime replyTime; // 回覆時間
    private String replyContext; // 回覆內容
    private Byte replyStatus; // 留言狀態 Not Null / 0: 顯示 / 1: 不顯示

    private Set<ReplyImageVO> replyImages; // 留言圖片
    private Set<ReplyReportVO> replyReportVO; // 討論區留言檢舉

    public ReplyVO() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
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
    @JoinColumn(name = "ac_id", nullable = false)
    @NotNull(message = "文章: 必須指定回覆的文章")
    public ArticlesVO getArticlesVO() {
        return articlesVO;
    }

    public void setArticlesVO(ArticlesVO articlesVO) {
        this.articlesVO = articlesVO;
    }

    @Column(name = "reply_time", nullable = false)
    @NotNull(message = "回覆時間: 不能為空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    @Column(name = "reply_context", nullable = false, length = 800)
    @NotEmpty(message = "回覆內容: 請勿空白")
    @Size(max = 800, message = "回覆內容: 長度不能超過{max}個字元")
    public String getReplyContext() {
        return replyContext;
    }

    public void setReplyContext(String replyContext) {
        this.replyContext = replyContext;
    }

    @Column(name = "reply_status", nullable = false)
    @NotNull(message = "回覆狀態: 不能為空")
    @Min(value = 0, message = "回覆狀態: 值必須為0或1")
    @Max(value = 1, message = "回覆狀態: 值必須為0或1")
    public Byte getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Byte replyStatus) {
        this.replyStatus = replyStatus;
    }

    @OneToMany(mappedBy = "replyVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public Set<ReplyImageVO> getReplyImages() {
        return replyImages;
    }

    public void setReplyImages(Set<ReplyImageVO> replyImages) {
        this.replyImages = replyImages;
    }

    @Override
    public String toString() {
        return "ReplyVO [replyId=" + replyId +
                ", memberVO=" + (memberVO != null ? "MemberVO@" + memberVO.hashCode() : null) +
                ", articlesVO=" + (articlesVO != null ? "ArticlesVO@" + articlesVO.hashCode() : null) +
                ", replyTime=" + replyTime +
                ", replyContext="
                + (replyContext != null && replyContext.length() > 50 ? replyContext.substring(0, 47) + "..."
                        : replyContext)
                +
                ", replyStatus=" + replyStatus + "]";
        // 重要提醒：絕對不要在 toString() 中包含延遲載入的集合
        // 例如：不要使用 replyImages, replyReportVO 等
    }

    @OneToMany(mappedBy = "replyVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public Set<ReplyReportVO> getReplyReportVO() {
        return replyReportVO;
    }

    public void setReplyReportVO(Set<ReplyReportVO> replyReportVO) {
        this.replyReportVO = replyReportVO;
    }

}
