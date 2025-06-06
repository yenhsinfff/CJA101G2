package com.lutu.ac_fav_record.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class AcFavRecordVO implements java.io.Serializable{

	
	private	Integer	acId;				// 討論區文章編號 (PK, FK)
	private	Integer	memId;				// 露營者編號 (PK, FK)
	private	LocalDateTime	acFavTime;	// 收藏時間
	
	
	
	public Integer getAcId() {
		return acId;
	}
	public void setAcId(Integer acId) {
		this.acId = acId;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public LocalDateTime getAcFavTime() {
		return acFavTime;
	}
	public void setAcFavTime(LocalDateTime acFavTime) {
		this.acFavTime = acFavTime;
	}
	
	
	
	@Override
	public int hashCode() {
	    return Objects.hash(acId, memId);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    AcFavRecordVO other = (AcFavRecordVO) obj;
	    return Objects.equals(acId, other.acId) &&
	           Objects.equals(memId, other.memId);
	}
	
	
	@Override
	public String toString() {		// +", = "
		return "AcFavRecord [討論區文章編號 = " + acId + 
						  ", 露營者編號 = "     + memId + 
						  ", 收藏時間 = "       + acFavTime + "]";
	}
	
	
	
	
	
}
