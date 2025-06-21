package com.lutu.ac_fav_record.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ac_fav_record")
@IdClass(com.lutu.ac_fav_record.model.AcFavRecordVO.AcFavRecordId.class)
public class AcFavRecordVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer acId;
	private Integer memId;
	private LocalDateTime acFavTime;

	public AcFavRecordVO() {
	}

	// ========================================================
	// 特別加上對複合主鍵物件的 getter / setter
	public AcFavRecordId getCompositeKey() {
		return new AcFavRecordId(acId, memId);
	}

	public void setCompositeKey(AcFavRecordId key) {
		this.acId = key.getAcId();
		this.memId = key.getMemId();
	}
	// ========================================================

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

	@Column(name = "ac_fav_time", nullable = false)
	@NotNull(message = "收藏時間: 不能為空")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime getAcFavTime() {
		return acFavTime;
	}

	public void setAcFavTime(LocalDateTime acFavTime) {
		this.acFavTime = acFavTime;
	}
	
	
	//========================================================
	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	
	static class AcFavRecordId implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer acId;
		private Integer memId;

		// 一定要有無參數建構子
		public AcFavRecordId() {
		}

		public AcFavRecordId(Integer acId, Integer memId) {
			this.acId = acId;
			this.memId = memId;
		}

		// getter, setter, equals, hashCode...
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

		// 一定要 override 此類別的 hashCode() 與 equals() 方法！
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			AcFavRecordId that = (AcFavRecordId) obj;
			return acId.equals(that.acId) && memId.equals(that.memId);
		}

		@Override
		public int hashCode() {
			return acId.hashCode() + memId.hashCode();
		}
	}
}



