package com.lutu.prodSpecList.model;

import java.io.Serializable;
import java.util.Objects;

import com.lutu.prodSpecList.model.ProdSpecListVO.CompositeDetail2;
import com.lutu.shop.model.SpecListVO;
import com.lutu.shopProd.model.ShopProdVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prod_spec_list")
@IdClass(CompositeDetail2.class)
public class ProdSpecListVO implements Serializable {

	@Id
	@Column(name = "prod_id", insertable = false, updatable = false)
	private Integer prodId; // 商品編號 PK

	@Id
	@Column(name = "prod_spec_id")
	private Integer prodSpecId; // 商品規格編號 PK

	@Column(name = "prod_spec_price")
	private Integer prodSpecPrice; // 規格價格

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_spec_id", referencedColumnName = "spec_id")
	private SpecListVO specListVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id", referencedColumnName = "prod_id")
	private ShopProdVO shopProdVO;
	
	// 特別加上對複合主鍵物件的 getter / setter
	public CompositeDetail2 getCompositeKey() {
		return new CompositeDetail2(prodId, prodSpecId);
	}

	public void setCompositeKey(CompositeDetail2 key) {
		this.prodId = key.getProdId();
		this.prodSpecId = key.getProdSpecId();
	}

	public ProdSpecListVO() {
		super();
	}

	public ProdSpecListVO(Integer prodId, Integer prodSpecId, Integer prodSpecPrice) {
		super();
		this.prodId = prodId;
		this.prodSpecId = prodSpecId;
		this.prodSpecPrice = prodSpecPrice;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Integer getProdSpecId() {
		return prodSpecId;
	}

	public void setProdSpecId(Integer prodSpecId) {
		this.prodSpecId = prodSpecId;
	}

	public Integer getProdSpecPrice() {
		return prodSpecPrice;
	}

	public void setProdSpecPrice(Integer prodSpecPrice) {
		this.prodSpecPrice = prodSpecPrice;
	}

	public SpecListVO getSpecListVO() {
		return specListVO;
	}

	public void setSpecListVO(SpecListVO specListVO) {
		this.specListVO = specListVO;
	}

	public ShopProdVO getShopProdVO() {
		return shopProdVO;
	}

	public void setShopProdVO(ShopProdVO shopProdVO) {
		this.shopProdVO = shopProdVO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodId, prodSpecId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdSpecListVO other = (ProdSpecListVO) obj;
		return Objects.equals(prodId, other.prodId) && Objects.equals(prodSpecId, other.prodSpecId);
	}

	@Override
	public String toString() {
		return "ProdSpecList [商品編號=" + prodId + ", 商品規格編號=" + prodSpecId + ", 規格價格=" + prodSpecPrice + "]";
	}

	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	public static class CompositeDetail2 implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer prodId;
		private Integer prodSpecId;

		// 一定要有無參數建構子
		public CompositeDetail2() {
			super();
		}

		public CompositeDetail2(Integer prodId, Integer prodSpecId) {
			super();
			this.prodId = prodId;
			this.prodSpecId = prodSpecId;
		}

		public Integer getProdId() {
			return prodId;
		}

		public void setProdId(Integer prodId) {
			this.prodId = prodId;
		}

		public Integer getProdSpecId() {
			return prodSpecId;
		}

		public void setProdSpecId(Integer prodSpecId) {
			this.prodSpecId = prodSpecId;
		}

		// 一定要 override 此類別的 hashCode() 與 equals() 方法！
		@Override
		public int hashCode() {
			return Objects.hash(prodId, prodSpecId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeDetail2 other = (CompositeDetail2) obj;
			return Objects.equals(prodId, other.prodId) && Objects.equals(prodSpecId, other.prodSpecId);
		}

	}

}
