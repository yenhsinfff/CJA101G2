package com.lutu.owner.dto;

import org.springframework.web.multipart.MultipartFile;

public class OwnerUpdateRequest {
	
//    private String ownerPwd;
    private String ownerRep;
    private String ownerTel;
    private String ownerPoc;
	private String ownerConPhone;
    private String bankAccount;
    private MultipartFile ownerPic;
    
//	public String getOwnerPwd() {
//		return ownerPwd;
//	}
//	public void setOwnerPwd(String ownerPwd) {
//		this.ownerPwd = ownerPwd;
//	}
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
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
    public MultipartFile getOwnerPic() {
        return ownerPic;
    }
    public void setOwnerPic(MultipartFile ownerPic) {
        this.ownerPic = ownerPic;
    }
}
