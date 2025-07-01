package com.lutu.ac_fav_record.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.lutu.article.model.ArticlesVO;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ac_fav_record")
@IdClass(com.lutu.ac_fav_record.model.AcFavRecordVO.AcFavRecordId.class)
public class AcFavRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ac_id")
    @NotNull(message = "文章ID: 不能為空")
    private Integer acId;

    @Id
    @Column(name = "mem_id")
    @NotNull(message = "會員ID: 不能為空")
    private Integer memId;

    @Column(name = "ac_fav_time", nullable = false)
    @NotNull(message = "收藏時間: 不能為空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acFavTime;

    // 修正關聯映射 - 設定 insertable 和 updatable 為 false，避免重複欄位
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ac_id", referencedColumnName = "ac_id", insertable = false, updatable = false)
    @JsonIgnore
    private ArticlesVO articlesVO;

    // 無參數建構子
    public AcFavRecordVO() {
    }

    // 有參數建構子
    public AcFavRecordVO(Integer acId, Integer memId, LocalDateTime acFavTime) {
        this.acId = acId;
        this.memId = memId;
        this.acFavTime = acFavTime;
    }

    // ========================================================
    // ArticlesVO 的 getter 和 setter
    public ArticlesVO getArticlesVO() {
        return articlesVO;
    }

    public void setArticlesVO(ArticlesVO articlesVO) {
        this.articlesVO = articlesVO;
    }

    // 特別加上對複合主鍵物件的 getter / setter
    // 加上 @Transient 避免 JPA 將其當作持久化屬性
    @Transient
    public AcFavRecordId getCompositeKey() {
        return new AcFavRecordId(acId, memId);
    }

    @Transient
    public void setCompositeKey(AcFavRecordId key) {
        this.acId = key.getAcId();
        this.memId = key.getMemId();
    }

    // ========================================================
    // 基本屬性的 getter 和 setter
    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public LocalDateTime getAcFavTime() {
        return acFavTime;
    }

    public void setAcFavTime(LocalDateTime acFavTime) {
        this.acFavTime = acFavTime;
    }

    // ========================================================
    // 複合主鍵類別
    public static class AcFavRecordId implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer acId;
        private Integer memId;

        // 無參數建構子
        public AcFavRecordId() {
        }

        // 有參數建構子
        public AcFavRecordId(Integer acId, Integer memId) {
            this.acId = acId;
            this.memId = memId;
        }

        // getter 和 setter
        public Integer getAcId() {
            return acId;
        }

        public void setAcId(Integer acId) {
            this.acId = acId;
        }

        public Integer getMemId() {
            return memId;
        }

        public void setMemId(Integer memId) {
            this.memId = memId;
        }

        // 必須 override equals() 和 hashCode()
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            AcFavRecordId that = (AcFavRecordId) obj;
            return acId != null ? acId.equals(that.acId)
                    : that.acId == null &&
                            memId != null ? memId.equals(that.memId) : that.memId == null;
        }

        @Override
        public int hashCode() {
            int result = acId != null ? acId.hashCode() : 0;
            result = 31 * result + (memId != null ? memId.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "AcFavRecordId{" +
                    "acId=" + acId +
                    ", memId=" + memId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AcFavRecordVO{" +
                "acId=" + acId +
                ", memId=" + memId +
                ", acFavTime=" + acFavTime +
                ", articlesVO=" + (articlesVO != null ? articlesVO.getClass().getSimpleName() : "null") +
                '}';
    }

    // 輔助方法
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AcFavRecordVO that = (AcFavRecordVO) obj;
        return acId != null ? acId.equals(that.acId)
                : that.acId == null &&
                        memId != null ? memId.equals(that.memId) : that.memId == null;
    }

    @Override
    public int hashCode() {
        int result = acId != null ? acId.hashCode() : 0;
        result = 31 * result + (memId != null ? memId.hashCode() : 0);
        return result;
    }
}