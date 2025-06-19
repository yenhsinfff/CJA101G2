package com.lutu.campsite_cancellation.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.lutu.campsite_order.model.CampSiteOrderVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campsite_cancellation")
public class CampsiteCancellationVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "campsite_cancel_id", length = 20, nullable = false)
	private String campsiteCancelId; // 營地取消編號

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campsite_order_id", nullable = false)
	private CampSiteOrderVO campsiteOrderVO; // 營地訂單（建議建立對應Entity）
	@Column(name = "campsite_cancel_date", nullable = false)
	private Timestamp campsiteCancelDate; // 取消日期

	@Column(name = "campsite_cancel_reason", length = 500, nullable = false)
	private String campsiteCancelReason; // 取消理由

	@Column(name = "campsite_cancel_status", nullable = false)
	private byte campsiteCancelStatus; // 取消狀態

	@Column(name = "campsite_cancel_reply_customer", length = 500)
	private String campsiteCancelReplyCustomer; // 營地主回覆

	@Column(name = "return_method", nullable = false)
	private byte returnMethod; // 退款方式

	public String getCampsiteCancelId() {
		return campsiteCancelId;
	}

	public void setCampsiteCancelId(String campsiteCancelId) {
		this.campsiteCancelId = campsiteCancelId;
	}

	public CampSiteOrderVO getCampsiteOrderVO() {
		return campsiteOrderVO;
	}

	public void setCampSiteOrder(CampSiteOrderVO campsiteOrderVO) {
		this.campsiteOrderVO = campsiteOrderVO;
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
