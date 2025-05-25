package com.lutu.user_discount.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserDiscountVO implements Serializable{

	private Integer memId; //露營者編號
	private String discountCodeId; //折價券編號
	private byte discountCodeType; //折扣訂單類型 0營地 1商城
	private LocalDateTime usedAt; //使用時間
	
	public UserDiscountVO() {
		super();
	}

	public UserDiscountVO(Integer memId, String discountCodeId, byte discountCodeType, LocalDateTime usedAt) {
		super();
		this.memId = memId;
		this.discountCodeId = discountCodeId;
		this.discountCodeType = discountCodeType;
		this.usedAt = usedAt;
	}

	
	// --- Getters and Setters ---
	
	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public byte getDiscountCodeType() {
		return discountCodeType;
	}

	public void setDiscountCodeType(byte discountCodeType) {
		this.discountCodeType = discountCodeType;
	}

	public LocalDateTime getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(LocalDateTime usedAt) {
		this.usedAt = usedAt;
	}
	
	
}
