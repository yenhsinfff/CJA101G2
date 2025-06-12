package com.lutu.return_order.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "return_order")
public class ReturnOrrderVO implements Serializable {
	
	@Id
	@Column(name = "return_order_id")
	private Integer returnOrderId; // 退貨編號
	
	@OneToOne
	@Column(name = "shop_order_id")
	private Integer shopOrderId; // 訂單編號
	
	@Column(name = "shop_return_apply_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime shopReturnApplyDate; // 退貨申請時間
	
	@Column(name = "shop_return_reason")
	private String shopReturnReason; // 退貨理由
	
	@Column(name = "shop_return_confirm")
	private byte shopReturnConfirm; // 退貨審核
	
	@Column(name = "shop_return_message")
	private String shopReturnMessage; // 退貨指示訊息
	
	@Column(name = "shop_return_method")
	private byte shopReturnMethod; // 退款方式
	
	@Column(name = "shop_return_status")
	private byte shopReturnStatus; // 退貨狀態

	public ReturnOrrderVO() {
	}

	public ReturnOrrderVO(Integer returnOrderId, Integer shopOrderId, LocalDateTime shopReturnApplyDate,
			String shopReturnReason, byte shopReturnConfirm, String shopReturnMessage, byte shopReturnMethod,
			byte shopReturnStatus) {
		super();
		this.returnOrderId = returnOrderId;
		this.shopOrderId = shopOrderId;
		this.shopReturnApplyDate = shopReturnApplyDate;
		this.shopReturnReason = shopReturnReason;
		this.shopReturnConfirm = shopReturnConfirm;
		this.shopReturnMessage = shopReturnMessage;
		this.shopReturnMethod = shopReturnMethod;
		this.shopReturnStatus = shopReturnStatus;
	}

	
	// --- Getters and Setters ---
	public Integer getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(Integer returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
	}

	public LocalDateTime getShopReturnApplyDate() {
		return shopReturnApplyDate;
	}

	public void setShopReturnApplyDate(LocalDateTime shopReturnApplyDate) {
		this.shopReturnApplyDate = shopReturnApplyDate;
	}

	public String getShopReturnReason() {
		return shopReturnReason;
	}

	public void setShopReturnReason(String shopReturnReason) {
		this.shopReturnReason = shopReturnReason;
	}

	public byte getShopReturnConfirm() {
		return shopReturnConfirm;
	}

	public void setShopReturnConfirm(byte shopReturnConfirm) {
		this.shopReturnConfirm = shopReturnConfirm;
	}

	public String getShopReturnMessage() {
		return shopReturnMessage;
	}

	public void setShopReturnMessage(String shopReturnMessage) {
		this.shopReturnMessage = shopReturnMessage;
	}

	public byte getShopReturnMethod() {
		return shopReturnMethod;
	}

	public void setShopReturnMethod(byte shopReturnMethod) {
		this.shopReturnMethod = shopReturnMethod;
	}

	public byte getShopReturnStatus() {
		return shopReturnStatus;
	}

	public void setShopReturnStatus(byte shopReturnStatus) {
		this.shopReturnStatus = shopReturnStatus;
	}

}
