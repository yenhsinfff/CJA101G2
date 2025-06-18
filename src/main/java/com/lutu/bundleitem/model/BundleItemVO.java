package com.lutu.bundleitem.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bundle_item")
public class BundleItemVO implements Serializable {

	@Id
	@Column(name = "bundle_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bundleId; // 加購項目編號

	@Column(name = "camp_id", nullable = false)
	private Integer campId; // 營地編號

	@Column(name = "bundle_name", nullable = false)
	private String bundleName; // 加購項目名稱

	@Column(name = "bundle_add_date", nullable = false)
	private Date bundleAddDate; // 加入日期

	@Column(name = "bundle_price", nullable = false)
	private Integer bundlePrice; // 加購項目價格

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
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

	@Override
	public String toString() {
		return "BundleItemVO [bundleId=" + bundleId + ", CampId=" + CampId + ", bundleName=" + bundleName
				+ ", bundleAddDate=" + bundleAddDate + ", bundlePrice=" + bundlePrice + "]";
	}

}
