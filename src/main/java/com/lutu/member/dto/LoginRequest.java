package com.lutu.member.dto;

public class LoginRequest { //登入請求
    private String memAcc;
    private String memPwd;

    public LoginRequest() {}

    public String getMemAcc() {
        return memAcc;
    }

    public void setMemAcc(String memAcc) {
        this.memAcc = memAcc;
    }

    public String getMemPwd() {
        return memPwd;
    }

    public void setMemPwd(String memPwd) {
        this.memPwd = memPwd;
    }
}
