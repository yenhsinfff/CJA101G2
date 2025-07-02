package com.lutu.user_discount.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class UserDiscountDTO {

	private Integer memId; // 露營者編號
	private String discountCodeId; // 折價券編號
	private String discountCode; // 折價券名稱
	private byte discountCodeType; // 折扣訂單類型 0營地 1商城
	private LocalDateTime startDate; //生效日期
	private LocalDateTime endDate; //失效日期
	private BigDecimal minOrderAmount; //最低訂單金額
	private LocalDateTime usedAt; // 使用時間
	
	public UserDiscountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDiscountDTO(Integer memId, String discountCodeId, String discountCode, byte discountCodeType,
			LocalDateTime startDate, LocalDateTime endDate, BigDecimal minOrderAmount, LocalDateTime usedAt) {
		super();
		this.memId = memId;
		this.discountCodeId = discountCodeId;
		this.discountCode = discountCode;
		this.discountCodeType = discountCodeType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.minOrderAmount = minOrderAmount;
		this.usedAt = usedAt;
	}

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

	public BigDecimal getMinOrderAmount() {
		return minOrderAmount;
	}

	public void setMinOrderAmount(BigDecimal minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}

	public LocalDateTime getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(LocalDateTime usedAt) {
		this.usedAt = usedAt;
	}

}
