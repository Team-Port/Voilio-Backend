package com.techeer.port.voilio.global.config.security.jwtCheck;

import com.techeer.port.voilio.global.config.security.JwtAuthenticationFilter;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
  WebSecurityConfig 에서의 사용을 위해
 */

@RequiredArgsConstructor
public class JwtSecurityConfig
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtProvider jwtProvider;

  @Override
  public void configure(HttpSecurity http) {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider);

    // Spring Security 는 기본적으로 세션 쿠키 방식의 인증이 이루어진다. 이 인증이 이루어지는 Filter = UsernamePasswordAuthenticationFilter
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
