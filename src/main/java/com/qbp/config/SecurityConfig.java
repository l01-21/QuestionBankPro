package com.qbp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Security 配置.
 */
@Configuration
public class SecurityConfig {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        // 不需要保护的资源路径允许匿名访问
        registry.antMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0])).permitAll();
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        registry.and().authorizeRequests().anyRequest().authenticated() // 除上面外的所有请求全部需要鉴权认证
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 关闭跨站请求防护及不使用session
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
