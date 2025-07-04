package com.lutu.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class OwnerLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false; // 不繼續進入 controller
        }
        
        HttpSession session = request.getSession(false);
        Object owner = session != null ? session.getAttribute("loggedInOwner") : null;

        System.out.println("[Interceptor] Owner session 檢查: " + (owner != null));

        if (owner == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Interceptor 攔截：請先登入");
            return false;
        }

        return true;
    }
}
