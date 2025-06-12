package com.lutu.article_type.model;

import java.util.Objects;


public class ArticleTypeVO implements java.io.Serializable{

	private Integer acTypeId;      //文章類別編號 (PK)
	private String	acTypeKind;    //文章類別名稱
	private String acTypeText;     //文章類別敘述
	
	
	public Integer getAcTypeId() {
		return acTypeId;
	}
	public void setAcTypeId(Integer acTypeId) {
		this.acTypeId = acTypeId;
	}
	public String getAcTypeKind() {
		return acTypeKind;
	}
	public void setAcTypeKind(String acTypeKind) {
		this.acTypeKind = acTypeKind;
	}
	public String getAcTypeText() {
		return acTypeText;
	}
	public void setAcTypeText(String acTypeText) {
		this.acTypeText = acTypeText;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(acTypeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleTypeVO other = (ArticleTypeVO) obj;
		return Objects.equals(acTypeId, other.acTypeId);
	}

	@Override
	public String toString() {
		return "ArticleType [文章類別編號 = "   + acTypeId +
						  ", 文章類別名稱 = " + acTypeKind + 
					      ", 文章類別敘述 = " + acTypeText+ "]";
	}
	
	
	

}
