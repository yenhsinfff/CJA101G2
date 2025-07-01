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

        // 露營者 
        registry.addInterceptor(memberLoginInterceptor)
                .addPathPatterns("/api/member/**")
                .excludePathPatterns("/", "/index","/login",
                    "/mem/login", "/mem/register", "/mem/forgot",
                    "/css/**", "/js/**", "/images/**"
                );

        // 營地主
        registry.addInterceptor(ownerLoginInterceptor) 
                .addPathPatterns("api/owner/**")
                .excludePathPatterns("/", "/index","/login",
                    "/owner/login", "/owner/register",
                    "/css/**", "/js/**", "/images/**"
                );

        // 管理員
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("api/admin/**")
                .excludePathPatterns("/", "/index","/login",
                    "/admin/login",
                    "/css/**", "/js/**", "/images/**"
                );
    }
}
