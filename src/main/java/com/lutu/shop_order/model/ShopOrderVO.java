package com.lutu.shop_order.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.discount_code.model.DiscountCodeVO;
import com.lutu.member.model.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "shop_order")
public class ShopOrderVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "shop_order_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shopOrderId; // 商品訂單編號

	@ManyToOne
	@JoinColumn(name = "mem_id", updatable = false)
	private MemberVO memId;						// 露營者編號


	@Column(name = "shop_order_date", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime shopOrderDate; // 訂單日期

	@Column(name = "shop_order_shipment")
	private Byte shopOrderShipment; // 出貨方式

	@Column(name = "shop_order_ship_fee")
	private Integer shopOrderShipFee = 60; // 運費，預設60

	@Column(name = "before_discount_amount")
	private Integer beforeDiscountAmount; 		// 折價前總金額

	@ManyToOne
	@JoinColumn(name = "discount_code_id", nullable = true)
	private DiscountCodeVO discountCodeId;		// 折價券編號

	@Column(name = "discount_amount")
	private Integer discountAmount; // 折價金額

	@Column(name = "after_discount_amount")
	private Integer afterDiscountAmount; // 實付金額

	@Column(name = "shop_order_payment")
	private Byte shopOrderPayment; // 付款方式

	@Column(name = "order_name")
	@Size(max = 40, message = "姓名不得超過40字")
	private String orderName; // 訂購人姓名

	@Column(name = "order_email")
	@Email(message = "請輸入有效的電子郵件格式")
	private String orderEmail; // 訂購人郵件

	@Column(name = "order_phone")
	@Pattern(regexp = "^09\\d{8}$", message = "請輸入手機號碼正確格式")
	private String orderPhone; // 訂購人手機

	@Column(name = "order_shipping_address")
	@Size(max = 60, message = "地址不得超過60字")
	private String orderShippingAddress; // 訂購人地址

	@Column(name = "shop_order_note")
	@Size(max = 30, message = "備註不得超過30字")
	private String shopOrderNote; // 訂單備註

	@Column(name = "shop_order_ship_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime shopOrderShipDate; // 出貨日期

	@Column(name = "shop_order_status")
	private Byte shopOrderStatus; // 訂單狀態

	@Column(name = "shop_return_apply")
	private Byte shopReturnApply; // 退貨申請

	public ShopOrderVO() {

	}

	// --- Getters and Setters ---

	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
	}

	public MemberVO getMemId() {
		return memId;
	}

	public void setMemId(MemberVO memId) {
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

	public DiscountCodeVO getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(DiscountCodeVO discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	

	public Integer getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}

	public void setShopOrderShipment(Byte shopOrderShipment) {
		this.shopOrderShipment = shopOrderShipment;
	}

	public void setShopOrderPayment(Byte shopOrderPayment) {
		this.shopOrderPayment = shopOrderPayment;
	}

	public void setShopOrderStatus(Byte shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}

	public void setShopReturnApply(Byte shopReturnApply) {
		this.shopReturnApply = shopReturnApply;
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

	public Byte getShopOrderStatus() {
		return shopOrderStatus;
	}

	public void setShopOrderStatus(byte shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}

	public Byte getShopReturnApply() {
		return shopReturnApply;
	}

	public void setShopReturnApply(byte shopReturnApply) {
		this.shopReturnApply = shopReturnApply;
	}

}
