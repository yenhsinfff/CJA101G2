package com.lutu.shop_cancellation.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "shop_cancellation")
public class ShopCancellationVO implements Serializable {
	
	@Id
	@Column(name = "shop_cancel_id")
	private Integer shopCancelId; // 商城取消編號
	
	@OneToOne
	@Column(name = "shop_order_id")
	private Integer shopOrderId; // 商城訂單編號
	
	@Column(name = "shop_cancel_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime shopCancelDate; // 取消日期
	
	@Column(name = "shop_cancel_reason")
	private String shopCancelReason; // 取消理由
	
	@Column(name = "shop_cancel_status")
	private Byte shopCancelStatus; // 取消狀態
	
	@Column(name = "return_method")
	private Byte returnMethod; // 退款方式

	public ShopCancellationVO() {

	}

	public ShopCancellationVO(Integer shopCancelId, Integer shopOrderId, LocalDateTime shopCancelDate,
			String shopCancelReason, byte shopCancelStatus, byte returnMethod) {
		super();
		this.shopCancelId = shopCancelId;
		this.shopOrderId = shopOrderId;
		this.shopCancelDate = shopCancelDate;
		this.shopCancelReason = shopCancelReason;
		this.shopCancelStatus = shopCancelStatus;
		this.returnMethod = returnMethod;
	}
	
	
	// --- Getters and Setters ---
	public Integer getShopCancelId() {
		return shopCancelId;
	}

	public void setShopCancelId(Integer shopCancelId) {
		this.shopCancelId = shopCancelId;
	}

	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
	}

	public LocalDateTime getShopCancelDate() {
		return shopCancelDate;
	}

	public void setShopCancelDate(LocalDateTime shopCancelDate) {
		this.shopCancelDate = shopCancelDate;
	}

	public String getShopCancelReason() {
		return shopCancelReason;
	}

	public void setShopCancelReason(String shopCancelReason) {
		this.shopCancelReason = shopCancelReason;
	}

	public byte getShopCancelStatus() {
		return shopCancelStatus;
	}

	public void setShopCancelStatus(byte shopCancelStatus) {
		this.shopCancelStatus = shopCancelStatus;
	}

	public byte getReturnMethod() {
		return returnMethod;
	}

	public void setReturnMethod(byte returnMethod) {
		this.returnMethod = returnMethod;
	}

}
