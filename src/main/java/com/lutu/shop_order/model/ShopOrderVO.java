package com.lutu.shop_order.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "shop_order")
public class ShopOrderVO implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name = "shop_order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shopOrderId; // 商品訂單編號
	
//	@ManyToOne
	@Column(name = "mem_id")
	private Integer memId; // 露營者編號
	
	@Column(name = "shop_order_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime shopOrderDate; // 訂單日期
	
	@Column(name = "shop_order_shipment")
	private byte shopOrderShipment; // 出貨方式
	
	@Column(name = "shop_order_ship_fee")
	private Integer shopOrderShipFee; // 運費
	
	@Column(name = "before_discount_amount")
	private Integer beforeDiscountAmount; // 折價前總金額
	
//	@ManyToOne
	@Column(name = "discount_code_id")
	@Size(min = 6, max = 6, message = "折扣碼為6位數")
	private String discountCodeId; // 折價券編號
	
	@Column(name = "discount_amount")
	private Integer discountAmount; // 折價金額
	
	@Column(name = "after_discount_amount")
	private Integer afterDiscountAmount; // 實付金額
	
	@Column(name = "shop_order_payment")
	private byte shopOrderPayment; // 付款方式
	
	@Column(name = "order_name")
	@Size(max = 40, message = "姓名不得超過40字")
	private String orderName; // 訂購人姓名
	
	@Column(name = "order_email")
	private String orderEmail; // 訂購人郵件
	
	@Column(name = "order_phone")
	@Pattern(regexp = "^09\\d{8}$", message = "請輸入手機號碼正確格式")
	private String orderPhone; // 訂購人手機
	
	@Column(name = "order_shipping_address")
	@Size(max = 60, message = "地址不得超過60字")
	private String orderShippingAddress; // 訂購人地址
	
	@Column(name = "shop_order_note")
	@Size(max = 30, message = "備註不得超過30字")
	private String shopOrderNote; // shop_order_note
	
	@Column(name = "shop_order_ship_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime shopOrderShipDate; // 出貨日期
	
	@Column(name = "shop_order_status")
	private byte shopOrderStatus; // 訂單狀態
	
	@Column(name = "shop_return_apply")
	private byte shopReturnApply; // 退貨申請

	public ShopOrderVO() {

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
