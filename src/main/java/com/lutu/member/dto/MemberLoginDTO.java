package com.lutu.member.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lutu.member.model.MemberVO;

public class MemberLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer memId;
    private String memAcc;
    private String memPwd;
    private String verificationToken;
    private byte accStatus;
    private byte memNation;
    private String memNationId;
    private String memName;
    private byte memGender;
    private String memEmail;
    private String memMobile;
    private String memAddr;
    private LocalDateTime memRegDate;
    private byte[] memPic;
    private LocalDate memBirth;

    // 無參構造器
    public MemberLoginDTO() {}

    // 基於 MemberVO 的構造器
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

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
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

	public LocalDate getMemBirth() {
		return memBirth;
	}

	public void setMemBirth(LocalDate memBirth) {
		this.memBirth = memBirth;
	}

	public MemberLoginDTO(MemberVO memberVO) {
        this.memId = memberVO.getMemId();
        this.memAcc = memberVO.getMemAcc();
        this.memPwd = memberVO.getMemPwd();
        this.verificationToken = memberVO.getVerificationToken();
        this.accStatus = memberVO.getAccStatus();
        this.memNation = memberVO.getMemNation();
        this.memNationId = memberVO.getMemNationId();
        this.memName = memberVO.getMemName();
        this.memGender = memberVO.getMemGender();
        this.memEmail = memberVO.getMemEmail();
        this.memMobile = memberVO.getMemMobile();
        this.memAddr = memberVO.getMemAddr();
        this.memRegDate = memberVO.getMemRegDate();
        this.memPic = memberVO.getMemPic();
        this.memBirth = memberVO.getMemBirth();
    }

    // Getter & Setter (省略重複代碼)
    public Integer getMemId() { return memId; }
    public void setMemId(Integer memId) { this.memId = memId; }
    // ... 其他屬性的getter/setter
}
