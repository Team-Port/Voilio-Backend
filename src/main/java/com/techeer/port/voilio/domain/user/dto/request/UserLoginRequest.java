package com.techeer.port.voilio.domain.user.dto.request;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class UserLoginRequest {
  private String email;
  private String password;
}
