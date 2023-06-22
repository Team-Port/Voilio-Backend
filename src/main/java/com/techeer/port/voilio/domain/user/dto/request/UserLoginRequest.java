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

  public UsernamePasswordAuthenticationToken toAuthentication() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
