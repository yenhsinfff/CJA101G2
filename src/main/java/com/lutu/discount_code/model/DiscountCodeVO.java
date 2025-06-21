package com.lutu.discount_code.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.lutu.owner.model.OwnerVO;
import com.lutu.administrator.model.AdministratorVO;


@Entity
@Table(name = "discount_code")
public class DiscountCodeVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id  
	@Column(name = "discount_code_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String discountCodeId; //折價券編號
	
	@Column(name = "discount_code")
	@NotEmpty(message="折價券名稱: 請勿空白")
	private String discountCode; //折價券名稱
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private OwnerVO ownerVO; //營地主編號
	
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private AdministratorVO administratorVO; //管理員編號
	
	@Column(name = "discount_type")
	@NotNull(message = "折扣類型: 請勿空白")
	@Min(value = 0, message = "折扣類型: 只能是 0（數值）或 1（%數）")
	@Max(value = 1, message = "折扣類型: 只能是 0（數值）或 1（%數）")
	private byte discountType; //折扣類型 0數值 1%數
	
	@Column(name = "discount_value")
	@NotNull(message = "折扣值: 請勿空白")
	private BigDecimal discountValue; //折扣值
	
	@Column(name = "discount_explain")
	@NotNull(message = "折扣說明: 請勿空白")
	private String discountExplain; //折扣說明
	
	@Column(name = "min_order_amount")
	@NotNull(message = "最低訂單金額: 請勿空白")
	private BigDecimal minOrderAmount; //最低訂單金額
	
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate; //生效日期
	
	@Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate; //失效日期
	
	@Column(name = "created")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created; //建立時間
	
	@Column(name = "updated")
	private LocalDateTime update; //更新時間
	
	
	
	
	public DiscountCodeVO() {
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

	public OwnerVO getOwnerVO() {
		return ownerVO;
	}

	public void setOwnerVO(OwnerVO ownerVO) {
		this.ownerVO = ownerVO;
	}

	public AdministratorVO getAdministratorVO() {
		return administratorVO;
	}

	public void setAdministratorVO(AdministratorVO administratorVO) {
		this.administratorVO = administratorVO;
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
