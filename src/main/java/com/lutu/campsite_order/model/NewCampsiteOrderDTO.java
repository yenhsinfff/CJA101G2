package com.lutu.campsite_order.model;

import java.util.Set;

import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;

public class NewCampsiteOrderDTO {

	private String campsiteOrderId;
	private String orderDate;
	private Byte campsiteOrderStatus;
	private Byte payMethod;
	private Integer bundleAmount;
	private Integer campsiteAmount;
	private Integer befAmount;
	private Integer disAmount;
	private Integer aftAmount;
	private String checkIn;
	private String checkOut;
	private Integer commentSatisfaction;
	private String commentContent;
	private String commentDate;
	private Integer campId;
	private String campName;
	private Integer memId;
	private String discountCodeId;
	private Set<CampSiteOrderDetailsVO> campSiteOrderDetails;

	public String getCampsiteOrderId() {
		return campsiteOrderId;
	}

	public void setCampsiteOrderId(String campsiteOrderId) {
		this.campsiteOrderId = campsiteOrderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Byte getCampsiteOrderStatus() {
		return campsiteOrderStatus;
	}

	public void setCampsiteOrderStatus(Byte campsiteOrderStatus) {
		this.campsiteOrderStatus = campsiteOrderStatus;
	}

	public Byte getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Byte payMethod) {
		this.payMethod = payMethod;
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

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
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

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public String getCampName() {
		return campName;
	}

	public void setCampName(String campName) {
		this.campName = campName;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getDiscountCodeId() {
		return discountCodeId;
	}

	public void setDiscountCodeId(String discountCodeId) {
		this.discountCodeId = discountCodeId;
	}

	public Set<CampSiteOrderDetailsVO> getCampSiteOrderDetails() {
		return campSiteOrderDetails;
	}

	public void setCampSiteOrderDetails(Set<CampSiteOrderDetailsVO> campSiteOrderDetails) {
		this.campSiteOrderDetails = campSiteOrderDetails;
	}

}
