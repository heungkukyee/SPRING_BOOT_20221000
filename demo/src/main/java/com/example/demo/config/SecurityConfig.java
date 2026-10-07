package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 설정 클래스 등록
@EnableWebSecurity // 스프링 시큐리티 활성화
@EnableMethodSecurity // [6주차] 메서드 보안 활성화 : @PreAuthorize 사용
public class SecurityConfig {
  @Bean // 비밀번호 암호화 객체 등록 (BCrypt 해시)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean // 보안 필터 체인 등록
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // 1. URL 접근 규칙 (인가)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/hello", "/detailed_web.html",
                "/login", "/signup", "/error")
            .permitAll()
            .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN") // [6주차] 관리자만
            .anyRequest().authenticated())
        // 2. 폼 로그인 설정 (인증)
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error")
            .permitAll())
        // 3. 로그아웃 설정
        .logout(logout -> logout
            .logoutUrl("/logout") // 로그아웃 처리 URL (POST)
            .logoutSuccessUrl("/login?logout") // 로그아웃 후 이동
            .invalidateHttpSession(true) // 세션 삭제
            .deleteCookies("JSESSIONID", "remember-me")); // 쿠키 삭제
    return http.build();
  }
}
