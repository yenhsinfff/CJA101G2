package com.lutu.campsitetype.model;

public class CampsiteTypeDTO_Info {

    private Integer campsiteTypeId;
    private Integer campId;
    private String campsiteName;
    private Integer campsitePeople;
    private Integer campsiteNum;
    private Integer campsitePrice;


    // Constructor
  
    public CampsiteTypeDTO_Info() {

	}
    
    public CampsiteTypeDTO_Info(Integer campsiteTypeId, Integer campId, String campsiteName, Integer campsitePeople,
			Integer campsiteNum, Integer campsitePrice) {
		super();
		this.campsiteTypeId = campsiteTypeId;
		this.campId = campId;
		this.campsiteName = campsiteName;
		this.campsitePeople = campsitePeople;
		this.campsiteNum = campsiteNum;
		this.campsitePrice = campsitePrice;
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
    
    

    
	
}
