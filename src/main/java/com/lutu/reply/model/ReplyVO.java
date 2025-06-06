package com.lutu.reply.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class ReplyVO implements java.io.Serializable{
	
	private Integer replyId;				// 留言編號(PK)
	private Integer	memId;					// 露營者編號
	private Integer	acId;					// 討論區文章編號
	private LocalDateTime	replyTime;		// 回覆時間
	private String	replyContext;			// 回覆內容
	private byte	replyStatus;			// 留言狀態		Not Null / 0: 顯示 / 1: 不顯示
	
	
	
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
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
	public LocalDateTime getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(LocalDateTime replyTime) {
		this.replyTime = replyTime;
	}
	public String getReplyContext() {
		return replyContext;
	}
	public void setReplyContext(String replyContext) {
		this.replyContext = replyContext;
	}
	public byte getReplyStatus() {
		return replyStatus;
	}
	public void setReplyStatus(byte replyStatus) {
		this.replyStatus = replyStatus;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(replyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplyVO other = (ReplyVO) obj;
		return Objects.equals(replyId, other.replyId);
	}
	
	
	@Override
	public String toString() {		// +", = "
		return "Reply [留言編號 = "                      + replyId + 
					", 露營者編號 = "                    + memId + 
					", 討論區文章編號 = "                + acId + 
					", 回覆時間 = "                      + replyTime + 
					", 回覆內容 = "                      + replyContext + 
					", 留言狀態	  0: 顯示  1: 不顯示 = " + replyStatus + "]";
	}

}
