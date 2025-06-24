package com.lutu.shop_order.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsDTO_insert_req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShopOrderDTO_insert {

	// private MemberVO memId;
	@NotNull(message = "請輸入露營者編號")
	private Integer memId; // 露營者編號

	@NotNull(message = "請選擇出貨方式")
	private Byte shopOrderShipment; // 出貨方式

	private Integer shopOrderShipFee = 60; // 運費，預設60

	private String discountCodeId; // 折價券編號

	@NotNull(message = "請確認付款方式")
	private Byte shopOrderPayment; // 付款方式

	@NotEmpty(message = "請輸入訂購人姓名")
	@Size(max = 40, message = "姓名不得超過40字")
	private String orderName; // 訂購人姓名

	@NotEmpty(message = "請輸入訂購人郵件")
	@Email(message = "請輸入有效的電子郵件格式")
	private String orderEmail; // 訂購人郵件

	@NotEmpty(message = "請輸入訂購人手機")
	@Pattern(regexp = "^09\\d{8}$", message = "請輸入手機號碼正確格式")
	private String orderPhone; // 訂購人手機

	@NotEmpty(message = "請確認寄送地址")
	@Size(max = 60, message = "地址不得超過60字")
	private String orderShippingAddress; // 訂購人地址

	@Size(max = 30, message = "備註不得超過30字")
	private String shopOrderNote; // 訂單備註

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime shopOrderShipDate; // 出貨日期

	private Byte shopOrderStatus; // 訂單狀態

	private Byte shopReturnApply; // 退貨申請

	private List<ShopOrderItemsDetailsDTO_insert_req> detailsDto; // 訂單明細

	public ShopOrderDTO_insert() {
	}

	// --- Getters and Setters ---

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public Byte getShopOrderShipment() {
		return shopOrderShipment;
	}

	public void setShopOrderShipment(Byte shopOrderShipment) {
		this.shopOrderShipment = shopOrderShipment;
	}

	public Integer getShopOrderShipFee() {
		return shopOrderShipFee;
	}

	public void setShopOrderShipFee(Integer shopOrderShipFee) {
		this.shopOrderShipFee = shopOrderShipFee;
	}

	
	public String getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public Byte getShopOrderPayment() {
		return shopOrderPayment;
	}

	public void setShopOrderPayment(Byte shopOrderPayment) {
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

	public Byte getShopOrderStatus() {
		return shopOrderStatus;
	}

	public void setShopOrderStatus(Byte shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}

	public Byte getShopReturnApply() {
		return shopReturnApply;
	}

	public void setShopReturnApply(Byte shopReturnApply) {
		this.shopReturnApply = shopReturnApply;
	}

	public List<ShopOrderItemsDetailsDTO_insert_req> getDetailsDto() {
		return detailsDto;
	}

	public void setDetailsDto(List<ShopOrderItemsDetailsDTO_insert_req> detailsDto) {
		this.detailsDto = detailsDto;
	}
	
	

}
