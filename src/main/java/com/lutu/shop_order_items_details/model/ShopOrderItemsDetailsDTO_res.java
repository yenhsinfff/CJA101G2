package com.lutu.shop_order_items_details.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class ShopOrderItemsDetailsDTO_res {
	
	@NotNull(message = "請輸入訂單編號")
	private Integer shopOrderId; // 訂單編號

	@NotNull(message = "請輸入商品編號")
	private Integer prodId; // 商品編號
	
	private String prodName; // 商品名稱

	@NotNull(message = "請輸入數量")
	private Integer shopOrderQty; // 購買數量
	
	private Integer prodOrderPrice; // 商品售價

	// 商品訂單明細新增時不會填寫
	private Byte commentSatis; // 評價滿意度

	// 商品訂單明細新增時不會填寫
	private String commentContent; // 評價內容

	// 商品訂單明細新增時不會填寫
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime commentDate; // 評價日期
	
	private String prodColorName; // 商品顏色名稱

	private String prodSpecName; // 商品規格名稱

	public ShopOrderItemsDetailsDTO_res() {
	}

	// --- Getters and Setters ---
	
	public Integer getProdId() {
		return prodId;
	}

	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
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

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdColorName() {
		return prodColorName;
	}

	public void setProdColorName(String prodColorName) {
		this.prodColorName = prodColorName;
	}

	public String getProdSpecName() {
		return prodSpecName;
	}

	public void setProdSpecName(String prodSpecName) {
		this.prodSpecName = prodSpecName;
	}
	
	// 新增時不會填寫評分和評價
	public Byte getCommentSatis() {
		return commentSatis;
	}

	public void setCommentSatis(Byte commentSatis) {
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

	public Integer getProdOrderPrice() {
		return prodOrderPrice;
	}

	public void setProdOrderPrice(Integer prodOrderPrice) {
		this.prodOrderPrice = prodOrderPrice;
	}

	

}
