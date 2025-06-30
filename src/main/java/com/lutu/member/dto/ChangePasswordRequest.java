package com.lutu.member.dto;

public class ChangePasswordRequest {  //更改密碼
	
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest() {}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}
