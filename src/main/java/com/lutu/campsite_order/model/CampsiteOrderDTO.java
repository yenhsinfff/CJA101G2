package com.lutu.campsite_order.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.lutu.campsite_order_details.model.CampSiteOrderDetailsDTO;
import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;

public class CampsiteOrderDTO {
    private String campsiteOrderId;
    private Integer memId;
    private String discountCodeId;
    private Timestamp orderDate;
    private Byte campsiteOrderStatus;
    private Integer bundleAmount;
    private Integer campsiteAmount;
    private Integer befAmount;
    private Integer disAmount;
    private Integer aftAmount;
    private Byte payMethod;
    private Date checkIn;
    private Date checkOut;
    private Integer commentSatisfaction;
    private String commentContent;
    private Timestamp commentDate;
    private Integer campId;
    private List<CampSiteOrderDetailsDTO> orderDetails; // 使用DTO列表

    // 轉換方法
    public static CampsiteOrderDTO fromEntity(CampSiteOrderVO entity) {
    	CampsiteOrderDTO dto = new CampsiteOrderDTO();
        
        // 複製基本屬性
        dto.setCampsiteOrderId(entity.getCampsiteOrderId());
        dto.setMemId(entity.getMemId());
        dto.setDiscountCodeId(entity.getDiscountCodeId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setCampsiteOrderStatus(entity.getCampsiteOrderStatus());
        dto.setBundleAmount(entity.getBundleAmount());
        dto.setCampsiteAmount(entity.getCampsiteAmount());
        dto.setBefAmount(entity.getBefAmount()); // 這行一定要有
        dto.setDisAmount(entity.getDisAmount());
        dto.setAftAmount(entity.getAftAmount());
        dto.setPayMethod(entity.getPayMethod());
        dto.setCheckIn(entity.getCheckIn());
        dto.setCheckOut(entity.getCheckOut());
        dto.setCommentSatisfaction(entity.getCommentSatisfaction());
        dto.setCommentContent(entity.getCommentContent());
        dto.setCommentDate(entity.getCommentDate());
        dto.setCampId(entity.getCampId());
        
        // 轉換明細為DTO
        List<CampSiteOrderDetailsDTO> detailsDTOs = entity.getCampSiteOrderDetails()
            .stream()
            .map(CampSiteOrderDetailsDTO::new)
            .collect(Collectors.toList());
        
        dto.setOrderDetails(detailsDTOs);
        return dto;
    }

	public String getCampsiteOrderId() {
		return campsiteOrderId;
	}

	public void setCampsiteOrderId(String campsiteOrderId) {
		this.campsiteOrderId = campsiteOrderId;
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

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Byte getCampsiteOrderStatus() {
		return campsiteOrderStatus;
	}

	public void setCampsiteOrderStatus(Byte campsiteOrderStatus) {
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

	public Byte getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Byte payMethod) {
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

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public List<CampSiteOrderDetailsDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<CampSiteOrderDetailsDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

    // Getters and Setters
}

