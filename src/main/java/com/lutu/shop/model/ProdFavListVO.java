package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "prod_fav_list")
public class ProdFavListVO implements Serializable {

	// 直接宣告複合識別類別的屬性，並加上 @EmbeddedId 標註
	@EmbeddedId
	private CompositeDetail compositeKey;

	public CompositeDetail getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeDetail compositeKey) {
		this.compositeKey = compositeKey;
	}


	// 需要宣告一個有包含複合主鍵屬性的類別，並一定實作 java.io.Serializable 介面
	@Embeddable
	public static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;
		@Column(name = "memId")
		private Integer memId; // 會員編號PK
		@Column(name = "prodId")
		private Integer prodId; // 商品編號PK

		// 一定要有無參數建構子
		public CompositeDetail() {
			super();
		}
		
		public CompositeDetail(Integer memId, Integer prodId) {
			super();
			this.memId = memId;
			this.prodId = prodId;
		}

		public Integer getMemId() {
			return memId;
		}

		public void setMemId(Integer memId) {
			this.memId = memId;
		}

		public Integer getProdId() {
			return prodId;
		}

		public void setProdId(Integer prodId) {
			this.prodId = prodId;
		}

		// 一定要 override 此類別的 hashCode() 與 equals() 方法！
		@Override
		public int hashCode() {
			return Objects.hash(memId, prodId);
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
			return Objects.equals(memId, other.memId) && Objects.equals(prodId, other.prodId);
		}
		
	}

	/*
	 * private Integer memId; // 會員編號PK private Integer prodId; // 商品編號PK
	 * 
	 * public ProdFavListVO() { super(); }
	 * 
	 * public ProdFavListVO(Integer memId, Integer prodId) { super(); this.memId =
	 * memId; this.prodId = prodId; }
	 * 
	 * public Integer getMemId() { return memId; }
	 * 
	 * public void setMemId(Integer memId) { this.memId = memId; }
	 * 
	 * public Integer getProdId() { return prodId; }
	 * 
	 * public void setProdId(Integer prodId) { this.prodId = prodId; }
	 * 
	 * @Override public int hashCode() { return Objects.hash(memId, prodId); }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; ProdFavListVO other = (ProdFavListVO) obj; return
	 * Objects.equals(memId, other.memId) && Objects.equals(prodId, other.prodId); }
	 * 
	 * @Override public String toString() { return "ProdFavList [露營者編號=" + memId +
	 * ", 商品編號=" + prodId + "]"; }
	 */

}
