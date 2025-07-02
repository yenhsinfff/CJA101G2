package com.lutu.administrator.dto;

public class AdminDTO {

    private Integer adminId;
    private String adminAcc;
    private byte adminStatus;
    private String adminName;

    public AdminDTO() {}

    public AdminDTO(Integer adminId, String adminAcc, byte adminStatus, String adminName) {
        this.adminId = adminId;
        this.adminAcc = adminAcc;
        this.adminStatus = adminStatus;
        this.adminName = adminName;
    }

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
