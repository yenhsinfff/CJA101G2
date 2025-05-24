package com.lutu.member.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

public class MemberVO implements Serializable{
	
	private Integer memId; //露營者編號
	private String membAcc; //露營者帳號
	private String memPwd; //露營者密碼
	private byte accStatus; //帳號狀態
	private byte memNation; //國籍
	private String memNationId; //身分證/護照
	private String memName; //姓名
	private byte memGender; //性別
	private String memEmail; //信箱
	private String memMobile; //手機
	private String memAddr; //地址
	private LocalDateTime memRegDate; //加入時間
	private byte[] memPic; //會員照片
	private Date memBirth; //會員生日
	
	public MemberVO() {
		super();
	}

	public MemberVO(Integer memId, String membAcc, String memPwd, byte accStatus, byte memNation, String memNationId,
			String memName, byte memGender, String memEmail, String memMobile, String memAddr, LocalDateTime memRegDate,
			byte[] memPic, Date memBirth) {
		super();
		this.memId = memId;
		this.membAcc = membAcc;
		this.memPwd = memPwd;
		this.accStatus = accStatus;
		this.memNation = memNation;
		this.memNationId = memNationId;
		this.memName = memName;
		this.memGender = memGender;
		this.memEmail = memEmail;
		this.memMobile = memMobile;
		this.memAddr = memAddr;
		this.memRegDate = memRegDate;
		this.memPic = memPic;
		this.memBirth = memBirth;
	}

	
	// --- Getters and Setters ---
	
	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getMembAcc() {
		return membAcc;
	}

	public void setMembAcc(String membAcc) {
		this.membAcc = membAcc;
	}

	public String getMemPwd() {
		return memPwd;
	}

	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}

	public byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(byte accStatus) {
		this.accStatus = accStatus;
	}

	public byte getMemNation() {
		return memNation;
	}

	public void setMemNation(byte memNation) {
		this.memNation = memNation;
	}

	public String getMemNationId() {
		return memNationId;
	}

	public void setMemNationId(String memNationId) {
		this.memNationId = memNationId;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public byte getMemGender() {
		return memGender;
	}

	public void setMemGender(byte memGender) {
		this.memGender = memGender;
	}

	public String getMemEmail() {
		return memEmail;
	}

	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}

	public String getMemMobile() {
		return memMobile;
	}

	public void setMemMobile(String memMobile) {
		this.memMobile = memMobile;
	}

	public String getMemAddr() {
		return memAddr;
	}

	public void setMemAddr(String memAddr) {
		this.memAddr = memAddr;
	}

	public LocalDateTime getMemRegDate() {
		return memRegDate;
	}

	public void setMemRegDate(LocalDateTime memRegDate) {
		this.memRegDate = memRegDate;
	}

	public byte[] getMemPic() {
		return memPic;
	}

	public void setMemPic(byte[] memPic) {
		this.memPic = memPic;
	}

	public Date getMemBirth() {
		return memBirth;
	}

	public void setMemBirth(Date memBirth) {
		this.memBirth = memBirth;
	}


}
