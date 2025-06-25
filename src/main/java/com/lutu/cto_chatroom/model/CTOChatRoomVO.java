package com.lutu.cto_chatroom.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CTOChatRoomVO {

	//private Integer chatroomMsgId; // 聊天室編號
    private Integer memId; // 露營者編號
    private Integer ownerId; // 營地主編號
    private Byte chatMsgDirect; // 訊息發送方向 "0: 露營者會員對營地主會員 1: 營地主會員對露營者會員"
    private Long chatMsgTime; // 訊息發送時間
    private String chatMsgContent; // 訊息內容
    private Integer status; // 0=已讀, 1=未讀

//    public Integer getChatroomMsgId() {
//        return chatroomMsgId;
//    }
//
//    public void setChatroomMsgId(Integer chatroomMsgId) {
//        this.chatroomMsgId = chatroomMsgId;
//    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Byte getChatMsgDirect() {
        return chatMsgDirect;
    }

    public void setChatMsgDirect(byte chatMsgDirect) {
        this.chatMsgDirect = chatMsgDirect;
    }

    public Long getChatMsgTime() {
        return chatMsgTime;
    }

    public void setChatMsgTime(Long chatMsgTime) {
        this.chatMsgTime = chatMsgTime;
    }

    public String getChatMsgContent() {
        return chatMsgContent;
    }

    public void setChatMsgContent(String chatMsgContent) {
        this.chatMsgContent = chatMsgContent;
    }
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
