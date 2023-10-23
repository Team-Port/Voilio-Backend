package com.techeer.port.voilio.domain.user.dto.request;

import lombok.*;

@Getter
public class UserLoginRequest {
  private String email;
  private String password;
}
