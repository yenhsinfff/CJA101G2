package com.lutu.discount_code.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DiscountCodeDTO_update {

	private String discountCodeId; // 折價券編號

	@NotEmpty(message = "折價券名稱: 請勿空白")
	private String discountCode; // 折價券名稱

	private Integer adminId; // 管理員編號

	@NotNull(message = "折扣類型: 請勿空白")
	@Min(value = 0, message = "折扣類型: 只能是 0（數值）或 1（%數）")
	@Max(value = 1, message = "折扣類型: 只能是 0（數值）或 1（%數）")
	private byte discountType; // 折扣類型 0數值 1%數

	@NotNull(message = "折扣值: 請勿空白")
	private BigDecimal discountValue; // 折扣值

	@NotNull(message = "折扣說明: 請勿空白")
	private String discountExplain; // 折扣說明

	@NotNull(message = "最低訂單金額: 請勿空白")
	private BigDecimal minOrderAmount; // 最低訂單金額

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate; // 生效日期

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate; // 失效日期

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private LocalDateTime created; // 建立時間

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime update; //更新時間

	public DiscountCodeDTO_update() {
	}


	public DiscountCodeDTO_update(String discountCodeId, @NotEmpty(message = "折價券名稱: 請勿空白") String discountCode,
			Integer adminId,
			@NotNull(message = "折扣類型: 請勿空白") @Min(value = 0, message = "折扣類型: 只能是 0（數值）或 1（%數）") @Max(value = 1, message = "折扣類型: 只能是 0（數值）或 1（%數）") byte discountType,
			@NotNull(message = "折扣值: 請勿空白") BigDecimal discountValue,
			@NotNull(message = "折扣說明: 請勿空白") String discountExplain,
			@NotNull(message = "最低訂單金額: 請勿空白") BigDecimal minOrderAmount, LocalDateTime startDate,
			LocalDateTime endDate, LocalDateTime update) {
		super();
		this.discountCodeId = discountCodeId;
		this.discountCode = discountCode;
		this.adminId = adminId;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.discountExplain = discountExplain;
		this.minOrderAmount = minOrderAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.update = update;
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

//	public LocalDateTime getCreated() {
//		return created;
//	}
//
//	public void setCreated(LocalDateTime created) {
//		this.created = created;
//	}

//	public LocalDateTime getUpdate() {
//		return update;
//	}
//
//
//
//
//	public void setUpdate(LocalDateTime update) {
//		this.update = update;
//	}

}