package com.lutu.article.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lutu.article.model.ArticlesVO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章資料傳輸物件，用於 API 回應，避免循環引用問題
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticlesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer acId; // 討論區文章編號(PK)
    private String acTitle; // 文章標題
    private Integer memId; // 會員編號
    private String memName; // 會員姓名
    private String memAcc; // 會員帳號
    private Integer acTypeId; // 文章類別編號
    private String acTypeKind; // 文章類別名稱
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acTime; // 文章發布時間
    private String acContext; // 文章內容
    private Byte acStatus; // 文章狀態
    private Long acViewCount; // 文章瀏覽次數

    public ArticlesDTO() {
    }

    public ArticlesDTO(ArticlesVO articlesVO) {
        if (articlesVO != null) {
            this.acId = articlesVO.getAcId();
            this.acTitle = articlesVO.getAcTitle();
            this.acTime = articlesVO.getAcTime();
            this.acContext = articlesVO.getAcContext();
            this.acStatus = articlesVO.getAcStatus();
            this.acViewCount = articlesVO.getAcViewCount();

            // 處理會員資訊
            if (articlesVO.getMemberVO() != null) {
                this.memId = articlesVO.getMemberVO().getMemId();
                this.memName = articlesVO.getMemberVO().getMemName();
                this.memAcc = articlesVO.getMemberVO().getMemAcc();
            }

            // 處理文章類別資訊
            if (articlesVO.getArticleTypeVO() != null) {
                this.acTypeId = articlesVO.getArticleTypeVO().getAcTypeId();
                this.acTypeKind = articlesVO.getArticleTypeVO().getAcTypeKind();
            }
        }
    }

    // Getters and Setters
    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getAcTitle() {
        return acTitle;
    }

    public void setAcTitle(String acTitle) {
        this.acTitle = acTitle;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemAcc() {
        return memAcc;
    }

    public void setMemAcc(String memAcc) {
        this.memAcc = memAcc;
    }

    public Integer getAcTypeId() {
        return acTypeId;
    }

    public void setAcTypeId(Integer acTypeId) {
        this.acTypeId = acTypeId;
    }

    public String getAcTypeKind() {
        return acTypeKind;
    }

    public void setAcTypeKind(String acTypeKind) {
        this.acTypeKind = acTypeKind;
    }

    public LocalDateTime getAcTime() {
        return acTime;
    }

    public void setAcTime(LocalDateTime acTime) {
        this.acTime = acTime;
    }

    public String getAcContext() {
        return acContext;
    }

    public void setAcContext(String acContext) {
        this.acContext = acContext;
    }

    public Byte getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(Byte acStatus) {
        this.acStatus = acStatus;
    }

    public Long getAcViewCount() {
        return acViewCount;
    }

    public void setAcViewCount(Long acViewCount) {
        this.acViewCount = acViewCount;
    }

    @Override
    public String toString() {
        return "ArticlesDTO{" +
                "acId=" + acId +
                ", acTitle='" + acTitle + '\'' +
                ", memId=" + memId +
                ", memName='" + memName + '\'' +
                ", memAcc='" + memAcc + '\'' +
                ", acTypeId=" + acTypeId +
                ", acTypeKind='" + acTypeKind + '\'' +
                ", acTime=" + acTime +
                ", acContext='"
                + (acContext != null && acContext.length() > 50 ? acContext.substring(0, 47) + "..." : acContext) + '\''
                +
                ", acStatus=" + acStatus +
                ", acViewCount=" + acViewCount +
                '}';
    }
}