package com.lutu.prodColorList.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import com.lutu.colorList.model.ColorListVO;
import com.lutu.prodColorList.model.ProdColorListVO.CompositeDetail;
import com.lutu.shopProd.model.ShopProdVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "prod_color_list")
@IdClass(CompositeDetail.class)
public class ProdColorListVO implements Serializable {

	@Id
	@Column(name = "prod_id")
	private Integer prodId; // PK

	@Id
	@Column(name = "prod_color_id")
	private Integer prodColorId; // PK

	@Lob
	@Column(name = "prod_color_pic")
	private byte[] prodColorPic;

	@Column(name = "prod_color_status")
	@NotNull(message = "商品顏色狀態: 請勿空白")
	@Min(value = 0, message = "商品顏色狀態只能是 0 或 1")
	@Max(value = 1, message = "商品顏色狀態只能是 0 或 1")
	private Byte prodColorStatus;// 0:未上架 1:上架
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_color_id", referencedColumnName = "color_id", insertable = false, updatable = false)
	private ColorListVO colorListVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id", referencedColumnName = "prod_id", insertable = false, updatable = false)
	private ShopProdVO shopProdVO;
	
	// 特別加上對複合主鍵物件的 getter / setter
	public CompositeDetail getCompositeKey() {
		return new CompositeDetail(prodId, prodColorId);
	}

	public void setCompositeKey(CompositeDetail key) {
		this.prodId = key.getProdId();
		this.prodColorId = key.getProdColorId();
	}

	
	public ProdColorListVO() {
		super();
	}

	public ProdColorListVO(Integer prodId, Integer prodColorId, byte[] prodColorPic) {
		super();
		this.prodId = prodId;
		this.prodColorId = prodColorId;
		this.prodColorPic = prodColorPic;
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

	public byte[] getProdColorPic() {
		return prodColorPic;
	}

	public void setProdColorPic(byte[] prodColorPic) {
		this.prodColorPic = prodColorPic;
	}

	public ColorListVO getColorListVO() {
		return colorListVO;
	}

	public void setColorListVO(ColorListVO colorListVO) {
		this.colorListVO = colorListVO;
	}

	public ShopProdVO getShopProdVO() {
		return shopProdVO;
	}

	public void setShopProdVO(ShopProdVO shopProdVO) {
		this.shopProdVO = shopProdVO;
	}

	public Byte getProdColorStatus() {
		return prodColorStatus;
	}

	public void setProdColorStatus(Byte prodColorStatus) {
		this.prodColorStatus = prodColorStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodColorId, prodId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdColorListVO other = (ProdColorListVO) obj;
		return Objects.equals(prodColorId, other.prodColorId) && Objects.equals(prodId, other.prodId);
	}

	@Override
	public String toString() {
		return "ProdColorList [商品編號=" + prodId + ", 商品顏色編號=" + prodColorId + ", 商品顏色圖片="
				+ Arrays.toString(prodColorPic) + "]";
	}

	
	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer prodId;
		private Integer prodColorId;

		// 一定要有無參數建構子
		public CompositeDetail() {
			super();
		}

		public CompositeDetail(Integer prodId, Integer prodColorId) {
			super();
			this.prodId = prodId;
			this.prodColorId = prodColorId;
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

		// 一定要 override 此類別的 hashCode() 與 equals() 方法！
		@Override
		public int hashCode() {
			return Objects.hash(prodColorId, prodId);
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
			return Objects.equals(prodColorId, other.prodColorId) && Objects.equals(prodId, other.prodId);
		}

	}
}
