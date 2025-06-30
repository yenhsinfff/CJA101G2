package com.lutu.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OwnerLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object owner = request.getSession().getAttribute("loggedInOwner");
        if (owner == null) {
            response.sendRedirect(request.getContextPath()+ "/owner/login");
            return false;
        }
        return true;
    }
}
