package com.lutu.campsitetype.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.*;


@Entity
@Table(name = "campsite_type")
public class CampsiteTypeVO implements Serializable{
	@Id
	@Column(name = "campsite_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer campsiteTypeId; //營地房型編號
	@Column(name = "camp_id")
	private Integer campId; //營地編號
	@Column(name = "campsite_name")
	private String campsiteName; //營地房型名稱
	@Column(name = "campsite_people")
	private Integer campsitePeople; //可入住人數
	@Column(name = "campsite_num")
	private Byte campsiteNum; //房間數量
	@Column(name = "campsite_price")
	private Integer campsitePrice; //房間價格
	@Column(name = "campsite_pic1")
	private byte[] campsitePic1; //房間照片1
	@Column(name = "campsite_pic2")
	private byte[] campsitePic2; //房間照片2
	@Column(name = "campsite_pic3")
	private byte[] campsitePic3; //房間照片3
	@Column(name = "campsite_pic4")
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
