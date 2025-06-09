package com.lutu.product_type.model;

import java.io.Serializable;

public class ProductTypeVO implements Serializable {
	private Integer prodTypeId; // 商品類型編號
	private String prodTypeName; // 商品類型名稱

	public ProductTypeVO() {
	}

	public ProductTypeVO(Integer prodTypeId, String prodTypeName) {
		super();
		this.prodTypeId = prodTypeId;
		this.prodTypeName = prodTypeName;
	}

	
	// --- Getters and Setters ---
	public Integer getProdTypeId() {
		return prodTypeId;
	}

	public void setProdTypeId(Integer prodTypeId) {
		this.prodTypeId = prodTypeId;
	}

	public String getProdTypeName() {
		return prodTypeName;
	}

	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}

}
