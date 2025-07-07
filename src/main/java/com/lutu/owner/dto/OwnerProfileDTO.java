package com.lutu.owner.dto;

import com.lutu.owner.model.OwnerVO;

public class OwnerProfileDTO {
    private Integer ownerId;
    private String ownerAcc;
    private byte accStatus;
    private String ownerName;
    private String ownerGui;
    private String ownerRep;
    private String ownerTel;
    private String ownerPoc;
    private String ownerConPhone;
    private String ownerAddr;
    private String ownerEmail;
    private String bankAccount;
    private String ownerRegDate;

    public OwnerProfileDTO(OwnerVO owner) {
        this.ownerId = owner.getOwnerId();
        this.ownerAcc = owner.getOwnerAcc();
        this.accStatus = owner.getAccStatus();
        this.ownerName = owner.getOwnerName();
        this.ownerGui = owner.getOwnerGui();
        this.ownerRep = owner.getOwnerRep();
        this.ownerTel = owner.getOwnerTel();
        this.ownerPoc = owner.getOwnerPoc();
        this.ownerConPhone = owner.getOwnerConPhone();
        this.ownerAddr = owner.getOwnerAddr();
        this.ownerEmail = owner.getOwnerEmail();
        this.bankAccount = owner.getBankAccount();
        this.ownerRegDate = owner.getOwnerRegDate().toString();
    }
    
    
    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    public String getOwnerAcc() { return ownerAcc; }
    public void setOwnerAcc(String ownerAcc) { this.ownerAcc = ownerAcc; }

    public byte getAccStatus() { return accStatus; }
    public void setAccStatus(byte accStatus) { this.accStatus = accStatus; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerGui() { return ownerGui; }
    public void setOwnerGui(String ownerGui) { this.ownerGui = ownerGui; }

    public String getOwnerRep() { return ownerRep; }
    public void setOwnerRep(String ownerRep) { this.ownerRep = ownerRep; }

    public String getOwnerTel() { return ownerTel; }
    public void setOwnerTel(String ownerTel) { this.ownerTel = ownerTel; }

    public String getOwnerPoc() { return ownerPoc; }
    public void setOwnerPoc(String ownerPoc) { this.ownerPoc = ownerPoc; }

    public String getOwnerConPhone() { return ownerConPhone; }
    public void setOwnerConPhone(String ownerConPhone) { this.ownerConPhone = ownerConPhone; }

    public String getOwnerAddr() { return ownerAddr; }
    public void setOwnerAddr(String ownerAddr) { this.ownerAddr = ownerAddr; }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

    public String getOwnerRegDate() { return ownerRegDate; }
    public void setOwnerRegDate(String ownerRegDate) { this.ownerRegDate = ownerRegDate; }

}

