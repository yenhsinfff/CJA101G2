package com.lutu.camptracklist.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "camp_track_list") // 營地收藏
public class CampTrackListVO{

	// 直接宣告複合識別類別的屬性，並加上 @EmbeddedId 標註
	@EmbeddedId
	private CompositeDetail compositeKey;
	
	@Column(name = "mem_track_date")
	private Date memTrackDate; // 收藏時間

	// 特別加上對複合主鍵物件的 getter / setter
	public CompositeDetail getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeDetail compositeKey) {
		this.compositeKey = compositeKey;
	}

	public Date getMemTrackDate() {
		return memTrackDate;
	}

	public void setMemTrackDate(Date memTrackDate) {
		this.memTrackDate = memTrackDate;
	}

	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	@Embeddable
	public static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "camp_id")
		private Integer campId; // 營地編號

		@Column(name = "mem_id")
		private Integer memId; // 露營者編號

		// 一定要有無參數建構子
		public CompositeDetail() {
			super();
		}

		public Integer getCampId() {
			return campId;
		}

		public void setCampId(Integer campId) {
			this.campId = campId;
		}

		public Integer getMemId() {
			return memId;
		}

		public void setMemId(Integer memId) {
			this.memId = memId;
		}

		// 一定要 override 此類別的 hashCode() 與 equals() 方法
		@Override
		public int hashCode() {
			return Objects.hash(campId, memId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeDetail other = (CompositeDetail) obj;
			return Objects.equals(campId, other.campId) && Objects.equals(memId, other.memId);
		}

	}

}
