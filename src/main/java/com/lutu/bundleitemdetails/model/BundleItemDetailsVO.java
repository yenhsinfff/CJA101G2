package com.lutu.bundleitemdetails.model;

import java.io.Serializable;

public class BundleItemDetailsVO implements Serializable{

	private Integer bundleDetailsId; //加購項目明細編號
	private Integer campsiteDetailsId; //訂單明細編號
	private Integer bundleId; //加購項目編號
	private Integer bundleBuyNum; //購買數量
	private Integer bundleBuyAmount; //加購項目總價
	
	public Integer getBundleDetailsId() {
		return bundleDetailsId;
	}
	public void setBundleDetailsId(Integer bundleDetailsId) {
		this.bundleDetailsId = bundleDetailsId;
	}
	public Integer getCampsiteDetailsId() {
		return campsiteDetailsId;
	}
	public void setCampsiteDetailsId(Integer campsiteDetailsId) {
		this.campsiteDetailsId = campsiteDetailsId;
	}
	public Integer getBundleId() {
		return bundleId;
	}
	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}
	public Integer getBundleBuyNum() {
		return bundleBuyNum;
	}
	public void setBundleBuyNum(Integer bundleBuyNum) {
		this.bundleBuyNum = bundleBuyNum;
	}
	public Integer getBundleBuyAmount() {
		return bundleBuyAmount;
	}
	public void setBundleBuyAmount(Integer bundleBuyAmount) {
		this.bundleBuyAmount = bundleBuyAmount;
	}
	
	
}
