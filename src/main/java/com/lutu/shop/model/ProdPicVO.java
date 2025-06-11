package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prod_pic")
public class ProdPicVO implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //AI
	@Column(name = "prod_pic_id", updatable = false)
	private Integer prodPicId;    // 商品圖片編號 PK
	
	@Column(name = "prod_id")
    private Integer prodId;       // 商品編號 FK
	
	@Column(name = "prod_pic")
    private byte[] prodPic;       // 商品圖片
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id", referencedColumnName = "prod_id")
	private ShopProdVO shopProdVO;
	
    
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

	public ShopProdVO getShopProdVO() {
		return shopProdVO;
	}

	public void setShopProdVO(ShopProdVO shopProdVO) {
		this.shopProdVO = shopProdVO;
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
