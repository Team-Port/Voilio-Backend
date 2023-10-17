package com.techeer.port.voilio.domain.user.dto.request;

import lombok.*;

@Getter
@Setter
public class UserSignUpRequest {

  private String email;
  private String password;
  private String nickname;
}
