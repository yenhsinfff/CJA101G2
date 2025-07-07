package com.lutu.owner.dto;

public class OwnerChangePasswordRequest {
	
    private String oldPassword;
    private String newPassword;

    public OwnerChangePasswordRequest() {}

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