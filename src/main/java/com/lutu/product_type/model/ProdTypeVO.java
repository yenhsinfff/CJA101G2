package com.lutu.product_type.model;

import java.io.Serializable;
import java.util.Set;

import com.lutu.shopProd.model.ShopProdVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "prod_type")
public class ProdTypeVO implements Serializable {
	@Id
	@Column(name = "prod_type_id", updatable = false)
	private Integer prodTypeId; // 商品類型編號
	
	@Column(name = "prod_type_name")
	private String prodTypeName; // 商品類型名稱
	
	@OneToMany(mappedBy = "prodTypeVO", cascade = CascadeType.ALL) //mappedBy:對方實體中的欄位名稱 ;CascadeType.ALL代表對ProdTypeVO 的儲存、刪除也會套用到它的 prods（商品們）
	@OrderBy("prodId asc") 
	private Set<ShopProdVO> prods;
	

	public ProdTypeVO() {
	}

	public ProdTypeVO(Integer prodTypeId, String prodTypeName) {
		super();
		this.prodTypeId = prodTypeId;
		this.prodTypeName = prodTypeName;
	}

	
	// --- Getters and Setters ---
	public Integer getProdTypeId() {
		return prodTypeId;
	}

	public void setProdTypeId(Integer prodTypeId) {
		this.prodTypeId = prodTypeId;
	}

	public String getProdTypeName() {
		return prodTypeName;
	}

	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}

	public Set<ShopProdVO> getProds() {
		return prods;
	}

	public void setProds(Set<ShopProdVO> prods) {
		this.prods = prods;
	}

}
