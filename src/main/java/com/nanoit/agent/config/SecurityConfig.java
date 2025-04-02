package com.nanoit.agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// 임포트 이름 확인!
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 여기서 new BCryptPasswordEncoder() 로 정확히 사용되었는지 확인!
        return new BCryptPasswordEncoder();
    }
}