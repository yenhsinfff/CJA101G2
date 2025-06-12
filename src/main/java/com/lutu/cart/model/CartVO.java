package com.lutu.cart.model;

public class CartVO {
	private Integer memId;		// 露營者編號
	private Integer prodId; 	// 商品編號
	private Integer colorId;      // 顏色編號
	private Integer specId;       // 規格編號
	private Integer cartProdQty;	// 商品數量
	
	public CartVO() {
		super();
	}

	public CartVO(Integer memId, Integer prodId, Integer colorId, Integer specId, Integer cartProdQty) {
		super();
		this.memId = memId;
		this.prodId = prodId;
		this.colorId = colorId;
		this.specId = specId;
		this.cartProdQty = cartProdQty;
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

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public Integer getSpecId() {
		return specId;
	}

	public void setSpecId(Integer specId) {
		this.specId = specId;
	}

	public Integer getCartProdQty() {
		return cartProdQty;
	}

	public void setCartProdQty(Integer cartProdQty) {
		this.cartProdQty = cartProdQty;
	}
	
	
	
	
}
