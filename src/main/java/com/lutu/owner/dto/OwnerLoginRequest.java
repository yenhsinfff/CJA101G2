package com.lutu.owner.dto;

public class OwnerLoginRequest {
	
    private String ownerAcc;
    private String ownerPwd;
    
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
}
