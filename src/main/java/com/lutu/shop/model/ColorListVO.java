package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;

public class ColorListVO implements Serializable{
	
	private Integer colorId;      // 顏色編號PK
    private String colorName;     // 顏色名稱
    
	public ColorListVO() {
		super();
	}

	public ColorListVO(Integer colorId, String colorName) {
		super();
		this.colorId = colorId;
		this.colorName = colorName;
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(colorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorListVO other = (ColorListVO) obj;
		return Objects.equals(colorId, other.colorId);
	}

	@Override
	public String toString() {
		return "ColorList [顏色編號=" + colorId + ", 顏色名稱=" + colorName + "]";
	}
    
    
}
