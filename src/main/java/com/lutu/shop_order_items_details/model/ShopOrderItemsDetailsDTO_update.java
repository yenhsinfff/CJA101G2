package com.lutu.shop_order_items_details.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.prodColorList.model.ProdColorListVO;
import com.lutu.prodSpecList.model.ProdSpecListVO;
import com.lutu.shopProd.model.ShopProdVO;
import com.lutu.shop_order.model.ShopOrderVO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class ShopOrderItemsDetailsDTO_update {

	@NotNull(message = "請提供訂單編號")
	private Integer shopOrderId; // 訂單編號

	@NotNull(message = "請提供商品編號")
	private Integer prodId; // 商品編號
	
	@NotNull(message = "請提供商品顏色編號")
	private Integer prodColorId; // 商品顏色編號

	@NotNull(message = "請提供商品規格編號")
	private Integer prodSpecId; // 商品規格編號

	private Byte commentSatis = 0; // 評價滿意度，預設是0，代表尚未填寫，1~5才是有效分數

	private String commentContent; // 評價內容

	
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
