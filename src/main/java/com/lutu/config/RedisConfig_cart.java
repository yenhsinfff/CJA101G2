//package com.lutu.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.lutu.cart.model.CartVO;
//
//@Configuration
//public class RedisConfig_cart {
//
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate1(RedisConnectionFactory connectionFactory) {
//		RedisTemplate<String, Object> template = new RedisTemplate<>();
//	    template.setConnectionFactory(connectionFactory);
//
//	    // Key、HashKey、HashValue、Value 全部都用 String
//	    template.setKeySerializer(new StringRedisSerializer());
//	    template.setHashKeySerializer(new StringRedisSerializer());
//	    template.setHashValueSerializer(new StringRedisSerializer());
//	    template.setValueSerializer(new StringRedisSerializer());
//	    return template;
//
//	}
//
//}
