package com.lutu.owner.dto;

import java.time.LocalDateTime;

public class OwnerListDTO {
    private Integer ownerId;
    private String ownerAcc;
    private String ownerName;
    private String ownerRep;
    private String ownerEmail;
    private LocalDateTime ownerRegDate;
    private Byte accStatus;

    // 建構子：從 OwnerVO 轉成 DTO
    public OwnerListDTO(com.lutu.owner.model.OwnerVO vo) {
        this.ownerId = vo.getOwnerId();
        this.ownerAcc = vo.getOwnerAcc();
        this.ownerName = vo.getOwnerName();
        this.ownerRep = vo.getOwnerRep();
        this.ownerEmail = vo.getOwnerEmail();
        this.ownerRegDate = vo.getOwnerRegDate();
        this.accStatus = vo.getAccStatus();
    }

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerRep() {
		return ownerRep;
	}

	public void setOwnerRep(String ownerRep) {
		this.ownerRep = ownerRep;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public LocalDateTime getOwnerRegDate() {
		return ownerRegDate;
	}

	public void setOwnerRegDate(LocalDateTime ownerRegDate) {
		this.ownerRegDate = ownerRegDate;
	}

	public Byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(Byte accStatus) {
		this.accStatus = accStatus;
	}


}
