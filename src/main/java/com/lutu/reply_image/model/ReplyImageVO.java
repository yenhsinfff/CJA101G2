package com.lutu.reply_image.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;

import com.lutu.reply.model.ReplyVO;

@Entity
@Table(name = "reply_image")
public class ReplyImageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer replyImgId;	 // 留言圖片編號(PK)
    private ReplyVO replyVO;	 // 留言編號(FK) 
    private byte[] replyImg;	 // 圖片訊息

    public ReplyImageVO() {
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_img_id")
    public Integer getReplyImgId() {
        return replyImgId;
    }

    public void setReplyImgId(Integer replyImgId) {
        this.replyImgId = replyImgId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reply_id", nullable = false)
    @NotNull(message = "留言: 必須指定所屬留言")
    public ReplyVO getReplyVO() {
        return replyVO;
    }

    public void setReplyVO(ReplyVO replyVO) {
        this.replyVO = replyVO;
    }

    @Column(name = "reply_img", nullable = false, columnDefinition = "longblob")
    @NotNull(message = "圖片: 請上傳圖片")
    public byte[] getReplyImg() {
        return replyImg;
    }

    public void setReplyImg(byte[] replyImg) {
        this.replyImg = replyImg;
    }

	@Override
	public String toString() {
		return "ReplyImageVO [replyImgId=" + replyImgId + ", replyVO=" + replyVO + ", replyImg="
				+ Arrays.toString(replyImg) + "]";
	}
    
    
}

