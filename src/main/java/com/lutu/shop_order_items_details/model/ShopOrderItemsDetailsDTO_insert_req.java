package com.lutu.shop_order_items_details.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class ShopOrderItemsDetailsDTO_insert_req {

	@NotNull(message = "請輸入商品編號")
	private Integer prodId; // 商品編號

	@NotNull(message = "請輸入數量")
	private Integer shopOrderQty; // 購買數量

	// 商品訂單明細新增時不會填寫
//	private Byte commentSatis; // 評價滿意度
//
//	private String commentContent; // 評價內容
//
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//	private LocalDateTime commentDate; // 評價日期

	@NotNull(message = "請輸入商品顏色")
	private Integer prodColorId; // 商品顏色編號

	@NotNull(message = "請輸入商品規格")
	private Integer prodSpecId; // 商品規格編號

	public ShopOrderItemsDetailsDTO_insert_req() {
	}

	// --- Getters and Setters ---
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
	
	//新增時不會填寫評分和評價
//	public Byte getCommentSatis() {
//		return commentSatis;
//	}
//
//	public void setCommentSatis(Byte commentSatis) {
//		this.commentSatis = commentSatis;
//	}
//
//	public String getCommentContent() {
//		return commentContent;
//	}
//
//	public void setCommentContent(String commentContent) {
//		this.commentContent = commentContent;
//	}
//
//	public LocalDateTime getCommentDate() {
//		return commentDate;
//	}
//
//	public void setCommentDate(LocalDateTime commentDate) {
//		this.commentDate = commentDate;
//	}

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
