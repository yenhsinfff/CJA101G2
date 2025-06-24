package com.lutu.productType.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ProdTypeDTO {
    private Integer prodTypeId;
    
    @NotEmpty(message="商品類型名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$", message = "商品類型名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間")
    private String prodTypeName;

    public ProdTypeDTO(Integer id, String name) {
        this.prodTypeId = id;
        this.prodTypeName = name;
    }

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

}
