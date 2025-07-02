package com.lutu.reply.controller;

import com.lutu.reply.model.ReplyVO;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ReplyDTO {
    private Integer replyId;

    @NotNull(message = "會員ID: 不能為空")
    private Integer memId;

    // 會員姓名，用於前端顯示
    private String memName;

    @NotNull(message = "文章ID: 不能為空")
    private Integer acId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replyTime;

    @NotEmpty(message = "回覆內容: 請勿空白")
    @Size(max = 800, message = "回覆內容: 長度不能超過{max}個字元")
    private String replyContext;

    @Min(value = 0, message = "回覆狀態: 值必須為0或1")
    @Max(value = 1, message = "回覆狀態: 值必須為0或1")
    private Byte replyStatus;

    // 預設建構子
    public ReplyDTO() {
    }

    // 從 VO 建立 DTO
    public ReplyDTO(ReplyVO replyVO) {
        this.replyId = replyVO.getReplyId();
        this.memId = replyVO.getMemberVO() != null ? replyVO.getMemberVO().getMemId() : null;
        this.memName = replyVO.getMemberVO() != null ? replyVO.getMemberVO().getMemName() : null;
        this.acId = replyVO.getArticlesVO() != null ? replyVO.getArticlesVO().getAcId() : null;
        this.replyTime = replyVO.getReplyTime();
        this.replyContext = replyVO.getReplyContext();
        this.replyStatus = replyVO.getReplyStatus();
    }

    // Getter 和 Setter
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyContext() {
        return replyContext;
    }

    public void setReplyContext(String replyContext) {
        this.replyContext = replyContext;
    }

    public Byte getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Byte replyStatus) {
        this.replyStatus = replyStatus;
    }

    @Override
    public String toString() {
        return "ReplyDTO [replyId=" + replyId + ", memId=" + memId + ", memName=" + memName + ", acId=" + acId
                + ", replyTime=" + replyTime
                + ", replyContext=" + replyContext + ", replyStatus=" + replyStatus + "]";
    }
}
