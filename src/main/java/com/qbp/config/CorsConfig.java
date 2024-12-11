package com.qbp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cors策略
 */
@Slf4j
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("cors---正在初始化...");
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // 设置允许跨域请求的域名
                .allowedHeaders("*") // 设置允许的请求头
                .allowCredentials(true) // 是否允许证书
                .allowedMethods("*") // 允许的方法
                .maxAge(3600); // 跨域允许时间
    }
}
