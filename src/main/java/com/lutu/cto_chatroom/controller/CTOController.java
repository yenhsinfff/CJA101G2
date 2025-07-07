package com.lutu.cto_chatroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.campsite_order.model.CampsiteOrderDTO;
import com.lutu.cto_chatroom.model.CTOChatRoomVO;
import com.lutu.cto_chatroom.model.CTOChatService;
import com.lutu.cto_chatroom.model.CTORedisRepository;

@RestController
public class CTOController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private CTOChatService ctoSrv;

	// 發送訊息
	@MessageMapping("/chat.send")
	public void sendMessage(CTOChatRoomVO message) {
//        message.setChatMsgTime(System.currentTimeMillis());
//        message.setStatus(1); // 初始為未讀

		// 儲存訊息到 Redis
		ctoSrv.createMsg(message.getMemId(), message.getOwnerId(), message);

		// 推送訊息給對方（即時顯示）
		messagingTemplate.convertAndSendToUser(message.getMemId().toString(), "/queue/messages", message);
	}

	// 已讀回執
	@MessageMapping("/chat.read")
	public void readMessage(CTOChatRoomVO message) {
		// 更新資料庫中該訊息狀態為已讀
		message.setStatus(0);
		// 通知發送者該訊息已讀
		messagingTemplate.convertAndSendToUser(message.getMemId().toString(), "/queue/read", message);
	}

	// 讀取歷史資料
//    @MessageMapping("/chat.history")
//    @SendToUser("/queue/history")
//    public void chatHistory(CTOChatRoomVO message) {
//
//        // 取得歷史訊息
//        List<CTOChatRoomVO> historyList = ctoSrv.readChatMsg(message.getMemId(), message.getOwnerId());
//
//        // 推送每一筆歷史訊息給使用者自己（接收方）
//        for (CTOChatRoomVO msg : historyList) {
//        	System.out.println("history:"+msg.getChatMsgContent());
//            messagingTemplate.convertAndSendToUser(
//            		message.getMemId().toString(), "/queue/history", msg
//            );
//        }
//    }

	@MessageMapping("/chat.history")
	@SendToUser("/queue/history")
	public List<CTOChatRoomVO> chatHistory(CTOChatRoomVO message) {
		return ctoSrv.readChatMsg(message.getMemId(), message.getOwnerId());
	}

	@PostMapping("/cto/getonecto")
	public ApiResponse<List<CTOChatRoomVO>> getChatListByOwner(@RequestParam("userId") Integer userId) {
		try {
			List<CTOChatRoomVO> voList = ctoSrv.getChatListByOwnerId(userId);
			return new ApiResponse<>("success", voList, "查詢成功");
		} catch (Exception e) {
			System.out.println("cto_getonecto_err:"+e);
			return new ApiResponse<>("fail", null, "查詢失敗");
		}
	}

}
