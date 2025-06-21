package com.lutu.article.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class ArticlesVO implements java.io.Serializable{
	
	private Integer acId; 			// 討論區文章編號(PK)
	private String acTitle;		    // 文章標題
	private Integer memId;			// 露營者編號
	private Integer acTypeId;		// 文章類別編號
	private LocalDateTime acTime;	// 文章發布時間
	private String acContext;	    // 文章內容
	private byte acStatus;			// 文章狀態 Not Null  0 : 顯示  1 : 不顯示
	
	
	public Integer getAcId() {
		return acId;
	}
	public void setAcId(Integer acId) {
		this.acId = acId;
	}
	public String getAcTitle() {
		return acTitle;
	}
	public void setAcTitle(String acTitle) {
		this.acTitle = acTitle;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public Integer getAcTypeId() {
		return acTypeId;
	}  
	
	
	public void setAcTypeId(Integer acTypeId) {
		this.acTypeId = acTypeId;
	}
	public LocalDateTime getAcTime() {
		return acTime;
	}
	public void setAcTime(LocalDateTime acTime) {
		this.acTime = acTime;
	}
	public String getAcContext() {
		return acContext;
	}
	public void setAcContext(String acContext) {
		this.acContext = acContext;
	}
	public byte getAcStatus() {
		return acStatus;
	}
	public void setAcStatus(byte acStatus) {
		this.acStatus = acStatus;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(acId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticlesVO other = (ArticlesVO) obj;
		return Objects.equals(acId, other.acId);
	}

	@Override
	public String toString() { 		// +", = "	
		return "Articles [討論區文章編號 = "                   + acId + 
					   ", 文章標題 = "                        + acTitle + 
					   ", 露營者編號 = "                      + memId + 
					   ", 文章類別編號 = "                    + acTypeId +
					   ", 文章發布時間 = "                    + acTime + 
					   ", 文章內容 = "                        + acContext + 
					   ", 文章狀態   0 : 顯示  1 : 不顯示 = "  + acStatus + "]";
	}																																																

}
