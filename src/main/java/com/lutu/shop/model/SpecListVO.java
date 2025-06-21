package com.lutu.shop.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.lutu.prodSpecList.model.ProdSpecListVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "spec_list")
public class SpecListVO implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //AI
	@Column(name = "spec_id", updatable = false)
	private Integer specId;       // 規格編號PK
    
	@Column(name = "spec_name")
	private String specName;      // 規格名稱 每項商品皆有規格(單一規格)
	
	@OneToMany(mappedBy = "specListVO", cascade = CascadeType.ALL)
	@OrderBy("prodSpecId asc")
	private Set<ProdSpecListVO> prodSpecs;
    
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

	public Set<ProdSpecListVO> getProdSpecs() {
		return prodSpecs;
	}

	public void setProdSpecs(Set<ProdSpecListVO> prodSpecs) {
		this.prodSpecs = prodSpecs;
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
