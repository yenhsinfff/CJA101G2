package com.lutu.prodSpecList.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProdSpecListDTO {

    private Integer prodId;

    private Integer prodSpecId;

    @NotNull(message = "規格價格不可為空")
    @Min(value = 0, message = "規格價格不能為負數")
    private Integer prodSpecPrice;
    
    private String prodSpecName;

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Integer getProdSpecId() {
		return prodSpecId;
	}

	public void setProdSpecId(Integer prodSpecId) {
		this.prodSpecId = prodSpecId;
	}

	public Integer getProdSpecPrice() {
		return prodSpecPrice;
	}

	public void setProdSpecPrice(Integer prodSpecPrice) {
		this.prodSpecPrice = prodSpecPrice;
	}

	public String getProdSpecName() {
		return prodSpecName;
	}

	public void setProdSpecName(String prodSpecName) {
		this.prodSpecName = prodSpecName;
	}

   
}
