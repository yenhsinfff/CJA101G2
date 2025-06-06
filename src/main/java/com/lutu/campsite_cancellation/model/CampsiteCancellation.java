package com.lutu.campsite_cancellation.model;

import java.sql.Timestamp;

public class CampsiteCancellation {

	private String campsiteCancelId; // 營地取消編號
	private Integer campOrderId; // 營地訂單編號
	private Timestamp campsiteCancelDate; // 取消日期
	private String campsiteCancelReason; // 取消理由
	private byte campsiteCancelStatus; // 取消狀態
	private String campsiteCancelReplyCustomer; // 營地主回覆
	private byte returnMethod;

	public String getCampsiteCancelId() {
		return campsiteCancelId;
	}

	public void setCampsiteCancelId(String campsiteCancelId) {
		this.campsiteCancelId = campsiteCancelId;
	}

	public Integer getCampOrderId() {
		return campOrderId;
	}

	public void setCampOrderId(Integer campOrderId) {
		this.campOrderId = campOrderId;
	}

	public Timestamp getCampsiteCancelDate() {
		return campsiteCancelDate;
	}

	public void setCampsiteCancelDate(Timestamp campsiteCancelDate) {
		this.campsiteCancelDate = campsiteCancelDate;
	}

	public String getCampsiteCancelReason() {
		return campsiteCancelReason;
	}

	public void setCampsiteCancelReason(String campsiteCancelReason) {
		this.campsiteCancelReason = campsiteCancelReason;
	}

	public byte getCampsiteCancelStatus() {
		return campsiteCancelStatus;
	}

	public void setCampsiteCancelStatus(byte campsiteCancelStatus) {
		this.campsiteCancelStatus = campsiteCancelStatus;
	}

	public String getCampsiteCancelReplyCustomer() {
		return campsiteCancelReplyCustomer;
	}

	public void setCampsiteCancelReplyCustomer(String campsiteCancelReplyCustomer) {
		this.campsiteCancelReplyCustomer = campsiteCancelReplyCustomer;
	}

	public byte getReturnMethod() {
		return returnMethod;
	}

	public void setReturnMethod(byte returnMethod) {
		this.returnMethod = returnMethod;
	} // 退款方式

}
