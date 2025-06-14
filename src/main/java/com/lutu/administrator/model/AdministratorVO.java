package com.lutu.administrator.model;


public class AdministratorVO implements java.io.Serializable{
	
	private Integer adminId; //管理員編號
	private String adminAcc; //管理員帳號
	private String adminPwd; //管理員密碼
	private String adminPwdHash; //管理員加密密碼
	private byte adminStatus; //帳號狀態
	private String adminName; //姓名
	
	
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminAcc() {
		return adminAcc;
	}

	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getAdminPwdHash() {
		return adminPwdHash;
	}

	public void setAdminPwdHash(String adminPwdHash) {
		this.adminPwdHash = adminPwdHash;
	}

	public byte getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(byte adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
}
