package com.lutu.nice_article.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "nice_article")
@IdClass(NiceArticleId.class)
public class NiceArticleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer acId;				// 討論區文章編號 (PK, FK)
    private Integer memId;				// 露營者編號     (PK, FK)
    private LocalDateTime likeTime;		// 按讚時間

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
    @NotNull(message = "按讚時間: 不能為空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(LocalDateTime likeTime) {
        this.likeTime = likeTime;
    }
}

// 複合主鍵類別
class NiceArticleId implements Serializable {
    private Integer acId;
    private Integer memId;

    public NiceArticleId() {
    	
    }

    public NiceArticleId(Integer acId, Integer memId) {
        this.acId = acId;
        this.memId = memId;
    }

    // getter, setter, equals, hashCode...
    public Integer getAcId() { return acId; }
    public void setAcId(Integer acId) { this.acId = acId; }
    public Integer getMemId() { return memId; }
    public void setMemId(Integer memId) { this.memId = memId; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NiceArticleId that = (NiceArticleId) obj;
        return acId.equals(that.acId) && memId.equals(that.memId);
    }

    @Override
    public int hashCode() {
        return acId.hashCode() + memId.hashCode();
    }
}
