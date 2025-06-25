package com.lutu.camp_chatroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.camp_chatroom.model.CampChatRoomVO;
import com.lutu.camp_chatroom.model.ChatRedisRepository;

@RestController
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ChatRedisRepository chatRedisRepository;

    // 發送訊息
    @MessageMapping("/chat.send")
    public void sendMessage(CampChatRoomVO message) {
        message.setTimestamp(System.currentTimeMillis());
        message.setStatus(1); // 初始為未讀
        
     // 儲存訊息到 Redis
        chatRedisRepository.saveMessage(message.getMemId(), message.getOwnerId(), message);

        // 推送訊息給對方（即時顯示）
        messagingTemplate.convertAndSendToUser(
            message.getOwnerId().toString(), "/queue/messages", message
        );
    }

    // 已讀回執
    @MessageMapping("/chat.read")
    public void readMessage(CampChatRoomVO message) {
        // 更新資料庫中該訊息狀態為已讀
        message.setStatus(0);
        // 通知發送者該訊息已讀
        messagingTemplate.convertAndSendToUser(
            message.getOwnerId().toString(), "/queue/read", message);
    }
}

