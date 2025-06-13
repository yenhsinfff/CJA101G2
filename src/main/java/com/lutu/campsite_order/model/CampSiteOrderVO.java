package com.lutu.campsite_order.model;

import java.sql.Date;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.lutu.camp.model.CampVO;
import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//@NotEmpty 只能用在 String、Collection、Map 或 Array 上，不能用在基本型別（如 int、Integer、Timestamp 等）
//@NotNull：適用於任何非 primitive 欄位，尤其是 Integer、Date、Timestamp

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "campsite_order") // 代表這個class是對應到資料庫的實體table，目前對應的table是EMP2
public class CampSiteOrderVO implements java.io.Serializable {
	@Id // @Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵
	@Column(name = "campsite_order_id") // @Column指這個屬性是對應到資料庫Table的哪一個欄位 //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
//	@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY(MySQL的AI), SEQUENCE, TABLE】
	private String campsiteOrderId; // 營地訂單編號

//	@ManyToOne //多筆訂單屬於同個會員(都是這個欄位對PK的關係)
	@NotNull(message = "會員編號: 請勿空白")
	@Column(name = "mem_id")
	private Integer memId; // 露營者編號
	// private Integer campId; // 營地編號

	@Column(name = "discount_code_id")
	private String discountCodeId;// 折價券編號

	@Column(name = "order_date")
	@NotNull(message = "訂單日期: 請勿空白")
	private Timestamp orderDate; // 訂單日期

	@Column(name = "campsite_order_status")
	@NotNull(message = "訂單狀態: 請勿空白")
	private byte campsiteOrderStatus; // 訂單狀態

	@Column(name = "bundle_amount")
	@NotNull(message = "加購金額: 請勿空白")
	private Integer bundleAmount; // 加購項目總金額

	@Column(name = "camp_amount")
	@NotNull(message = "營地訂單金額: 請勿空白")
	private Integer campsiteAmount; // 營地總金額

	@Column(name = "bef_amount")
	@NotNull(message = "營地訂單＿折價前總金額: 請勿空白")
	private Integer befAmount; // 折價前總金額

	@Column(name = "dis_amount")
	@NotNull(message = "營地訂單＿折價券金額: 請勿空白")
	private Integer disAmount; // 折價金額

	@Column(name = "aft_amount")
	@NotNull(message = "營地訂單＿折價後總金額: 請勿空白")
	private Integer aftAmount; // 實付金額

	@Column(name = "pay_method")
	@NotNull(message = "付款方式: 請勿空白")
	private byte payMethod; // 支付方式 1: 信用卡 2: LINEPAY

	@Column(name = "check_in")
	@NotNull(message = "入住日期: 請勿空白")
	private Date checkIn; // 入住日期

	@Column(name = "check_out")
	@NotNull(message = "離營日期: 請勿空白")
	private Date checkOut; // 退營日期

	@Column(name = "comment_satisfaction")
	private Integer commentSatisfaction; // 評價滿意度

	@Column(name = "comment_content")
	private String commentContent; // 評價內文

	@Column(name = "comment_date")
	private Timestamp commentDate; // 評價日期
	// name:自己的欄位 ｜｜referencedColumnName是關聯的欄位
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "camp_id", referencedColumnName = "camp_id")
	private CampVO campVO;

	@OneToMany(mappedBy = "campSiteOrderVO", cascade = CascadeType.ALL)
	private Set<CampSiteOrderDetailsVO> campsiteOrderDetails = new HashSet<>();

	public String getCampsiteOrderId() {
		return campsiteOrderId;
	}

	public void setCampsiteOrderId(String campsiteOrderId) {
		this.campsiteOrderId = campsiteOrderId;
	}

	public Set<CampSiteOrderDetailsVO> getCampSiteOrderDetails() {
		return campsiteOrderDetails;
	}

	public void setCampSiteOrderDetails(Set<CampSiteOrderDetailsVO> campsiteOrders) {
		this.campsiteOrderDetails = campsiteOrders;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	// FK的關聯欄位
	public CampVO getCampVO() {
		return campVO;
	}

	public void setCampVO(CampVO campVO) {
		this.campVO = campVO;
	}

	public String getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public byte getCampsiteOrderStatus() {
		return campsiteOrderStatus;
	}

	public void setCampsiteOrderStatus(byte campsiteOrderStatus) {
		this.campsiteOrderStatus = campsiteOrderStatus;
	}

	public Integer getBundleAmount() {
		return bundleAmount;
	}

	public void setBundleAmount(Integer bundleAmount) {
		this.bundleAmount = bundleAmount;
	}

	public Integer getCampsiteAmount() {
		return campsiteAmount;
	}

	public void setCampsiteAmount(Integer campsiteAmount) {
		this.campsiteAmount = campsiteAmount;
	}

	public Integer getBefAmount() {
		return befAmount;
	}

	public void setBefAmount(Integer befAmount) {
		this.befAmount = befAmount;
	}

	public Integer getDisAmount() {
		return disAmount;
	}

	public void setDisAmount(Integer disAmount) {
		this.disAmount = disAmount;
	}

	public Integer getAftAmount() {
		return aftAmount;
	}

	public void setAftAmount(Integer aftAmount) {
		this.aftAmount = aftAmount;
	}

	public byte getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(byte payMethod) {
		this.payMethod = payMethod;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Integer getCommentSatisfaction() {
		return commentSatisfaction;
	}

	public void setCommentSatisfaction(Integer commentSatisfaction) {
		this.commentSatisfaction = commentSatisfaction;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Timestamp getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

}
