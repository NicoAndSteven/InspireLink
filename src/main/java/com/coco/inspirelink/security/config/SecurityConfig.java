package com.coco.inspirelink.security.config;

import com.coco.inspirelink.common.right.Role;
import com.coco.inspirelink.security.filter.JwtAuthFilter;
import com.coco.inspirelink.security.service.WechatOAuth2UserService;
import com.coco.inspirelink.utils.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/20 下午2:55
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private WechatOAuth2UserService wechatOAuth2UserService;
    @Resource
    private JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 允许匿名访问登录端点
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/wechat/callback").permitAll()
                        .anyRequest().authenticated()
                )
                // 表单登录配置
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .successHandler(jwtAuthSuccessHandler())
                )
                // OAuth2微信登录
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(wechatOAuth2UserService)
                        )
                        .successHandler(jwtAuthSuccessHandler())
                )
                // JWT验证过滤器
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtTokenProvider);
    }

    @Bean
    public AuthenticationSuccessHandler jwtAuthSuccessHandler() {
        return (request, response, authentication) -> {
            String token = jwtTokenProvider.generateToken(authentication.getName(), Role.VISITOR.getLevel());
            response.setContentType("application/json");
            response.getWriter().write("{\"token\":\"" + token + "\"}");
        };
    }
}
