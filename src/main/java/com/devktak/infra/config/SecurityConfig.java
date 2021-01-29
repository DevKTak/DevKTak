package com.devktak.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // SpringSecurity 직접 설정
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurity 설정을 좀 더 손쉽게 하기 위한 상속

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "login", "sign-up").permitAll() // 권한 확인없이 접근 해야 할 요청들
                .anyRequest().authenticated(); // 나머지 요청은 로그인 (인증받은 사용자) 해야만 사용 가능

        http.formLogin() // Spring Security의 기본 로그인 화면
                .loginPage("/login").permitAll(); // 커스텀한 페이지를 로그인 페이지로 사용

        http.logout()
                .logoutSuccessUrl("/"); // 로그아웃 성공 시 url
    }

    /**
     * 스태틱한 리소스들은 인증을 하지 않게(시큐리티 필터를 적용하지 말아라) 하는 로직
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
