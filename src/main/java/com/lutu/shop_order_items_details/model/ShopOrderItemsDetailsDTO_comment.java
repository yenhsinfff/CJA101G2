package com.lutu.shop_order_items_details.model;

import java.io.Serializable;

public class ShopOrderItemsDetailsDTO_comment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer prodId; // 商品編號
	
	private Integer memId;	// 會員編號

	private Byte commentSatis = 0; // 評價滿意度，預設是0，代表尚未填寫，1~5才是有效分數

	private String commentContent; // 評價內容

	// --- Getters and Setters ---
	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

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

}
