package com.lutu.cart.model.dto;

public class CartDTO_req {
	
	private Integer memId; // 露營者編號
	private Integer prodId; // 商品編號
	
	private Integer prodColorId; // 顏色編號

	private Integer prodSpecId; // 規格編號

	private Integer cartProdQty; // 商品數量
	
	
	
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

	public Integer getProdColorId() {
		return prodColorId;
	}
	public void setProdColorId(Integer prodColorId) {
		this.prodColorId = prodColorId;
	}
	
	public Integer getProdSpecId() {
		return prodSpecId;
	}
	public void setProdSpecId(Integer prodSpecId) {
		this.prodSpecId = prodSpecId;
	}
	
	public Integer getCartProdQty() {
		return cartProdQty;
	}
	public void setCartProdQty(Integer cartProdQty) {
		this.cartProdQty = cartProdQty;
	}

}
