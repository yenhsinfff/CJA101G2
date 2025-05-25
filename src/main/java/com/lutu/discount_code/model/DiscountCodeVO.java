package com.lutu.discount_code.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DiscountCodeVO implements Serializable{
	
	private String discountCodeId; //折價券編號
	private String discountCode; //折價券名稱
	private Integer ownerId; //營地主編號
	private Integer adminId; //管理員編號
	private byte discountType; //折扣類型 0數值 1%數
	private BigDecimal discountValue; //折扣值
	private String discountExplain; //折扣說明
	private BigDecimal minOrderAmount; //最低訂單金額
	private LocalDateTime startDate; //生效日期
	private LocalDateTime endDate; //失效日期
	private LocalDateTime created; //建立時間
	private LocalDateTime update; //更新時間
	
	public DiscountCodeVO() {
		super();
	}

	public DiscountCodeVO(String discountCodeId, String discountCode, Integer ownerId, Integer adminId,
			byte discountType, BigDecimal discountValue, String discountExplain, BigDecimal minOrderAmount,
			LocalDateTime startDate, LocalDateTime endDate, LocalDateTime created, LocalDateTime update) {
		super();
		this.discountCodeId = discountCodeId;
		this.discountCode = discountCode;
		this.ownerId = ownerId;
		this.adminId = adminId;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.discountExplain = discountExplain;
		this.minOrderAmount = minOrderAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.created = created;
		this.update = update;
	}

	
	// --- Getters and Setters ---
	
	public String getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public byte getDiscountType() {
		return discountType;
	}

	public void setDiscountType(byte discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public String getDiscountExplain() {
		return discountExplain;
	}

	public void setDiscountExplain(String discountExplain) {
		this.discountExplain = discountExplain;
	}

	public BigDecimal getMinOrderAmount() {
		return minOrderAmount;
	}

	public void setMinOrderAmount(BigDecimal minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdate() {
		return update;
	}

	public void setUpdate(LocalDateTime update) {
		this.update = update;
	}

}
