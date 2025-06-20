package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.lutu.prodColorList.model.ProdColorListVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;


@Entity
@Table(name = "color_list")
public class ColorListVO implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //AI
	@Column(name = "color_id", updatable = false)
	private Integer colorId;      // 顏色編號PK
	
	@Column(name = "color_name")
    private String colorName;     // 顏色名稱
	
//	@OneToMany(mappedBy = "colorListVO", cascade = CascadeType.ALL)
	@OrderBy("prodColorId asc")
	private Set<ProdColorListVO> prodColors;
  
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

	public Set<ProdColorListVO> getProdColors() {
		return prodColors;
	}

	public void setProdColors(Set<ProdColorListVO> prodColors) {
		this.prodColors = prodColors;
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
