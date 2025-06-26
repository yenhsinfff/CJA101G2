package com.lutu.campsite.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "campsite") // 營地房間明細
public class CampsiteVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campsite_id", updatable = false)
	private Integer campsiteId; // 營地房間編號
	
	
	@Column(name = "campsite_id_name")
	@NotEmpty(message = "房間名稱: 請勿空白")
	private String campsiteIdName; // 房間名稱
	
	@Column(name = "camper_name")
	private String camperName; // 露營者姓名
	
	@Column(name="camp_id", insertable = false, updatable = false)
	private Integer campId;
	
	@Column(name="campsite_type_id", insertable = false, updatable = false)
	private Integer campsiteTypeId;
	
	
	
	//=======================Association==================================		
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "camp_id", referencedColumnName = "camp_id"),
		@JoinColumn(name = "campsite_type_id", referencedColumnName = "campsite_type_id")   
	    })
	private CampsiteTypeVO campsiteType;
	
	public CampsiteTypeVO getCampsiteType() {
		return campsiteType;
	}

	public void setCampsiteType(CampsiteTypeVO campsiteType) {
		this.campsiteType = campsiteType;
	}

	//===================================================================	
	
	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public Integer getCampsiteTypeId() {
		return campsiteTypeId;
	}

	public void setCampsiteTypeId(Integer campsiteTypeId) {
		this.campsiteTypeId = campsiteTypeId;
	}

	public CampsiteVO() {
		
	}
	
	public Integer getCampsiteId() {
		return campsiteId;
	}

	public void setCampsiteId(Integer campsiteId) {
		this.campsiteId = campsiteId;
	}


	public String getCampsiteIdName() {
		return campsiteIdName;
	}

	public void setCampsiteIdName(String campsiteIdName) {
		this.campsiteIdName = campsiteIdName;
	}

	public String getCamperName() {
		return camperName;
	}

	public void setCamperName(String camperName) {
		this.camperName = camperName;
	}

	//覆寫 toString() 後，可以顯示內容
//	@Override
//	public String toString() {
//		return "CampsiteVO [campsiteId=" + campsiteId + ", campsiteTypeId=" + (campsiteType != null ? campsiteType.getId() : "null") 
//				+ ", campsiteIdName=" + campsiteIdName + ", camperName=" + camperName + "]";
//	}

	
	


}
