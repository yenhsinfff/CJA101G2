package com.lutu.campsite.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "campsite") // 營地房間明細
public class CampsiteVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campsite_id", updatable = false)
	private Integer campsiteId; // 營地房間編號
	
	@Column(name = "camp_id", nullable = false)
	private Integer campId; // 營地編號
	
	@Column(name = "campsite_type_id", nullable = false)
	private Integer campsiteTypeId; // 營地房型編號
	
	@Column(name = "campsite_id_name", nullable = false)
	private String campsiteIdName; // 房間名稱
	
	@Column(name = "camper_name")
	private String camperName; // 露營者姓名

	public Integer getCampsiteId() {
		return campsiteId;
	}

	public void setCampsiteId(Integer campsiteId) {
		this.campsiteId = campsiteId;
	}

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
	@Override
	public String toString() {
		return "CampsiteVO [campsiteId=" + campsiteId + ", campId=" + campId + ", campsiteTypeId=" + campsiteTypeId
				+ ", campsiteIdName=" + campsiteIdName + ", camperName=" + camperName + "]";
	}
	

}
