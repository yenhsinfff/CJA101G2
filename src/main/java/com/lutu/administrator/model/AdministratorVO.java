package com.lutu.administrator.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.camp_report.model.CampReportVO;
import com.lutu.campsite_order.model.CampSiteOrderVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "administrator")
public class AdministratorVO implements java.io.Serializable {

	@Id
	@Column(name = "admin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminId; // 管理員編號

	@Column(name = "admin_acc")
	@NotEmpty(message = "管理者帳號: 請勿空白")
	private String adminAcc; // 管理員帳號

	@Column(name = "admin_pwd")
	@NotEmpty(message = "管理者密碼: 請勿空白")
	private String adminPwd; // 管理員密碼


//	@Column(name = "admin_pwd_hash")
//	@NotEmpty(message = "管理者加密密碼: 請勿空白")
//	private String adminPwdHash; // 管理員加密 密碼

	@Column(name = "admin_status")
	@NotNull(message = "帳號狀態: 請勿空白")
	@Min(value = 0, message = "帳號狀態只能是 0:未啟用、1:已啟用")
	@Max(value = 1, message = "帳號狀態只能是 0:未啟用、1:已啟用")
	private byte adminStatus; // 帳號狀態

	@Column(name = "admin_name")
	@NotEmpty(message = "姓名: 請勿空白")
	@Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9_]{2,10}$", message = "姓名只能是中、英文字母、數字和_ , 且長度需在2到10之間")
	private String adminName; // 姓名

	// ========物件關聯======//
	@OneToMany(mappedBy = "administratorVO")
	private Set<CampReportVO> campReports = new HashSet<>();

	public AdministratorVO() {
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminAcc() {
		return adminAcc;
	}

	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	
	
	public void setPassword(String password) {
	    this.adminPwd = password;
	}

	public String getPassword() {
	    return this.adminPwd;
	}
	

//	public String getAdminPwdHash() {
//		return adminPwdHash;
//	}
//
//	public void setAdminPwdHash(String adminPwdHash) {
//		this.adminPwdHash = adminPwdHash;
//	}

	public byte getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(byte adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	// ========物件關聯 getter setter======//

	public Set<CampReportVO> getCampReports() {
		return campReports;
	}

	public void setCampReports(Set<CampReportVO> campReports) {
		this.campReports = campReports;
	}
}
