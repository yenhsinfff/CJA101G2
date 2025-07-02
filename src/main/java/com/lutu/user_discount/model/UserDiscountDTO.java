package com.lutu.user_discount.model;

import java.time.LocalDateTime;


public class UserDiscountDTO {

	private Integer memId; // 露營者編號
	private String discountCode; // 折價券編號
	private byte discountCodeType; // 折扣訂單類型 0營地 1商城
	private LocalDateTime usedAt; // 使用時間
	public Integer getMemId() {
		return memId;
	}
	
	
	public UserDiscountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserDiscountDTO(Integer memId, String discountCode, byte discountCodeType, LocalDateTime usedAt) {
		super();
		this.memId = memId;
		this.discountCode = discountCode;
		this.discountCodeType = discountCodeType;
		this.usedAt = usedAt;
	}


	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
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
