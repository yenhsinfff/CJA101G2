package com.lutu.user_discount.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//回傳發送會員折價券(傳出)
public class UserDiscountDTO {

	private Integer memId; // 露營者編號，前端不顯示
	private String discountCodeId; // 折價券編號
	private String discountCode; // 折價券名稱
	private BigDecimal minOrderAmount; //最低訂單金額
	private byte discountType; //折扣類型
	private BigDecimal discountValue; //折扣值
	private LocalDateTime startDate; //生效日期
	private LocalDateTime endDate; //失效日期
	private LocalDateTime usedAt; // 使用時間
	
	public UserDiscountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDiscountDTO(Integer memId, String discountCodeId, String discountCode, BigDecimal minOrderAmount,
			byte discountType, BigDecimal discountValue, LocalDateTime startDate, LocalDateTime endDate,
			LocalDateTime usedAt) {
		super();
		this.memId = memId;
		this.discountCodeId = discountCodeId;
		this.discountCode = discountCode;
		this.minOrderAmount = minOrderAmount;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public BigDecimal getMinOrderAmount() {
		return minOrderAmount;
	}

	public void setMinOrderAmount(BigDecimal minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
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

	public LocalDateTime getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(LocalDateTime usedAt) {
		this.usedAt = usedAt;
	}

	

}
