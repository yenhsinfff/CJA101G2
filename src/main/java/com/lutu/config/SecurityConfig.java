package com.lutu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configure(http)).csrf(csrf -> csrf.disable()) // ✅ 若使用 RESTful API 建議關閉
				.authorizeHttpRequests(
						authz -> authz
						//預設攔截，例外通行
//								.requestMatchers("/api/getallcamps", "/campsitetype/**", "/camptracklist/**", "/api/**",
//										"/campsite/**", "/bundleitem/**", "/member/getallmembers", "/CJA101G02/**",
//										"/static/**", "/index.html", "/css/**","/js/**","/data/**","/images/**","/#")
//								.permitAll()
//						.anyRequest().authenticated());
								//預設開放，設定攔截
								.requestMatchers("/api/admin/**","/*/getonemember").authenticated()
								.anyRequest().permitAll());
								
		return http.build();
	}

}
