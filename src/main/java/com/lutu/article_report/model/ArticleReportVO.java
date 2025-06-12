package com.lutu.article_report.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class ArticleReportVO implements java.io.Serializable{
	
	private Integer acReportId;			// 討論區案件編號 (PK)
	private Integer memId;				// 露營者編號
	private Integer acId;				// 討論區文章編號
	private LocalDateTime rpTime;		// 檢舉時間
	private String rpContent;			// 檢舉文字內容
	private Integer adminId;			// 管理員編號
	private String adminMemCustomer;	// 管理員回覆
	private LocalDateTime rpDoneTime;	// 處理完成時間
	private byte rpStatus;				// 處理狀態   Not Null   0: 未處理	1: 已處理
	private byte rpResult;				// 處理結果   Not Null   0: 通過	    1: 未通過
	private String rpNote;				// 處理註記
	
	
	
	public Integer getAcReportId() {
		return acReportId;
	}
	public void setAcReportId(Integer acReportId) {
		this.acReportId = acReportId;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public Integer getAcId() {
		return acId;
	}
	public void setAcId(Integer acId) {
		this.acId = acId;
	}
	public LocalDateTime getRpTime() {
		return rpTime;
	}
	public void setRpTime(LocalDateTime rpTime) {
		this.rpTime = rpTime;
	}
	public String getRpContent() {
		return rpContent;
	}
	public void setRpContent(String rpContent) {
		this.rpContent = rpContent;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public String getAdminMemCustomer() {
		return adminMemCustomer;
	}
	public void setAdminMemCustomer(String adminMemCustomer) {
		this.adminMemCustomer = adminMemCustomer;
	}
	public LocalDateTime getRpDoneTime() {
		return rpDoneTime;
	}
	public void setRpDoneTime(LocalDateTime rpDoneTime) {
		this.rpDoneTime = rpDoneTime;
	}
	public byte getRpStatus() {
		return rpStatus;
	}
	public void setRpStatus(byte rpStatus) {
		this.rpStatus = rpStatus;
	}
	public byte getRpResult() {
		return rpResult;
	}
	public void setRpResult(byte rpResult) {
		this.rpResult = rpResult;
	}
	public String getRpNote() {
		return rpNote;
	}
	public void setRpNote(String rpNote) {
		this.rpNote = rpNote;
	}
	

	
	
	@Override
	public int hashCode() {
		return Objects.hash(acReportId);
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleReportVO other = (ArticleReportVO) obj;
		return Objects.equals(acReportId, other.acReportId);
	}
	
	
	@Override
	public String toString() {		// +", = "
		return "ArticleReportVO [討論區案件編號 = "                    + acReportId + 
						      ", 露營者編號 = "                        + memId + 
						      ", 討論區文章編號 = "                    + acId + 
						      ", 檢舉時間 = "                          + rpTime + 
						      ", 檢舉文字內容 = "                      + rpContent + 
					          ", 管理員編號 = "                        + adminId + 
						      ", 管理員回覆 = "                        + adminMemCustomer + 
						      ", 處理完成時間 = "                      + rpDoneTime + 
						      ", 處理狀態	0: 未處理   1: 已處理 = "  + rpStatus + 
						      ", 處理結果	0: 通過		1: 未通過 = "  + rpResult + 
						      ", 處理註記 = "                          + rpNote + "]";
	}
	
	
}
