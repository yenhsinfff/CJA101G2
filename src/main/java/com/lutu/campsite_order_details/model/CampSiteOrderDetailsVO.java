
package com.lutu.campsite_order_details.model;

import com.lutu.campsite_order.model.CampSiteOrderVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campsite_order_details")
public class CampSiteOrderDetailsVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_details_id")
	private Integer campsiteDetailsId; // 訂單明細編號

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campsite_order_id",referencedColumnName = "campsite_order_id", nullable = false)
	private CampSiteOrderVO campSiteOrderVO;
//	private Integer campsiteOrderId; // 營地訂單編號
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "campsite_type_id", nullable = false)
	private Integer campsiteTypeId; // 營地房型編號

	@Column(name = "campsite_num", nullable = false)
	private Integer campsiteNum; // 營地房型數量

	@Column(name = "campsite_amount", nullable = false)
	private Integer campsiteAmount; // 營地房型總價

	public Integer getCampsiteDetailsId() {
		return campsiteDetailsId;
	}

	public void setCampsiteDetailsId(Integer campsiteDetailsId) {
		this.campsiteDetailsId = campsiteDetailsId;
	}

	public CampSiteOrderVO getcampSiteOrderVO() {
		return campSiteOrderVO;
	}

	public void setcampSiteOrderVO(CampSiteOrderVO campSiteOrderVO) {
		this.campSiteOrderVO = campSiteOrderVO;
	}

	public Integer getCampsiteTypeId() {
		return campsiteTypeId;
	}

	public void setCampsiteTypeId(Integer campsiteTypeId) {
		this.campsiteTypeId = campsiteTypeId;
	}

	public Integer getCampsiteNum() {
		return campsiteNum;
	}

	public void setCampsiteNum(Integer campsiteNum) {
		this.campsiteNum = campsiteNum;
	}

	public Integer getCampsiteAmount() {
		return campsiteAmount;
	}

	public void setCampsiteAmount(Integer campsiteAmount) {
		this.campsiteAmount = campsiteAmount;
	}

}