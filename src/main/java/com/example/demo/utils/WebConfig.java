package com.example.demo.utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user") // /user 엔드포인트에 대해서만 CORS 설정 적용
                .allowedOriginPatterns("*") // 모든 외부 사용자에게 허용 (보안 주의)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 지정
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(false); // 자격 증명 (쿠키 등) 허용
    }
}