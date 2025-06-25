package com.lutu.cto_chatroom.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("cTOChatService")
public class CTOChatService {
	@Autowired
	CTORedisRepository ctoRedisRepository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void createMsg(int memId, int ownerId,CTOChatRoomVO vo) {
//		CTOChatRoomVO vo = new CTOChatRoomVO();
//		
//		vo.setMemId(memId);
//		vo.setOwnerId(ownerId);
//		vo.setChatMsgDirect((byte) 0);
//		vo.setChatMsgTime( System.currentTimeMillis());
//		vo.setChatMsgContent(content);
//		vo.setStatus(0);
		
		// 模擬資料：每一筆記錄一個 Map
		ctoRedisRepository.saveMessage(memId,ownerId,vo);
		System.out.println( "聊天建立完成!");
	}
	
	public void createFakeMsg() {
		// 模擬資料：每一筆記錄一個 Map
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 0, "2024-01-01 00:00:10", "請問你們的這個營地有方便的地方嗎?"));
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 1, "2024-01-02 08:05:12", "沒有廁所，因此價格已經降低了"));
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 1, "2024-01-02 08:05:30", "但是這個營地旁邊有條河所以很方便喔!"));
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 0, "2024-01-02 12:15:46", "好的感謝……那麼請問營地可以帶外食跟飲料嗎?"));
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 1, "2024-01-02 13:44:24", "食物可以，但是飲料酌收開瓶費。500/每包裝"));
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 0, "2024-01-02 16:22:16", "那天突然有事不太方便"));
		ctoRedisRepository.saveMessage(10000002, 20000001,
				createChatMsg( 10000002, 20000001, (byte) 0, "2024-01-02 16:22:24", "下次一定 ^^"));
		System.out.println( "聊天室建立完成!");
	}

	public List<CTOChatRoomVO> readChatMsg(int memId, int ownerId) {
		List<Object> msgObjList = ctoRedisRepository.getMessages(memId, ownerId);
		List<CTOChatRoomVO> voList = new ArrayList<>();
		for (Object obj : msgObjList) {
			if (obj instanceof CTOChatRoomVO) {
				voList.add((CTOChatRoomVO) obj);
			}
		}
		return voList;
	}

	// 建立一筆 CampChatRoomVO 物件
	private static CTOChatRoomVO createChatMsg( int memId, int ownerId, byte direct, String timeStr,
			String content) {
		CTOChatRoomVO vo = new CTOChatRoomVO();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(timeStr, formatter);
		long millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

//		vo.setChatroomMsgId(chatroomMsgId);
		vo.setMemId(memId);
		vo.setOwnerId(ownerId);
		vo.setChatMsgDirect(direct);
		vo.setChatMsgTime(millis);
		vo.setChatMsgContent(content);
		vo.setStatus(0);
		return vo;
	}

}
