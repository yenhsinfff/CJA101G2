package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;

public class ProdSpecListVO implements Serializable{

	private Integer prodId;           // 商品編號 PK
    private Integer prodSpecId;       // 商品規格編號 PK
    private Integer prodSpecPrice;    // 規格價格
    
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
		return "ProdSpecList [商品編號=" + prodId + ", 商品規格編號=" + prodSpecId + ", 規格價格=" + prodSpecPrice
				+ "]";
	}
	
	
    
}
