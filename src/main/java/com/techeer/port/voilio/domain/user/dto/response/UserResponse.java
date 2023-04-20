package com.techeer.port.voilio.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
  private Long id;
  private String email;
  private String password;
  private String nickname;
}
