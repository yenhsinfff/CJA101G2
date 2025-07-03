package com.lutu.nice_article.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.lutu.article.model.ArticlesVO;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "nice_article")
@IdClass(NiceArticleId.class)
public class NiceArticleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer acId; // 討論區文章編號 (PK, FK)
    private Integer memId; // 露營者編號 (PK, FK)
    private LocalDateTime likeTime; // 按讚時間

    private ArticlesVO articlesVO;

    public NiceArticleVO() {

    }

    @Id
    @Column(name = "ac_id")
    @NotNull(message = "文章ID: 不能為空")
    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    @Id
    @Column(name = "mem_id")
    @NotNull(message = "會員ID: 不能為空")
    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    @Column(name = "like_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(LocalDateTime likeTime) {
        this.likeTime = likeTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ac_id", insertable = false, updatable = false)
    @JsonIgnore
    public ArticlesVO getArticlesVO() {
        return articlesVO;
    }

    public void setArticlesVO(ArticlesVO articlesVO) {
        this.articlesVO = articlesVO;
    }
}
