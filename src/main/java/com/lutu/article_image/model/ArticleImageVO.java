package com.lutu.article_image.model;

import java.util.Objects;

import com.lutu.article_type.model.ArticleTypeVO;

public class ArticleImageVO implements java.io.Serializable{
	
	private Integer acImgId;	// 討論區圖片編號(PK)
	private Integer acId;		// 討論區文章編號
	private byte[] ac_img;		// 圖片訊息
	
	
	
	public Integer getAcImgId() {
		return acImgId;
	}
	public void setAcImgId(Integer acImgId) {
		this.acImgId = acImgId;
	}
	public Integer getAcId() {
		return acId;
	}
	public void setAcId(Integer acId) {
		this.acId = acId;
	}
	public byte[] getAc_img() {
		return ac_img;
	}
	public void setAc_img(byte[] ac_img) {
		this.ac_img = ac_img;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(acImgId);
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        ArticleImageVO other = (ArticleImageVO) obj;
        return Objects.equals(acImgId, other.acImgId);
    }

	@Override
	public String toString() {		// +", = "
		return "ArticleImage [討論區圖片編號 = " + acImgId + 
						   ", 討論區文章編號 = " + acId + 
						   ", 圖片訊息 = "       + ac_img + "]";
	}
	

}
