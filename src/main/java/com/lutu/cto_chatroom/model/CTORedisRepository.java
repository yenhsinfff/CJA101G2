package com.lutu.cto_chatroom.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CTORedisRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getRoomKey(Integer memId, Integer ownerId) {
        // 用戶ID排序確保唯一性
        return "CTOROOM:" + Math.min(memId, ownerId) + ":" + Math.max(memId, ownerId);
    }

    // 儲存訊息
    public void saveMessage(Integer memId, Integer ownerId, CTOChatRoomVO message) {
        String key = getRoomKey(memId, ownerId);
        redisTemplate.opsForList().rightPush(key, message);
    }

    // 取得歷史訊息
    public List<Object> getMessages(Integer memId, Integer ownerId) {
        String key = getRoomKey(memId, ownerId);
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}

