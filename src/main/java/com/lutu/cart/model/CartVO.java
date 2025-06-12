package com.lutu.cart.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
 


@RedisHash("cart")
public class CartVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private CartKey CartKey;	// 唯一主鍵
	
	private Integer memId; // 露營者編號
	private Integer prodId; // 商品編號
	private Integer prodColorId; // 顏色編號
	private Integer prodSpecId; // 規格編號
	private Integer cartProdQty; // 商品數量

	public CartKey getCartKey() {
		return  CartKey;
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

	static class CartKey implements Serializable {
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
