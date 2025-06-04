package com.lutu.shop_order_items_details.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ShopOrderItemsDetailsVO implements Serializable {

	private Integer shopOrderId; // 訂單編號
	private Integer prodId; // 商品編號
	private Integer shopOrderQty; // 購買數量
	private Integer prodOrderPrice; // 商品售價
	private Byte commentSatis; // 評價滿意度
	private String commentContent; // 評價內容
	private LocalDateTime commentDate; // 評價日期
	private Integer prodColorId; // 商品顏色編號
	private Integer prodSpecId; // 商品規格編號

	public ShopOrderItemsDetailsVO() {

	}

	public ShopOrderItemsDetailsVO(Integer shopOrderId, Integer prodId, Integer shopOrderQty, Integer prodOrderPrice,
			byte commentSatis, String commentContent, LocalDateTime commentDate, Integer prodColorId,
			Integer prodSpecId) {
		super();
		this.shopOrderId = shopOrderId;
		this.prodId = prodId;
		this.shopOrderQty = shopOrderQty;
		this.prodOrderPrice = prodOrderPrice;
		this.commentSatis = commentSatis;
		this.commentContent = commentContent;
		this.commentDate = commentDate;
		this.prodColorId = prodColorId;
		this.prodSpecId = prodSpecId;
	}
	
	

	// --- Getters and Setters ---
	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Integer getShopOrderQty() {
		return shopOrderQty;
	}

	public void setShopOrderQty(Integer shopOrderQty) {
		this.shopOrderQty = shopOrderQty;
	}

	public Integer getProdOrderPrice() {
		return prodOrderPrice;
	}

	public void setProdOrderPrice(Integer prodOrderPrice) {
		this.prodOrderPrice = prodOrderPrice;
	}

	public byte getCommentSatis() {
		return commentSatis;
	}

	public void setCommentSatis(byte commentSatis) {
		this.commentSatis = commentSatis;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public LocalDateTime getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(LocalDateTime commentDate) {
		this.commentDate = commentDate;
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

}
