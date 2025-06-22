package com.lutu.article_type.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.lutu.article.model.ArticlesVO;

@Entity
@Table(name = "article_type")
public class ArticleTypeVO implements Serializable {

	//serialVersionUID 是一個 唯一的版本識別碼，用來判斷序列化（Serialization）與反序列化（Deserialization）時，Java 是否可以正確還原物件。
    private static final long serialVersionUID = 1L;
    
    private Integer acTypeId;			//文章類別編號 (PK)
    private String acTypeKind;			//文章類別名稱
    private String acTypeText;			//文章類別敘述
    private Set<ArticlesVO> articles;

    public ArticleTypeVO() {
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_type_id")
    public Integer getAcTypeId() {
        return acTypeId;
    }

    public void setAcTypeId(Integer acTypeId) {
        this.acTypeId = acTypeId;
    }

    @Column(name = "ac_type_kind", nullable = false, length = 50)
    @NotEmpty(message = "文章類別名稱: 請勿空白")
    @Size(max = 50, message = "文章類別名稱: 長度不能超過{50}個字元")
    public String getAcTypeKind() {
        return acTypeKind;
    }

    public void setAcTypeKind(String acTypeKind) {
        this.acTypeKind = acTypeKind;
    }

    @Column(name = "ac_type_text", length = 80)
    @Size(max = 80, message = "類別描述: 長度不能超過{80}個字元")
    public String getAcTypeText() {
        return acTypeText;
    }

    public void setAcTypeText(String acTypeText) {
        this.acTypeText = acTypeText;
    }

    @OneToMany(mappedBy = "articleTypeVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ArticlesVO> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticlesVO> articles) {
        this.articles = articles;
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(acTypeId);  // 通常只用主鍵
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArticleTypeVO other = (ArticleTypeVO) obj;
        return Objects.equals(acTypeId, other.acTypeId);
    }
    
    
}