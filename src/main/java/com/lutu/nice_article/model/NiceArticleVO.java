package com.lutu.nice_article.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class NiceArticleVO implements java.io.Serializable{

	
	private Integer	acId;				// 討論區文章編號 (PK, FK)
	private Integer	memId;				// 露營者編號     (PK, FK)
	private LocalDateTime	likeTime;	// 按讚時間
	
	
	
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
	public LocalDateTime getLikeTime() {
		return likeTime;
	}
	public void setLikeTime(LocalDateTime likeTime) {
		this.likeTime = likeTime;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(acId,memId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NiceArticleVO other = (NiceArticleVO) obj;
		return Objects.equals(acId, other.acId) &&
			   Objects.equals(memId, other.memId);
	}
		
	
	@Override
	public String toString() {		// +", = "
		return "NiceArticleVO [討論區文章編號 = "     + acId + 
							", 露營者編號 = "        + memId + 
							", 按讚時間 = "          + likeTime + "]";
		
		
		
	}
	
	
}
