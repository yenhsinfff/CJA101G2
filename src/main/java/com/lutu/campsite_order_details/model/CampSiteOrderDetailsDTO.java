package com.lutu.campsite_order_details.model;

public class CampSiteOrderDetailsDTO {
    private Integer campsiteDetailsId;
    private Integer campsiteTypeId;
    private Integer campsiteNum;
    private Integer campsiteAmount;

    // 構造方法
    public CampSiteOrderDetailsDTO(CampSiteOrderDetailsVO entity) {
        this.campsiteDetailsId = entity.getCampsiteDetailsId();
        this.campsiteTypeId = entity.getCampsiteTypeId();
        this.campsiteNum = entity.getCampsiteNum();
        this.campsiteAmount = entity.getCampsiteAmount();
    }

	public Integer getCampsiteDetailsId() {
		return campsiteDetailsId;
	}

	public void setCampsiteDetailsId(Integer campsiteDetailsId) {
		this.campsiteDetailsId = campsiteDetailsId;
	}

	public Integer getCampsiteTypeId() {
		return campsiteTypeId;
	}

	public void setCampsiteTypeId(Integer campsiteTypeId) {
		this.campsiteTypeId = campsiteTypeId;
	}

	public Integer getCampsiteNum() {
		return campsiteNum;
	}

	public void setCampsiteNum(Integer campsiteNum) {
		this.campsiteNum = campsiteNum;
	}

	public Integer getCampsiteAmount() {
		return campsiteAmount;
	}

	public void setCampsiteAmount(Integer campsiteAmount) {
		this.campsiteAmount = campsiteAmount;
	}

    // Getters and Setters
}

