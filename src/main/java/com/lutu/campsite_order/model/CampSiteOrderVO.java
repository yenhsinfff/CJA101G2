package com.lutu.campsite_order.model;

import java.sql.Date;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



//@NotEmpty 只能用在 String、Collection、Map 或 Array 上，不能用在基本型別（如 int、Integer、Timestamp 等）
//@NotNull：適用於任何非 primitive 欄位，尤其是 Integer、Date、Timestamp

@Entity  //要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "campsite_order") //代表這個class是對應到資料庫的實體table，目前對應的table是EMP2 
public class CampSiteOrderVO implements java.io.Serializable {
    private String campsiteOrderId; // 營地訂單編號
    private Integer memId; // 露營者編號
    private Integer campId; // 營地編號
    private String discountCodeId;// 折價券編號
    private Timestamp orderDate; // 訂單日期
    private byte campsiteOrderStatus; // 訂單狀態
    private Integer bundleAmount; // 加購項目總金額
    private Integer campsiteAmount; // 營地總金額
    private Integer befAmount; // 折價前總金額
    private Integer disAmount; // 折價金額
    private Integer aftAmount; // 實付金額
    private byte payMethod; // 支付方式 1: 信用卡 2: LINEPAY
    private Date checkIn; // 入住日期
    private Date checkOut; // 退營日期
    private Integer commentSatisfaction; // 評價滿意度
    private String commentContent; // 評價內文
    private Timestamp commentDate; // 評價日期
    
    @Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@Column(name = "campsite_order_id")  //@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
//	@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY(MySQL的AI), SEQUENCE, TABLE】
	public String getCampsiteOrderId() {
		return campsiteOrderId;
	}
	public void setCampsiteOrderId(String campsiteOrderId) {
		this.campsiteOrderId = campsiteOrderId;
	}
	
//	@ManyToOne //多筆訂單屬於同個會員(都是這個欄位對PK的關係)
	@NotNull(message="會員編號: 請勿空白")
	@Column(name = "mem_id") 
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	
	//@Pattern(regexp = "^[0-9]{4}$", message = "只能輸入4位數字")
	@Min(value = 1000, message = "營地編號需為4位數字")
	@Max(value = 9999, message = "營地編號需為4位數字")
	@NotNull(message="營地編號: 請勿空白")
	@Column(name = "camp_id") 
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	
	@Column(name = "discount_code_id") 
	public String getDiscountCodeId() {
		return discountCodeId;
	}
	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}
	
	@Column(name = "order_date") 
	@NotNull(message="訂單日期: 請勿空白")
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	
	@Column(name = "campsite_order_status")
	@NotNull(message="訂單狀態: 請勿空白")
	public byte getCampsiteOrderStatus() {
		return campsiteOrderStatus;
	}
	public void setCampsiteOrderStatus(byte campsiteOrderStatus) {
		this.campsiteOrderStatus = campsiteOrderStatus;
	}
	
	@Column(name = "bundle_amount")
	@NotNull(message="加購金額: 請勿空白")
	public Integer getBundleAmount() {
		return bundleAmount;
	}
	public void setBundleAmount(Integer bundleAmount) {
		this.bundleAmount = bundleAmount;
	}
	
	@Column(name = "camp_amount")
	@NotNull(message="營地訂單金額: 請勿空白")
	public Integer getCampsiteAmount() {
		return campsiteAmount;
	}
	public void setCampsiteAmount(Integer campsiteAmount) {
		this.campsiteAmount = campsiteAmount;
	}
	
	@Column(name = "bef_amount")
	@NotNull(message="營地訂單＿折價前總金額: 請勿空白")
	public Integer getBefAmount() {
		return befAmount;
	}
	public void setBefAmount(Integer befAmount) {
		this.befAmount = befAmount;
	}
	
	@Column(name = "dis_amount")
	@NotNull(message="營地訂單＿折價券金額: 請勿空白")
	public Integer getDisAmount() {
		return disAmount;
	}
	public void setDisAmount(Integer disAmount) {
		this.disAmount = disAmount;
	}
	
	@Column(name = "aft_amount")
	@NotNull(message="營地訂單＿折價後總金額: 請勿空白")
	public Integer getAftAmount() {
		return aftAmount;
	}
	public void setAftAmount(Integer aftAmount) {
		this.aftAmount = aftAmount;
	}
	
	@Column(name = "pay_method")
	@NotNull(message="付款方式: 請勿空白")
	public byte getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(byte payMethod) {
		this.payMethod = payMethod;
	}
	
	@Column(name = "check_in")
	@NotNull(message="入住日期: 請勿空白")
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	
	@Column(name = "check_out")
	@NotNull(message="離營日期: 請勿空白")
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	
	@Column(name = "comment_satisfaction")
	public Integer getCommentSatisfaction() {
		return commentSatisfaction;
	}
	public void setCommentSatisfaction(Integer commentSatisfaction) {
		this.commentSatisfaction = commentSatisfaction;
	}
	
	@Column(name = "comment_content")
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	@Column(name = "comment_date")
	public Timestamp getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

    
}
