package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;

public class SpecListVO implements Serializable{
	
	private Integer specId;       // 規格編號PK
    private String specName;      // 規格名稱 每項商品皆有規格(單一規格)
    
	public SpecListVO() {
		super();
	}

	public SpecListVO(Integer specId, String specName) {
		super();
		this.specId = specId;
		this.specName = specName;
	}

	public Integer getSpecId() {
		return specId;
	}

	public void setSpecId(Integer specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(specId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpecListVO other = (SpecListVO) obj;
		return Objects.equals(specId, other.specId);
	}

	@Override
	public String toString() {
		return "SpecList [規格編號=" + specId + ", 規格名稱=" + specName + "]";
	}
    
    
}
