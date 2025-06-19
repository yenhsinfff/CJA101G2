package com.lutu.member.model;

import java.io.Serializable;
import java.sql.Date;
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
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "MEMBER")
public class MemberVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id  
	@Column(name = "mem_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memId; //露營者編號
	
	@Column(name = "mem_acc")
	@NotEmpty(message="露營者帳號: 請勿空白。露營者帳號=信箱。")
	@Email(message = "帳號格式不正確。露營者帳號=信箱。")
	private String membAcc; //露營者帳號
	
	@Column(name = "mem_pwd")
	@NotEmpty(message="露營者密碼: 請勿空白")
	private String memPwd; //露營者密碼
	
	@Column(name = "acc_status")
	@NotNull(message = "帳號狀態: 請勿空白")
	@Min(value = 0, message = "帳號狀態只能是 0:未啟用、1:已啟用、2:停權")
	@Max(value = 2, message = "帳號狀態只能是 0:未啟用、1:已啟用、2:停權")
	private byte accStatus; //帳號狀態
	
	@Column(name = "mem_nation")
	@NotNull(message = "國籍: 請勿空白")
	@Min(value = 0, message = "會員國籍: 只能是 0（本國籍）或 1（非本國籍）")
	@Max(value = 1, message = "會員國籍: 只能是 0（本國籍）或 1（非本國籍）")
	private byte memNation; //國籍
	
	@Column(name = "mem_nation_id")
	@Size(min=8,max=10,message="會員身分證或是護照號碼: 長度必需在{min}到{max}之間")
	private String memNationId; //身分證/護照
	
	@Column(name = "mem_name")
	@NotEmpty(message="姓名: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "露營者姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	private String memName; //姓名
	
	@Column(name = "mem_gender")
	@NotNull(message = "性別: 請勿空白")
	@Min(value = 1, message = "性別: 只能是 1:男、2:女、3:其他")
	@Max(value = 3, message = "性別: 只能是 1:男、2:女、3:其他")
	private byte memGender; //性別
	
	@Column(name = "mem_email")
	@NotEmpty(message="信箱: 請勿空白")
	@Email(message = "信箱格式不正確")
	private String memEmail; //信箱
	
	@Column(name = "mem_mobile")
	@NotEmpty(message="手機: 請勿空白")
	@Pattern(
			  regexp = "^09\\d{2}-\\d{3}-\\d{3}$",
			  message = "手機號碼格式錯誤，請使用 0968-123-456 格式"
			)
	private String memMobile; //手機
	
	@Column(name = "mem_addr")
	@NotEmpty(message="地址: 請勿空白")
	private String memAddr; //地址
	
	@Column(name = "mem_reg_date", nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime memRegDate; //加入時間
	
	@Column(name = "mem_pic")
	private byte[] memPic; //露營者照片
	
	@Column(name = "mem_birth")
	@Past(message="日期必須是在今日(含)之前")
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date memBirth; //露營者生日
	
	
	
	
	public MemberVO() {
	}
	
	public Integer getMemId() {
		return memId;
	}
	
	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getMembAcc() {
		return membAcc;
	}

	public void setMembAcc(String membAcc) {
		this.membAcc = membAcc;
	}

	public String getMemPwd() {
		return memPwd;
	}

	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
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

	public Date getMemBirth() {
		return memBirth;
	}

	public void setMemBirth(Date memBirth) {
		this.memBirth = memBirth;
	}


}
