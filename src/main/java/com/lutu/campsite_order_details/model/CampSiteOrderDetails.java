
package com.lutu.campsite_order_details.model;

public class CampSiteOrderDetails implements java.io.Serializable{
	
	private Integer campsiteDetailsId; //訂單明細編號
	private Integer campsiteOrderId; //營地訂單編號
	private Integer campsiteTypeId; //營地房型編號
	private Integer campsiteNum; //營地房型數量
	private Integer campsiteAmount; //營地房型總價
	
	public Integer getCampsiteDetailsId() {
		return campsiteDetailsId;
	}
	public void setCampsiteDetailsId(Integer campsiteDetailsId) {
		this.campsiteDetailsId = campsiteDetailsId;
	}
	public Integer getCampsiteOrderId() {
		return campsiteOrderId;
	}
	public void setCampsiteOrderId(Integer campsiteOrderId) {
		this.campsiteOrderId = campsiteOrderId;
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
	
	
}