package com.lutu.bundleitem.model;

import java.io.Serializable;
import java.sql.Date;

public class BundleItemVO implements Serializable{

	private Integer bundleId; //加購項目編號
	private Integer CampId; //營地編號
	private String bundleName; //加購項目名稱
	private Date bundleAddDate; //加入日期
	private Integer bundlePrice; //加購項目價格
	
	
	public Integer getBundleId() {
		return bundleId;
	}
	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}
	public Integer getCampId() {
		return CampId;
	}
	public void setCampId(Integer campId) {
		CampId = campId;
	}
	public String getBundleName() {
		return bundleName;
	}
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}
	public Date getBundleAddDate() {
		return bundleAddDate;
	}
	public void setBundleAddDate(Date bundleAddDate) {
		this.bundleAddDate = bundleAddDate;
	}
	public Integer getBundlePrice() {
		return bundlePrice;
	}
	public void setBundlePrice(Integer bundlePrice) {
		this.bundlePrice = bundlePrice;
	}
	
	
}
