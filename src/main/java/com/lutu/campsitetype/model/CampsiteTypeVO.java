package com.lutu.campsitetype.model;

import java.io.Serializable;

public class CampsiteTypeVO implements Serializable{
	private Integer campsiteId; //營地房間編號
	private Integer campId; //營地編號
	private String campsiteName; //營地房型名稱
	private Integer camsitePeople; //可入住人數
	private byte camsiteNum; //房間數量
	private Integer camsitePrice; //房間價格
	private byte[] camsitePic1; //房間照片1
	private byte[] camsitePic2; //房間照片2
	private byte[] camsitePic3; //房間照片3
	private byte[] camsitePic4; //房間照片4
	
	
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
	public String getCampsiteName() {
		return campsiteName;
	}
	public void setCampsiteName(String campsiteName) {
		this.campsiteName = campsiteName;
	}
	public Integer getCamsitePeople() {
		return camsitePeople;
	}
	public void setCamsitePeople(Integer camsitePeople) {
		this.camsitePeople = camsitePeople;
	}
	public byte getCamsiteNum() {
		return camsiteNum;
	}
	public void setCamsiteNum(byte camsiteNum) {
		this.camsiteNum = camsiteNum;
	}
	public Integer getCamsitePrice() {
		return camsitePrice;
	}
	public void setCamsitePrice(Integer camsitePrice) {
		this.camsitePrice = camsitePrice;
	}
	public byte[] getCamsitePic1() {
		return camsitePic1;
	}
	public void setCamsitePic1(byte[] camsitePic1) {
		this.camsitePic1 = camsitePic1;
	}
	public byte[] getCamsitePic2() {
		return camsitePic2;
	}
	public void setCamsitePic2(byte[] camsitePic2) {
		this.camsitePic2 = camsitePic2;
	}
	public byte[] getCamsitePic3() {
		return camsitePic3;
	}
	public void setCamsitePic3(byte[] camsitePic3) {
		this.camsitePic3 = camsitePic3;
	}
	public byte[] getCamsitePic4() {
		return camsitePic4;
	}
	public void setCamsitePic4(byte[] camsitePic4) {
		this.camsitePic4 = camsitePic4;
	}
	
	
	
}
