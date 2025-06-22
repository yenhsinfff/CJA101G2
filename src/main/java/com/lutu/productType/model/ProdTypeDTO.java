package com.lutu.productType.model;

public class ProdTypeDTO {
    private Integer prodTypeId;
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
