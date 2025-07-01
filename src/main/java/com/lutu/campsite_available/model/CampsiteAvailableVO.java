package com.lutu.campsite_available.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "campsite_available")
public class CampsiteAvailableVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CACompositeDetail id;

	/** 剩餘房量 */
	@Column(name = "remaining",nullable = false)
	private Integer remaining;
	
	@Column(name = "camp_id",nullable = false)
	private Integer campId;

	/** 最後一次更新時間（由 DB 自動維護） */
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;

//    @JoinColumn(name = "campsite_type_id")
//    private CampsiteTypeVO campsiteType;

	public CACompositeDetail getId() {
		return id;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public void setId(CACompositeDetail id) {
		this.id = id;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	/* ===== 方便呼叫的方法 ===== */
	public boolean deduct(int qty) {
		if (remaining < qty)
			return false;
		remaining -= qty;
		return true;
	}

	public void refund(int qty) {
		remaining += qty;
	}

	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	@Embeddable
	public static class CACompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "campsite_type_id")
		private Integer campsiteTypeId;

		@Column(name = "date")
		private Date date;

		// 一定要有無參數建構子
		public CACompositeDetail(Integer campsiteTypeId, Date date) {
			this.campsiteTypeId = campsiteTypeId;
			this.date = date;
		}

		// 一定要有無參數建構子
		public CACompositeDetail() {
			super();
		}

		public Integer getCampsiteTypeId() {
			return campsiteTypeId;
		}

		public void setCampsiteTypeId(Integer campsiteTypeId) {
			this.campsiteTypeId = campsiteTypeId;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		// 一定要 override 此類別的 hashCode() 與 equals() 方法
		@Override
		public int hashCode() {
			return Objects.hash(campsiteTypeId, date);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CACompositeDetail other = (CACompositeDetail) obj;
			return Objects.equals(campsiteTypeId, other.campsiteTypeId) && Objects.equals(date, other.date);
		}

		@Override
		public String toString() {
			return "CompositeDetail [campsiteTypeId=" + campsiteTypeId + ", date=" + date + "]";
		}

	}

}
