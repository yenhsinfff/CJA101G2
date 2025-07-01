package com.lutu.campsite_available.model;

public class CampsiteTypeAvailableDTO {
    private Integer campId;
    private Integer campsiteTypeId;
    private String campsiteName;
    private Integer campsitePeople;
    private Integer campsiteNum;
    private Integer campsitePrice;
    private Integer remaining; // 新增剩餘房量

    // 建構子
    public CampsiteTypeAvailableDTO(Integer campId, Integer campsiteTypeId, String campsiteName, Integer campsitePeople,
                                   Integer campsiteNum, Integer campsitePrice, Integer remaining) {
        this.campId = campId;
        this.campsiteTypeId = campsiteTypeId;
        this.campsiteName = campsiteName;
        this.campsitePeople = campsitePeople;
        this.campsiteNum = campsiteNum;
        this.campsitePrice = campsitePrice;
        this.remaining = remaining;
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

	public String getCampsiteName() {
		return campsiteName;
	}

	public void setCampsiteName(String campsiteName) {
		this.campsiteName = campsiteName;
	}

	public Integer getCampsitePeople() {
		return campsitePeople;
	}

	public void setCampsitePeople(Integer campsitePeople) {
		this.campsitePeople = campsitePeople;
	}

	public Integer getCampsiteNum() {
		return campsiteNum;
	}

	public void setCampsiteNum(Integer campsiteNum) {
		this.campsiteNum = campsiteNum;
	}

	public Integer getCampsitePrice() {
		return campsitePrice;
	}

	public void setCampsitePrice(Integer campsitePrice) {
		this.campsitePrice = campsitePrice;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}

    // Getter/Setter ...
}

