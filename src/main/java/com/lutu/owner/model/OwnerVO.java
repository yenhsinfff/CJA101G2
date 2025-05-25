package com.lutu.owner.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OwnerVO implements Serializable{
	
	private Integer ownerId; //營地主編號
	private String ownerAcc; //營地主帳號
	private String ownerPwd; //營地主密碼
	private byte accStatus; //帳號狀態
	private String ownerName; //店家名稱
	private String ownerGui;//統編
	private String ownerRep; //負責人
	private String ownerTel; //負責人電話
	private String ownerPoc; //聯絡人
	private String ownerConPhone; //聯絡人電話
	private String ownerAddr; //地址
	private String ownerEmail; //信箱
	private LocalDateTime ownerRegDate; //加入時間
	private String bankAccount; //轉帳帳號
	private byte[] ownerPic; //營地主照片
	
	public OwnerVO() {
		super();
	}

	public OwnerVO(Integer ownerId, String ownerAcc, String ownerPwd, byte accStatus, String ownerName, String ownerGui,
			String ownerRep, String ownerTel, String ownerPoc, String ownerConPhone, String ownerAddr,
			String ownerEmail, LocalDateTime ownerRegDate, String bankAccount, byte[] ownerPic) {
		super();
		this.ownerId = ownerId;
		this.ownerAcc = ownerAcc;
		this.ownerPwd = ownerPwd;
		this.accStatus = accStatus;
		this.ownerName = ownerName;
		this.ownerGui = ownerGui;
		this.ownerRep = ownerRep;
		this.ownerTel = ownerTel;
		this.ownerPoc = ownerPoc;
		this.ownerConPhone = ownerConPhone;
		this.ownerAddr = ownerAddr;
		this.ownerEmail = ownerEmail;
		this.ownerRegDate = ownerRegDate;
		this.bankAccount = bankAccount;
		this.ownerPic = ownerPic;
	}

	
	// --- Getters and Setters ---
	
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

	public String getOwnerPwd() {
		return ownerPwd;
	}

	public void setOwnerPwd(String ownerPwd) {
		this.ownerPwd = ownerPwd;
	}

	public byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(byte accStatus) {
		this.accStatus = accStatus;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public byte[] getOwnerPic() {
		return ownerPic;
	}

	public void setOwnerPic(byte[] ownerPic) {
		this.ownerPic = ownerPic;
	}


}
