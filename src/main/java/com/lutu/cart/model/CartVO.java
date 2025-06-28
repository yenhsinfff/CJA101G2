package com.lutu.cart.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

//@RedisHash("cartItem")
@Entity
public class CartVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CartKey CartKey; // 唯一主鍵

	@Transient
	private Integer memId; // 露營者編號

	@Transient
	private Integer prodId; // 商品編號

	@Transient
	private Integer prodColorId; // 顏色編號

	@Transient
	private Integer prodSpecId; // 規格編號
	
	private Integer prodPrice;	// 商品價格
	
	private Integer cartProdQty; // 商品數量

	public CartKey getCartKey() {
		return CartKey;
	}

	public void setCartKey(CartKey CartKey) {
		this.CartKey = CartKey;

	}

	public CartVO() {
		super();
	}

	public CartVO(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId, Integer cartProdQty) {
		super();
		this.memId = memId;
		this.prodId = prodId;
		this.prodColorId = prodColorId;
		this.prodSpecId = prodSpecId;
		this.cartProdQty = cartProdQty;
	}

	public Integer getMemId() {
//		return memId;
		return CartKey != null ? CartKey.getMemId() : null;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public Integer getProdId() {
//		return prodId;
		return CartKey != null ? CartKey.getProdId() : null;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Integer getProdColorId() {
//		return prodColorId;
		return CartKey != null ? CartKey.getProdColorId() : null;
	}

	public void setProdColorId(Integer prodColorId) {
		this.prodColorId = prodColorId;
	}

	public Integer getProdSpecId() {
//		return prodSpecId;
		return CartKey != null ? CartKey.getProdSpecId() : null;
	}

	public void setProdSpecId(Integer prodSpecId) {
		this.prodSpecId = prodSpecId;
	}

	public Integer getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Integer prodPrice) {
		this.prodPrice = prodPrice;
	}

	public Integer getCartProdQty() {
		return cartProdQty;
	}

	public void setCartProdQty(Integer cartProdQty) {
		this.cartProdQty = cartProdQty;
	}

	@Embeddable
	public static class CartKey implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer memId;
		private Integer prodId;
		private Integer prodColorId;
		private Integer prodSpecId;

		public CartKey() {
			super();
		}

		public CartKey(Integer memId, Integer prodId, Integer prodColorId, Integer prodSpecId) {
			super();
			this.memId = memId;
			this.prodId = prodId;
			this.prodColorId = prodColorId;
			this.prodSpecId = prodSpecId;
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
			return Objects.hash(memId, prodColorId, prodId, prodSpecId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CartKey other = (CartKey) obj;
			return Objects.equals(memId, other.memId) && Objects.equals(prodColorId, other.prodColorId)
					&& Objects.equals(prodId, other.prodId) && Objects.equals(prodSpecId, other.prodSpecId);
		}

	}

}
