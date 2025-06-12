package com.lutu.reply_report_id.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class ReplyReportIdVO implements java.io.Serializable{
	
	private Integer	replyreportid;		// 留言案件編號 (PK)
	private Integer	memid;				// 露營者編號
	private Integer	acId;				// 文章編號
	private Integer replyId;			// 留言編號
	private Integer adminId;			// 管理員編號
	private LocalDateTime rpTime;		// 檢舉時間
	private String rpContent;			// 檢舉文字內容
	private String adminReply;			// 管理員回覆
	private LocalDateTime rpDoneTime;	// 處理完成時間
	private byte rpStatus;				// 處理狀態  Not Null	 0: 未處理	1: 已處理
	private byte rpSresult;				// 處理結果  Not Null	 0: 未通過	1: 通過
	private String rpNote;				// 處理註記
	
	
	
	public Integer getReplyreportid() {
		return replyreportid;
	}
	public void setReplyreportid(Integer replyreportid) {
		this.replyreportid = replyreportid;
	}
	public Integer getMemid() {
		return memid;
	}
	public void setMemid(Integer memid) {
		this.memid = memid;
	}
	public Integer getAcId() {
		return acId;
	}
	public void setAcId(Integer acId) {
		this.acId = acId;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
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
	public String getAdminReply() {
		return adminReply;
	}
	public void setAdminReply(String adminReply) {
		this.adminReply = adminReply;
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
	public byte getRpSresult() {
		return rpSresult;
	}
	public void setRpSresult(byte rpSresult) {
		this.rpSresult = rpSresult;
	}
	public String getRpNote() {
		return rpNote;
	}
	public void setRpNote(String rpNote) {
		this.rpNote = rpNote;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(replyreportid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplyReportIdVO other = (ReplyReportIdVO) obj;
		return Objects.equals(replyreportid, other.replyreportid);
	}
	
	
	
	@Override
	public String toString() {		// +", = "
		return "ReplyReportIdVO [留言案件編號 = "                + replyreportid + 
						", 露營者編號 = "                        + memid + 
						", 文章編號 = "                          + acId + 
						", 留言編號 = "                          + replyId + 
						", 管理員編號 = "                        + adminId + 
						", 檢舉時間 = "                          + rpTime + 
						", 檢舉文字內容 = "                      + rpContent + 
						", 管理員回覆 = "                        + adminReply + 
						", 處理完成時間 = "                      + rpDoneTime + 
						", 處理狀態  0: 未處理	1: 已處理 = "   + rpStatus + 
						", 處理結果  0: 未通過	1: 通過 = "     + rpSresult + 
						", 處理註記 = "                         + rpNote + "]";
	}
	
	
	
	

}
