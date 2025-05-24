package com.lutu.shop_order.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ShopOrderVO implements Serializable {
	private Integer shopOrderId; // 商品訂單編號
	private Integer memId; // 露營者編號
	private LocalDateTime shopOrderDate; // 訂單日期
	private byte shopOrderShipment; // 出貨方式
	private Integer shopOrderShipFee; // 運費
	private Integer beforeDiscountAmount; // 折價前總金額
	private String discountCodeId; // 折價券編號
	private Integer discountAmount; // 折價金額
	private Integer afterDiscountAmount; // 實付金額
	private byte shopOrderPayment; // 付款方式
	private String orderName; // 訂購人姓名
	private String orderEmail; // 訂購人郵件
	private String orderPhone; // 訂購人手機
	private String orderShippingAddress; // 訂購人地址
	private String shopOrderNote; // shop_order_note
	private LocalDateTime shopOrderShipDate; // 出貨日期
	private byte shopOrderStatus; // 訂單狀態
	private byte shopReturnApply; // 退貨申請

	public ShopOrderVO() {

	}

	public ShopOrderVO(Integer shopOrderId, Integer memId, LocalDateTime shopOrderDate, byte shopOrderShipment,
			Integer shopOrderShipFee, Integer beforeDiscountAmount, String discountCodeId, Integer discountAmount,
			Integer afterDiscountAmount, byte shopOrderPayment, String orderName, String orderEmail, String orderPhone,
			String orderShippingAddress, String shopOrderNote, LocalDateTime shopOrderShipDate, byte shopOrderStatus,
			byte shopReturnApply) {
		super();
		this.shopOrderId = shopOrderId;
		this.memId = memId;
		this.shopOrderDate = shopOrderDate;
		this.shopOrderShipment = shopOrderShipment;
		this.shopOrderShipFee = shopOrderShipFee;
		this.beforeDiscountAmount = beforeDiscountAmount;
		this.discountCodeId = discountCodeId;
		this.discountAmount = discountAmount;
		this.afterDiscountAmount = afterDiscountAmount;
		this.shopOrderPayment = shopOrderPayment;
		this.orderName = orderName;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
		this.orderShippingAddress = orderShippingAddress;
		this.shopOrderNote = shopOrderNote;
		this.shopOrderShipDate = shopOrderShipDate;
		this.shopOrderStatus = shopOrderStatus;
		this.shopReturnApply = shopReturnApply;
	}



	// --- Getters and Setters ---

	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public LocalDateTime getShopOrderDate() {
		return shopOrderDate;
	}

	public void setShopOrderDate(LocalDateTime shopOrderDate) {
		this.shopOrderDate = shopOrderDate;
	}

	public byte getShopOrderShipment() {
		return shopOrderShipment;
	}

	public void setShopOrderShipment(byte shopOrderShipment) {
		this.shopOrderShipment = shopOrderShipment;
	}

	public Integer getShopOrderShipFee() {
		return shopOrderShipFee;
	}

	public void setShopOrderShipFee(Integer shopOrderShipFee) {
		this.shopOrderShipFee = shopOrderShipFee;
	}

	public Integer getBeforeDiscountAmount() {
		return beforeDiscountAmount;
	}

	public void setBeforeDiscountAmount(Integer beforeDiscountAmount) {
		this.beforeDiscountAmount = beforeDiscountAmount;
	}

	public String getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public Integer getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getAfterDiscountAmount() {
		return afterDiscountAmount;
	}

	public void setAfterDiscountAmount(Integer afterDiscountAmount) {
		this.afterDiscountAmount = afterDiscountAmount;
	}

	public byte getShopOrderPayment() {
		return shopOrderPayment;
	}

	public void setShopOrderPayment(byte shopOrderPayment) {
		this.shopOrderPayment = shopOrderPayment;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderEmail() {
		return orderEmail;
	}

	public void setOrderEmail(String orderEmail) {
		this.orderEmail = orderEmail;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderShippingAddress() {
		return orderShippingAddress;
	}

	public void setOrderShippingAddress(String orderShippingAddress) {
		this.orderShippingAddress = orderShippingAddress;
	}

	public String getShopOrderNote() {
		return shopOrderNote;
	}

	public void setShopOrderNote(String shopOrderNote) {
		this.shopOrderNote = shopOrderNote;
	}

	public LocalDateTime getShopOrderShipDate() {
		return shopOrderShipDate;
	}

	public void setShopOrderShipDate(LocalDateTime shopOrderShipDate) {
		this.shopOrderShipDate = shopOrderShipDate;
	}

	public byte getShopOrderStatus() {
		return shopOrderStatus;
	}

	public void setShopOrderStatus(byte shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}

	public byte getShopReturnApply() {
		return shopReturnApply;
	}

	public void setShopReturnApply(byte shopReturnApply) {
		this.shopReturnApply = shopReturnApply;
	}

}
