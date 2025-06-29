package com.lutu.campsitetype.model;

public class CampsiteTypeDTO_Pic {

	private Integer campsiteTypeId;
	private Integer campId;
	private String campsitePic1; // Base64 字串（或 URL）
	private String campsitePic2;
	private String campsitePic3;
	private String campsitePic4;

	// Constructor

	public CampsiteTypeDTO_Pic() {

	}

	public CampsiteTypeDTO_Pic(Integer campsiteTypeId, Integer campId, String campsitePic1, String campsitePic2,
			String campsitePic3, String campsitePic4) {
		super();
		this.campsiteTypeId = campsiteTypeId;
		this.campId = campId;
		this.campsitePic1 = campsitePic1;
		this.campsitePic2 = campsitePic2;
		this.campsitePic3 = campsitePic3;
		this.campsitePic4 = campsitePic4;
	}

	// Getters and Setters
	
	public Integer getCampsiteTypeId() {
		return campsiteTypeId;
	}

	public void setCampsiteTypeId(Integer campsiteTypeId) {
		this.campsiteTypeId = campsiteTypeId;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public String getCampsitePic1() {
		return campsitePic1;
	}

	public void setCampsitePic1(String campsitePic1) {
		this.campsitePic1 = campsitePic1;
	}

	public String getCampsitePic2() {
		return campsitePic2;
	}

	public void setCampsitePic2(String campsitePic2) {
		this.campsitePic2 = campsitePic2;
	}

	public String getCampsitePic3() {
		return campsitePic3;
	}

	public void setCampsitePic3(String campsitePic3) {
		this.campsitePic3 = campsitePic3;
	}

	public String getCampsitePic4() {
		return campsitePic4;
	}

	public void setCampsitePic4(String campsitePic4) {
		this.campsitePic4 = campsitePic4;
	}



}
