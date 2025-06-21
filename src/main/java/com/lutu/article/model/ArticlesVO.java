package com.lutu.article.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.ac_fav_record.model.AcFavRecordVO;
import com.lutu.article_image.model.ArticleImageVO;
import com.lutu.article_report.model.ArticleReportVO;
import com.lutu.article_type.model.ArticleTypeVO;
import com.lutu.member.model.MemberVO;
import com.lutu.nice_article.model.NiceArticleVO;
import com.lutu.reply.model.ReplyVO;
import com.lutu.user_discount.model.UserDiscountVO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "articles")
public class ArticlesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer acId;								// 討論區文章編號(PK)
    private String acTitle;								// 文章標題
    private MemberVO memberVO; 							// 露營者編號(FK)
    private ArticleTypeVO articleTypeVO;				// 文章類別編號(FK)
    private LocalDateTime acTime;						// 文章發布時間
    private String acContext;							// 文章內容
    private Byte acStatus;							// 文章狀態 Not Null  0 : 顯示  1 : 不顯示
   
    private Set<ArticleImageVO> articleImages;			// 討論區圖片
    private Set<ReplyVO> replies;						// 留言
    private Set<NiceArticleVO> niceArticleVO;			// 這個文章★讚
    private Set<ArticleReportVO> articleReport;			// 討論區文章檢舉	
    private Set<AcFavRecordVO> acFavRecord;				// 文章收藏紀錄
    
    public ArticlesVO() {
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_id")
    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    @Column(name = "ac_title", nullable = false, length = 30)
    @NotEmpty(message = "文章標題: 請勿空白")
    @Size(max = 30, message = "文章標題: 長度不能超過{max}個字元")
    public String getAcTitle() {
        return acTitle;
    }

    public void setAcTitle(String acTitle) {
        this.acTitle = acTitle;
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
    @JoinColumn(name = "ac_type_id", nullable = false)
    @NotNull(message = "文章類別: 請選擇文章類別")
    public ArticleTypeVO getArticleTypeVO() {
        return articleTypeVO;
    }

    public void setArticleTypeVO(ArticleTypeVO articleTypeVO) {
        this.articleTypeVO = articleTypeVO;
    }

    @Column(name = "ac_time", nullable = false)
    @NotNull(message = "發表時間: 不能為空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getAcTime() {
        return acTime;
    }

    public void setAcTime(LocalDateTime acTime) {
        this.acTime = acTime;
    }

    @Column(name = "ac_context", nullable = false, length = 800)
    @NotEmpty(message = "文章內容: 請勿空白")
    @Size(max = 800, message = "文章內容: 長度不能超過{max}個字元")
    public String getAcContext() {
        return acContext;
    }

    public void setAcContext(String acContext) {
        this.acContext = acContext;
    }

    @Column(name = "ac_status", nullable = false)
    @NotNull(message = "文章狀態: 不能為空")
    @Min(value = 0, message = "文章狀態: 值必須為0或1")
    @Max(value = 1, message = "文章狀態: 值必須為0或1")
    public Byte getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(Byte acStatus) {
        this.acStatus = acStatus;
    }

    @OneToMany(mappedBy = "articlesVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ArticleImageVO> getArticleImages() {
        return articleImages;
    }

    public void setArticleImages(Set<ArticleImageVO> articleImages) {
        this.articleImages = articleImages;
    }

    @OneToMany(mappedBy = "articleVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ReplyVO> getReplies() {
        return replies;
    }

    public void setReplies(Set<ReplyVO> replies) {
        this.replies = replies;
    }
    
    @OneToMany(mappedBy = "articlesVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<NiceArticleVO> getNiceArticle() {
        return niceArticleVO;
    }

    public void setNiceArticle(Set<NiceArticleVO> niceArticleVO) {
        this.niceArticleVO = niceArticleVO;
    }
    
    @OneToMany(mappedBy = "articlesVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ArticleReportVO> getArticleReport() {
        return articleReport;
    }

    public void setArticleReport(Set<ArticleReportVO> articleReport) {
        this.articleReport = articleReport;
    }
    
    @OneToMany(mappedBy = "articlesVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<AcFavRecordVO> getAcFavRecord() {
        return acFavRecord;
    }

    public void setAcFavRecord(Set<AcFavRecordVO> acFavRecord) {
        this.acFavRecord = acFavRecord;
    }
}

