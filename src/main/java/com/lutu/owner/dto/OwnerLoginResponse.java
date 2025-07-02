package com.lutu.owner.dto;

import java.io.Serializable;

public class OwnerLoginResponse implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer ownerId;
    private String ownerAcc;
    private byte accStatus;

    public OwnerLoginResponse(Integer ownerId, String ownerAcc, byte accStatus) {
        this.ownerId = ownerId;
        this.ownerAcc = ownerAcc;
        this.accStatus = accStatus;
    }

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}

	public byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(byte accStatus) {
		this.accStatus = accStatus;
	}
    
    
}
