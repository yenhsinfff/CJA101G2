package com.lutu.camp_chatroom.Controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lutu.camp_chatroom.model.CampChatRoomVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

public class TestRedisChatRoom {

    public static void main(String[] args) {
        // createChatMsg();
        readChatRoom();
        // 一般字串資料
        // jedis.set("myKey", "Hello, Redis~");
        // System.out.println("myKey is: " + jedis.get("myKey"));

        // jedis.append("myKey", "安安你好");
        // System.out.println("Append result: " + jedis.get("myKey"));
        // System.out.println("myKey's length: " + jedis.strlen("myKey"));
        // System.out.println("Get range: " + jedis.getrange("myKey", 7, 11));
        // System.out.println("Get range(2): " + jedis.getrange("myKey", -18, -14));

    }

    // 建立一筆 CampChatRoomVO 物件
    private static CampChatRoomVO createChatMsg(int chatroomMsgId, int memId, int ownerId, byte direct, String timeStr,
            String content) {
        CampChatRoomVO vo = new CampChatRoomVO();
        vo.setChatroomMsgId(chatroomMsgId);
        vo.setMemId(memId);
        vo.setOwnerId(ownerId);
        vo.setChatMsgDirect(direct);
        vo.setChatMsgTime(Timestamp.valueOf(timeStr));
        vo.setChatMsgContent(content);
        return vo;
    }

    public static void createChatMsg() {
        Jedis jedis = new Jedis("localhost", 6379);

        ObjectMapper mapper = new ObjectMapper();
        // 聊天室編號
        String chatId = "70000003";

        // 2. 建立聊天記錄 List
        List<CampChatRoomVO> chatList = new ArrayList<>();

        // 模擬資料：每一筆記錄一個 Map
        chatList.add(createChatMsg(70000003, 10000002, 20000001, (byte) 0, "2024-01-01 00:00:10", "請問你們的這個營地有方便的地方嗎?"));
        chatList.add(createChatMsg(70000003, 10000002, 20000001, (byte) 1, "2024-01-02 08:05:12", "沒有廁所，因此價格已經降低了"));
        chatList.add(
                createChatMsg(70000003, 10000002, 20000001, (byte) 1, "2024-01-02 08:05:30", "但是這個營地旁邊有條河所以很方便喔!"));
        chatList.add(
                createChatMsg(70000003, 10000002, 20000001, (byte) 0, "2024-01-02 12:15:46", "好的感謝……那麼請問營地可以帶外食跟飲料嗎?"));
        chatList.add(
                createChatMsg(70000003, 10000002, 20000001, (byte) 1, "2024-01-02 13:44:24", "食物可以，但是飲料酌收開瓶費。500/每包裝"));
        chatList.add(createChatMsg(70000003, 10000002, 20000001, (byte) 0, "2024-01-02 16:22:16", "那天突然有事不太方便"));
        chatList.add(createChatMsg(70000003, 10000002, 20000001, (byte) 0, "2024-01-02 16:22:24", "下次一定 ^^"));

        // 轉換成 JSON 字串存入 Redis
        String jsonValue = "";
        try {
            // jsonValue = mapper.writeValueAsString(chatList);
            jsonValue = new JSONArray(chatList).toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        jedis.set(chatId, jsonValue);
        System.out.println(chatId + "聊天室建立完成!");
    }

    public static void readChatRoom() {
        // 1. 建立連線
        Jedis jedis = new Jedis("localhost", 6379);

        // 2. 從 Redis 取得 JSON 字串
        String chatRoomId = "70000003";
        String json = jedis.get(chatRoomId);
        if (json == null) {
            System.out.println("❌ 查無資料，Key: " + chatRoomId);
            return;
        }

        // 3. 使用 Jackson 將 JSON 字串轉為 List<CampChatRoomVO>
        // ObjectMapper mapper = new ObjectMapper();
        // List<CampChatRoomVO> chatList = null;
        // try {
        // chatList = mapper.readValue(json, new TypeReference<List<CampChatRoomVO>>() {
        // });
        // } catch (JsonMappingException e) {
        // System.out.println("JsonMappingException:" + e);
        // e.printStackTrace();
        // } catch (JsonProcessingException e) {
        // System.out.println("JsonProcessingException:" + e);
        // e.printStackTrace();
        // }

        List<CampChatRoomVO> chatList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            CampChatRoomVO vo = new CampChatRoomVO();
            vo.setChatroomMsgId(obj.getInt("chatroomMsgId"));
            vo.setMemId(obj.getInt("memId"));
            vo.setOwnerId(obj.getInt("ownerId"));
            vo.setChatMsgDirect((byte) obj.getInt("chatMsgDirect"));
            vo.setChatMsgTime(Timestamp.valueOf(obj.getString("chatMsgTime")));
            vo.setChatMsgContent(obj.getString("chatMsgContent"));

            chatList.add(vo);
        }

        // 🔽 依照訊息時間由早到晚排序
        if (chatList != null) {
            chatList.sort((a, b) -> a.getChatMsgTime().compareTo(b.getChatMsgTime()));
            // 4. 使用 for-each 印出每一筆聊天記錄
            for (CampChatRoomVO vo : chatList) {
                System.out.println("====== 聊天記錄 ======");
                System.out.println("聊天室編號: " + vo.getChatroomMsgId());
                System.out.println("露營者編號: " + vo.getMemId());
                System.out.println("營地主編號: " + vo.getOwnerId());
                System.out.println("發送方向: " + vo.getChatMsgDirect());
                System.out.println("發送時間: " + vo.getChatMsgTime());
                System.out.println("訊息內容: " + vo.getChatMsgContent());
                System.out.println();
                if (vo.getChatMsgDirect() == 0) {
                    System.out.println("💬 露營者對營地主說：" + vo.getChatMsgContent() + "在" + vo.getChatMsgTime());
                } else {
                    System.out.println("💬 營地主對露營者" + vo.getChatMsgContent() + "在" + vo.getChatMsgTime());
                }
            }
        }
    }

}
