package com.lutu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MemberLoginInterceptor memberLoginInterceptor;

    @Autowired
    private OwnerLoginInterceptor ownerLoginInterceptor;

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //露營者 
        registry.addInterceptor(memberLoginInterceptor)
                .addPathPatterns("/api/member/**")
                .excludePathPatterns(
                		"/api/member/login", 
                		"/api/member/logout",
                		"/api/member/update",
                		"/api/member/changePassword",
                		"/api/member/register",
                		"/api/member/verify",
                		"/api/auth/forgot-password",
                        "/api/auth/reset-password"
                );

        //營地主
        registry.addInterceptor(ownerLoginInterceptor) 
                .addPathPatterns("/api/owner/**")
                .excludePathPatterns(
                		"/api/owner/login",
                		"/api/owner/logout",
                		"/api/owner/update",
                		"/api/owner/changePassword",
                        "/api/owner/register",
                        "/api/owner/verify",
                        "/api/owner/avatar/**",
                        "/api/owner/all",
                        "/api/owner/update-status/**",
                		"/api/auth/forgot-password",
                        "/api/auth/reset-password",
                        "/api/owner/profile"
                );

        //管理員
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns(
                		"/api/admin/login",
                		"/api/admin/logout",
                		"/api/admin/all",
                		"/api/admin/add",
                		"/api/admin/update-status/**",
                		"/api/auth/forgot-password",
                        "/api/auth/reset-password"
                );
    }
}