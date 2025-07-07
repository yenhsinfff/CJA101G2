package com.lutu.member.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.lutu.member.model.MemberVO;

public class MemberAdminListDTO {
    private Integer memId;
    private String memAcc;
    private String memName;
    private String memEmail;
    private LocalDateTime memRegDate;
    private byte accStatus;

    public MemberAdminListDTO(MemberVO vo) {
        this.memId = vo.getMemId();
        this.memAcc = vo.getMemAcc();
        this.memName = vo.getMemName();
        this.memEmail = vo.getMemEmail();
        this.memRegDate = vo.getMemRegDate();
        this.accStatus = vo.getAccStatus();
    }

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getMemAcc() {
		return memAcc;
	}

	public void setMemAcc(String memAcc) {
		this.memAcc = memAcc;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemEmail() {
		return memEmail;
	}

	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}

	public LocalDateTime getMemRegDate() {
		return memRegDate;
	}

	public void setMemRegDate(LocalDateTime memRegDate) {
		this.memRegDate = memRegDate;
	}

	public byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(byte accStatus) {
		this.accStatus = accStatus;
	}



    
}
