package com.lutu.owner.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "owner")
public class OwnerVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id  
	@Column(name = "owner_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ownerId; //營地主編號
	
	
	@Column(name = "owner_acc")
	@NotEmpty(message="營地主帳號: 請勿空白。營地主帳號=信箱。")
    @Email(message="帳號格式不正確。露營者帳號=信箱。")
	private String ownerAcc; //營地主帳號
	
	
	@Column(name = "owner_pwd")
	@NotEmpty(message="營地主密碼: 請勿空白")
	private String ownerPwd; //營地主密碼
	
	
	@Column(name = "verification_token")
	private String verificationToken; //token
	
	
	public String getVerificationToken() {
	    return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
	    this.verificationToken = verificationToken;
	}
	
	
	@Column(name = "acc_status")
	@NotNull(message = "帳號狀態: 請勿空白")
	@Min(value = 0, message = "帳號狀態只能是 0:未啟用、1:已啟用、2:停權")
	@Max(value = 2, message = "帳號狀態只能是 0:未啟用、1:已啟用、2:停權")
	private byte accStatus; //帳號狀態
	
	
	@Column(name = "owner_name")
	@NotEmpty(message="店家名稱: 請勿空白")
	private String ownerName; //店家名稱
	
	
	@Column(name = "owner_gui")
	@NotEmpty(message = "統一編號: 請勿空白")
	@Pattern(regexp = "^\\d{8}$", message = "統一編號必須為8位數字")
	private String ownerGui;// 統編
	
	
	@Column(name = "owner_rep")
	@NotEmpty(message="負責人: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "負責人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	private String ownerRep; //負責人
	
	
	@Column(name = "owner_tel")
	@NotEmpty(message = "電話不可空白")
	@Pattern(
	  regexp = "^(09\\d{2}-\\d{3}-\\d{3}|0[3-8]-\\d{3}-\\d{4}|02-\\d{4}-\\d{4})$",
	  message = "電話格式錯誤，請輸入手機如0968-123-456，或市話如03-456-7890 / 02-1234-5678"
	)
	private String ownerTel; //負責人電話
	
	
	@Column(name = "owner_poc")
	@NotEmpty(message="聯絡人: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "聯絡人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	private String ownerPoc; //聯絡人
	
	
	@Column(name = "owner_con_phone")
	@NotEmpty(message = "電話不可空白")
	@Pattern(
	  regexp = "^(09\\d{2}-\\d{3}-\\d{3}|0[3-8]-\\d{3}-\\d{4}|02-\\d{4}-\\d{4})$",
	  message = "電話格式錯誤，請輸入手機如0968-123-456，或市話如03-456-7890 / 02-1234-5678"
	)
	private String ownerConPhone; //聯絡人電話
	
	
	@Column(name = "owner_addr")
	@NotEmpty(message="地址: 請勿空白")
	private String ownerAddr; //地址
	
	
	@Column(name = "owner_email")
	@NotEmpty(message="信箱: 請勿空白")
	@Email(message = "信箱格式不正確")
	private String ownerEmail; //信箱
	
	
	@Column(name = "owner_reg_date", nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime ownerRegDate; //加入時間
	
	
	@Column(name = "bank_account")
	@NotEmpty(message="轉帳帳號: 請勿空白")
	private String bankAccount; //轉帳帳號
	
	
	@Column(name = "owner_pic")
	private byte[] ownerPic; //營地主照片
	
	
	
	
	public OwnerVO() {
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

	public String getOwnerPwd() {
		return ownerPwd;
	}

	public void setOwnerPwd(String ownerPwd) {
		this.ownerPwd = ownerPwd;
	}
	
	
	public void setPassword(String password) {
	    this.ownerPwd = password;
	}

	public String getPassword() {
	    return this.ownerPwd;
	}
	

	public byte getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(byte accStatus) {
		this.accStatus = accStatus;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerGui() {
		return ownerGui;
	}

	public void setOwnerGui(String ownerGui) {
		this.ownerGui = ownerGui;
	}

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

	public String getOwnerAddr() {
		return ownerAddr;
	}

	public void setOwnerAddr(String ownerAddr) {
		this.ownerAddr = ownerAddr;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public LocalDateTime getOwnerRegDate() {
		return ownerRegDate;
	}

	public void setOwnerRegDate(LocalDateTime ownerRegDate) {
		this.ownerRegDate = ownerRegDate;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public byte[] getOwnerPic() {
		return ownerPic;
	}

	public void setOwnerPic(byte[] ownerPic) {
		this.ownerPic = ownerPic;
	}


}
