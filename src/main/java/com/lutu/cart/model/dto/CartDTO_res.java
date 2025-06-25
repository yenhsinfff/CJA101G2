package com.lutu.cart.model.dto;

public class CartDTO_res {
	
	private Integer memId; // 露營者編號
	private Integer prodId; // 商品編號
	private String prodName; // 商品名稱
	private Integer prodColorId; // 顏色編號
	private String colorName;	//顏色名稱
	private Integer prodSpecId; // 規格編號
	private String specName; 	// 規格名稱
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
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public Integer getProdColorId() {
		return prodColorId;
	}
	public void setProdColorId(Integer prodColorId) {
		this.prodColorId = prodColorId;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public Integer getProdSpecId() {
		return prodSpecId;
	}
	public void setProdSpecId(Integer prodSpecId) {
		this.prodSpecId = prodSpecId;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public Integer getCartProdQty() {
		return cartProdQty;
	}
	public void setCartProdQty(Integer cartProdQty) {
		this.cartProdQty = cartProdQty;
	}

}
