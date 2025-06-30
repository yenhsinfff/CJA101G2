package com.lutu.member.dto;

import java.time.LocalDate;

public class RegisterRequest {
    private String memAcc;
    private String memPwd;
    private String memName;
    private Byte memGender;
    private String memEmail;
    private String memMobile;
    private String memAddr;
    private byte memNation;
    private String memNationId;
    private byte[] memPic;
    private LocalDate memBirth;

    // 空建構子
    public RegisterRequest() {}

    // 以下為 getter / setter

    public String getMemAcc() { return memAcc; }
    public void setMemAcc(String memAcc) { this.memAcc = memAcc; }

    public String getMemPwd() { return memPwd; }
    public void setMemPwd(String memPwd) { this.memPwd = memPwd; }

    public String getMemName() { return memName; }
    public void setMemName(String memName) { this.memName = memName; }

    public Byte getMemGender() { return memGender; }
    public void setMemGender(Byte memGender) { this.memGender = memGender; }

    public String getMemEmail() { return memEmail; }
    public void setMemEmail(String memEmail) { this.memEmail = memEmail; }

    public String getMemMobile() { return memMobile; }
    public void setMemMobile(String memMobile) { this.memMobile = memMobile; }

    public String getMemAddr() { return memAddr; }
    public void setMemAddr(String memAddr) { this.memAddr = memAddr; }

    public byte getMemNation() { return memNation; }
    public void setMemNation(byte memNation) { this.memNation = memNation; }

    public String getMemNationId() { return memNationId; }
    public void setMemNationId(String memNationId) { this.memNationId = memNationId; }

    public byte[] getMemPic() { return memPic; }
    public void setMemPic(byte[] memPic) { this.memPic = memPic; }

    public LocalDate getMemBirth() { return memBirth; }
    public void setMemBirth(LocalDate memBirth) { this.memBirth = memBirth; }
}
