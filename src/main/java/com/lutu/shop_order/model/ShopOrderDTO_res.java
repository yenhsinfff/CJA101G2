package com.lutu.shop_order.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShopOrderDTO_res {

	@NotNull(message = "訂單編號必填")
	private Integer shopOrderId; // 商品訂單編號

	// private MemberVO memId;
	private Integer memId; // 露營者編號

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Taipei")
	private LocalDateTime shopOrderDate;

	private Byte shopOrderShipment; // 出貨方式

	private Integer shopOrderShipFee; // 運費

	private Integer beforeDiscountAmount; // 折扣前金額

	private String discountCodeId; // 折價券編號

	private Integer discountAmount; // 折價金額

	private Integer afterDiscountAmount; // 實付金額

	private Byte shopOrderPayment; // 付款方式

	@Size(max = 40, message = "姓名不得超過40字")
	private String orderName; // 訂購人姓名

	@Email(message = "請輸入有效的電子郵件格式")
	private String orderEmail; // 訂購人郵件

	@Pattern(regexp = "^09\\d{8}$", message = "請輸入手機號碼正確格式")
	private String orderPhone; // 訂購人手機

	@Size(max = 60, message = "地址不得超過60字")
	private String orderShippingAddress; // 訂購人地址

	@Size(max = 30, message = "備註不得超過30字")
	private String shopOrderNote; // 訂單備註

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Taipei")
	private LocalDateTime shopOrderShipDate; // 出貨日期

	private Byte shopOrderStatus; // 訂單狀態

	private Byte shopReturnApply; // 退貨申請

	public ShopOrderDTO_res() {
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

	
	public Byte getShopOrderShipment() {
		return shopOrderShipment;
	}

	public void setShopOrderShipment(Byte shopOrderShipment) {
		this.shopOrderShipment = shopOrderShipment;
	}

	public String getShopOrderShipmentStr() {
		String result = switch (shopOrderShipment) {
		case 1 -> "賣家宅配";
		case 2 -> "超商取貨付款";
		default -> "尚未選取";
		};
		return result;

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

	public Byte getShopOrderPayment() {
		return shopOrderPayment;
	}

	public void setShopOrderPayment(Byte shopOrderPayment) {
		this.shopOrderPayment = shopOrderPayment;
	}

	public String getShopOrderPaymentStr() {
		String result = switch (shopOrderPayment) {
		case 1 -> "LINEPAY";
		case 2 -> "宅配取貨付款";
		case 3 -> "超商取貨付款";
		default -> "LINEPAY";
		};
		return result;

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

	public Byte getShopOrderStatus() {
		return shopOrderStatus;
	}

	public void setShopOrderStatus(Byte shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}

	public String getShopOrderStatusStr() {
		String result = switch (shopOrderStatus) {
		case 0 -> "等待賣家確認中";
		case 1 -> "準備出貨中";
		case 2 -> "已出貨";
		case 3 -> "已取貨，完成訂單";
		case 4 -> "未取貨，退回賣家";
		case 5 -> "已取消";
		case 6 -> "付款失敗";
		case 7 -> "付款成功，待賣家確認";
		default -> "等待賣家確認中";
		};
		return result;

	}

	public Byte getShopReturnApply() {
		return shopReturnApply;
	}

	public void setShopReturnApply(Byte shopReturnApply) {
		this.shopReturnApply = shopReturnApply;
	}

	public String getShopReturnApplyStr() {
		String result = switch (shopReturnApply) {
		case 0 -> "未申請退貨";
		case 1 -> "申請退貨";
		case 2 -> "退貨成功";
		case 3 -> "退貨失敗";
		default -> "未申請退貨";
		};
		return result;
	}

}
