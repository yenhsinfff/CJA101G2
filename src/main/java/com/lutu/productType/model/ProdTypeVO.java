package com.lutu.productType.model;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "prod_type")
public class ProdTypeVO implements Serializable {
	@Id
	@Column(name = "prod_type_id", updatable = false)
	private Integer prodTypeId; // 商品類型編號
	
	@Column(name = "prod_type_name")
	@NotEmpty(message="商品類型名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$", message = "商品類型名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間")
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

