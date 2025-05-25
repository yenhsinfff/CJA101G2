package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ProdPicVO implements Serializable{
	
	private Integer prodPicId;    // 商品圖片編號 PK
    private Integer prodId;       // 商品編號
    private byte[] prodPic;       // 商品圖片
    
	public ProdPicVO() {
		super();
	}

	public ProdPicVO(Integer prodPicId, Integer prodId, byte[] prodPic) {
		super();
		this.prodPicId = prodPicId;
		this.prodId = prodId;
		this.prodPic = prodPic;
	}

	public Integer getProdPicId() {
		return prodPicId;
	}

	public void setProdPicId(Integer prodPicId) {
		this.prodPicId = prodPicId;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public byte[] getProdPic() {
		return prodPic;
	}

	public void setProdPic(byte[] prodPic) {
		this.prodPic = prodPic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodPicId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdPicVO other = (ProdPicVO) obj;
		return Objects.equals(prodPicId, other.prodPicId);
	}

	@Override
	public String toString() {
		return "ProdPic [商品圖片編號=" + prodPicId + ", 商品編號=" + prodId + ", 商品圖片=" + Arrays.toString(prodPic)
				+ "]";
	}
    
    
}
