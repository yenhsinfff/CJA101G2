package com.lutu.shop_order_items_details.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsVO.CompositeDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "shop_order_items_details")
@IdClass(CompositeDetail.class)
public class ShopOrderItemsDetailsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "shop_order_id")
	private Integer shopOrderId; // 訂單編號

	@Id
	@Column(name = "prod_id")
	private Integer prodId; // 商品編號

	@Column(name = "shop_order_qty")
	private Integer shopOrderQty; // 購買數量

	@Column(name = "prod_order_price")
	private Integer prodOrderPrice; // 商品售價

	@Column(name = "comment_satis")
	private Byte commentSatis; // 評價滿意度

	@Column(name = "comment_content")
	private String commentContent; // 評價內容

	@Column(name = "comment_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime commentDate; // 評價日期

	@Id
	@Column(name = "prod_color_id")
	private Integer prodColorId; // 商品顏色編號

	@Id
	@Column(name = "prod_spec_id")
	private Integer prodSpecId; // 商品規格編號

	public CompositeDetail getCompositeKey() {
		return new CompositeDetail(shopOrderId, prodId, prodColorId, prodSpecId);
	}

	public void setCompositeKey(CompositeDetail key) {
		this.shopOrderId = key.getShopOrderId();
		this.prodId = key.getProdId();
		this.prodColorId = key.getProdColorId();
		this.prodSpecId = key.getProdSpecId();

	}

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

	static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer shopOrderId; // 訂單編號
		private Integer prodId; // 商品編號
		private Integer prodColorId; // 商品顏色編號
		private Integer prodSpecId; // 商品規格編號

		public CompositeDetail() {
			super();
		}

		public CompositeDetail(Integer shopOrderId, Integer prodId, Integer prodColorId, Integer prodSpecId) {
			super();
			this.shopOrderId = shopOrderId;
			this.prodId = prodId;
			this.prodColorId = prodColorId;
			this.prodSpecId = prodSpecId;
		}

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

		@Override
		public int hashCode() {
			return Objects.hash(prodColorId, prodId, prodSpecId, shopOrderId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeDetail other = (CompositeDetail) obj;
			return Objects.equals(prodColorId, other.prodColorId) && Objects.equals(prodId, other.prodId)
					&& Objects.equals(prodSpecId, other.prodSpecId) && Objects.equals(shopOrderId, other.shopOrderId);
		}

	}

}
