package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ProdColorListVO implements Serializable{

	private Integer prodId; 		// PK
	private Integer prodColorId; 	// PK
	private byte[] prodColorPicture;
	
	
	public ProdColorListVO() {
		super();
	}


	public ProdColorListVO(Integer prodId, Integer prodColorId, byte[] prodColorPicture) {
		super();
		this.prodId = prodId;
		this.prodColorId = prodColorId;
		this.prodColorPicture = prodColorPicture;
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


	public byte[] getProdColorPicture() {
		return prodColorPicture;
	}


	public void setProdColorPicture(byte[] prodColorPicture) {
		this.prodColorPicture = prodColorPicture;
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
				+ Arrays.toString(prodColorPicture) + "]";
	}  
	
	
}
