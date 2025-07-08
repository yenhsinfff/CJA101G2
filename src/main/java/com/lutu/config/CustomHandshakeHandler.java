package com.lutu.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	@Override
	protected Principal determineUser(
	    ServerHttpRequest request,
	    WebSocketHandler wsHandler,
	    Map<String, Object> attributes) {

	    String memId = "guest";

	    if (request instanceof ServletServerHttpRequest servletRequest) {
	        String query = servletRequest.getServletRequest().getQueryString();
	        if (query != null && query.contains("memId=")) {
	            memId = query.replaceAll(".*memId=([^&]+).*", "$1");
	        }
	    }

	    String finalMemId = memId;
	    System.out.println("CustomHandshakeHandler: " + finalMemId);

	    return () -> finalMemId;
	}

}

