package com.lutu.article_image.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;

import com.lutu.article.model.ArticlesVO;

@Entity
@Table(name = "article_image")
public class ArticleImageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer acImgId;			// 討論區圖片編號(PK)
    private ArticlesVO articlesVO;		// 討論區文章編號(FK) 
    private byte[] acImg;				// 圖片訊息

    public ArticleImageVO() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_img_id")
    public Integer getAcImgId() {
        return acImgId;
    }

    public void setAcImgId(Integer acImgId) {
        this.acImgId = acImgId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ac_id", nullable = false)
    @NotNull(message = "文章: 必須指定所屬文章")
    public ArticlesVO getArticlesVO() {
        return articlesVO;
    }

    public void setArticlesVO(ArticlesVO articlesVO) {
        this.articlesVO = articlesVO;
    }

    @Column(name = "ac_img", nullable = false, columnDefinition = "longblob")
    @NotNull(message = "圖片: 請上傳圖片")
    public byte[] getAcImg() {
        return acImg;
    }

    public void setAcImg(byte[] acImg) {
        this.acImg = acImg;
    }

	@Override
	public String toString() {
		return "ArticleImageVO [acImgId=" + acImgId + ", articlesVO=" + articlesVO + ", acImg=" + Arrays.toString(acImg)
				+ "]";
	}
    
    
}
