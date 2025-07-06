package com.lutu.owner.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.lutu.owner.model.OwnerVO;

public class OwnerLoginResponse implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer ownerId;
    private String ownerAcc;
    private String ownerName;
    private String ownerEmail;
    private byte accStatus;
    private String ownerGui;
    private String ownerRep;
    private String ownerTel;
    private String ownerPoc;
    private String ownerConPhone;
    private String ownerAddr;
    private String bankAccount;
    private LocalDateTime ownerRegDate;
    private byte[] ownerPic;

    public OwnerLoginResponse(OwnerVO owner) {
        this.ownerId = owner.getOwnerId();
        this.ownerAcc = owner.getOwnerAcc();
        this.ownerName = owner.getOwnerName();
        this.ownerEmail = owner.getOwnerEmail();
        this.accStatus = owner.getAccStatus();
        this.ownerGui = owner.getOwnerGui();
        this.ownerRep = owner.getOwnerRep();
        this.ownerTel = owner.getOwnerTel();
        this.ownerPoc = owner.getOwnerPoc();
        this.ownerConPhone = owner.getOwnerConPhone();
        this.ownerAddr = owner.getOwnerAddr();
        this.bankAccount = owner.getBankAccount();
        this.ownerRegDate = owner.getOwnerRegDate();
        this.ownerPic = owner.getOwnerPic(); // 可根據需求省略
    }

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerGui() {
		return ownerGui;
	}

	public void setOwnerGui(String ownerGui) {
		this.ownerGui = ownerGui;
	}

	public String getOwnerRep() {
		return ownerRep;
	}

	public void setOwnerRep(String ownerRep) {
		this.ownerRep = ownerRep;
	}

	public String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}

	public String getOwnerPoc() {
		return ownerPoc;
	}

	public void setOwnerPoc(String ownerPoc) {
		this.ownerPoc = ownerPoc;
	}

	public String getOwnerConPhone() {
		return ownerConPhone;
	}

	public void setOwnerConPhone(String ownerConPhone) {
		this.ownerConPhone = ownerConPhone;
	}

	public String getOwnerAddr() {
		return ownerAddr;
	}

	public void setOwnerAddr(String ownerAddr) {
		this.ownerAddr = ownerAddr;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public LocalDateTime getOwnerRegDate() {
		return ownerRegDate;
	}

	public void setOwnerRegDate(LocalDateTime ownerRegDate) {
		this.ownerRegDate = ownerRegDate;
	}

	public byte[] getOwnerPic() {
		return ownerPic;
	}

	public void setOwnerPic(byte[] ownerPic) {
		this.ownerPic = ownerPic;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(byte accStatus) {
		this.accStatus = accStatus;
	}
    
    
}
