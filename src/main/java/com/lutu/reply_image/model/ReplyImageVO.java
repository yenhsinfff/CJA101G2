package com.lutu.reply_image.model;

import java.util.Objects;

import com.lutu.reply.model.ReplyVO;

public class ReplyImageVO implements java.io.Serializable{
	
	private Integer replyImgId;		// 留言圖片編號(PK)
	private Integer	replyId;		// 留言編號
	private byte[]	replyImg;		// 圖片訊息
	
	
	
	public Integer getReplyImgId() {
		return replyImgId;
	}
	public void setReplyImgId(Integer replyImgId) {
		this.replyImgId = replyImgId;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public byte[] getReplyImg() {
		return replyImg;
	}
	public void setReplyImg(byte[] replyImg) {
		this.replyImg = replyImg;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(replyImgId);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplyImageVO other = (ReplyImageVO) obj;
		return Objects.equals(replyImgId, other.replyImgId);
	}
	
	
	@Override
	public String toString() {		// +", = "
		return "ReplyImage [留言圖片編號 = " + replyImgId + 
						 ", 留言編號 = "     + replyId + 
						 ", 圖片訊息 = "     + replyImg + "]";
	}
	

}
