package com.lutu.prodFavList.model;

import java.io.Serializable;
import java.util.Objects;

import com.lutu.member.model.MemberVO;
import com.lutu.shopProd.model.ShopProdVO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prod_fav_list")
public class ProdFavListVO implements Serializable {

	// 直接宣告複合識別類別的屬性，並加上 @EmbeddedId 標註
	@EmbeddedId
	private CompositeDetail compositeKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prodId", referencedColumnName = "prod_id", insertable = false, updatable = false)
	private ShopProdVO shopProdVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memId", referencedColumnName = "mem_id", insertable = false, updatable = false)
	private MemberVO memberVO;

	public CompositeDetail getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeDetail compositeKey) {
		this.compositeKey = compositeKey;
	}

	public ShopProdVO getShopProdVO() {
		return shopProdVO;
	}

	public void setShopProdVO(ShopProdVO shopProdVO) {
		this.shopProdVO = shopProdVO;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
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


}
