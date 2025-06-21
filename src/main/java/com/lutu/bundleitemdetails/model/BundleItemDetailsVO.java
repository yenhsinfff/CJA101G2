package com.lutu.bundleitemdetails.model;

import java.io.Serializable;

import com.lutu.bundleitem.model.BundleItemVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bundle_item_details")
public class BundleItemDetailsVO implements Serializable {

	@Id
	@Column(name = "bundle_details_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bundleDetailsId; // 加購項目明細編號

	@Column(name = "campsite_details_id")
	@NotNull(message="訂單明細編號: 請勿空白")
	private Integer campsiteDetailsId; // 訂單明細編號

//	@Column(name = "bundle_id")
//	@NotNull(message="加購項目編號: 請勿空白")
//	private Integer bundleId; // 加購項目編號

	@Column(name = "bundle_buy_num")
	@NotNull(message="購買數量: 請勿空白")
	private Integer bundleBuyNum; // 購買數量

	@Column(name = "bundle_buy_amount")
	@NotNull(message="加購項目總價: 請勿空白")
	private Integer bundleBuyAmount; // 加購項目總價

	
//=======================================================	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bundle_id", referencedColumnName = "bundle_id")
	private BundleItemVO bundleItem;
	
	public BundleItemVO getBundleItem() {
		return bundleItem;
	}

	public void setBundleItem(BundleItemVO bundleItem) {
		this.bundleItem = bundleItem;
	}
	
//=======================================================	

	public BundleItemDetailsVO(){
		
	}
	
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

//	public Integer getBundleId() {
//		return bundleId;
//	}

//	public void setBundleId(Integer bundleId) {
//		this.bundleId = bundleId;
//	}

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

//	@Override
//	public String toString() {
//		return "BundleItemDetailsVO [bundleDetailsId=" + bundleDetailsId + ", campsiteDetailsId=" + campsiteDetailsId
//				+ ", bundleId=" + bundleId + ", bundleBuyNum=" + bundleBuyNum + ", bundleBuyAmount=" + bundleBuyAmount
//				+ "]";
//	}
	

}
