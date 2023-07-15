package com.techeer.port.voilio.domain.user.dto.request;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserLoginRequest {
  private String email;
  private String password;

  // UsernamePasswordAuthenticationToken 은 AbstractAuthenticationToken 을 상속 받는다.
  // AbstractAuthenticationToken 은 Authentication 을 상속받는다
  // 즉, UsernamePasswordAuthenticationToken 은 추후 인증이 끝나고 SecurityContextHolder.getContext()에 등록될
  // Authentication 객체이다.
  public UsernamePasswordAuthenticationToken toAuthentication() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
