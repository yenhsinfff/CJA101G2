package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;

public class ProdFavListVO implements Serializable{
	
	private Integer memId; 	// 會員編號PK
	private Integer prodId; // 商品編號PK
	public ProdFavListVO() {
		super();
	}
	public ProdFavListVO(Integer memId, Integer prodId) {
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
		ProdFavListVO other = (ProdFavListVO) obj;
		return Objects.equals(memId, other.memId) && Objects.equals(prodId, other.prodId);
	}
	@Override
	public String toString() {
		return "ProdFavList [露營者編號=" + memId + ", 商品編號=" + prodId + "]";
	}
	
	
}
