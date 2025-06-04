package com.lutu.campsitetype.model;

import java.io.Serializable;

public class CampsiteTypeVO implements Serializable{
	private Integer campsiteTypeId; //營地房型編號
	private Integer campId; //營地編號
	private String campsiteName; //營地房型名稱
	private Integer campsitePeople; //可入住人數
	private Byte campsiteNum; //房間數量
	private Integer campsitePrice; //房間價格
	private byte[] campsitePic1; //房間照片1
	private byte[] campsitePic2; //房間照片2
	private byte[] campsitePic3; //房間照片3
	private byte[] campsitePic4; //房間照片4
	


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

	public Byte getCampsiteNum() {
		return campsiteNum;
	}

	public void setCampsiteNum(Byte campsiteNum) {
		this.campsiteNum = campsiteNum;
	}

	public Integer getCampsitePrice() {
		return campsitePrice;
	}

	public void setCampsitePrice(Integer campsitePrice) {
		this.campsitePrice = campsitePrice;
	}

	public byte[] getCampsitePic1() {
		return campsitePic1;
	}

	public void setCampsitePic1(byte[] campsitePic1) {
		this.campsitePic1 = campsitePic1;
	}

	public byte[] getCampsitePic2() {
		return campsitePic2;
	}

	public void setCampsitePic2(byte[] campsitePic2) {
		this.campsitePic2 = campsitePic2;
	}

	public byte[] getCampsitePic3() {
		return campsitePic3;
	}

	public void setCampsitePic3(byte[] campsitePic3) {
		this.campsitePic3 = campsitePic3;
	}

	public byte[] getCampsitePic4() {
		return campsitePic4;
	}

	public void setCampsitePic4(byte[] campsitePic4) {
		this.campsitePic4 = campsitePic4;
	}
	

}
