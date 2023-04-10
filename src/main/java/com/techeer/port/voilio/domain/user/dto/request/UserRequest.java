package com.techeer.port.voilio.domain.user.dto.request;

import com.techeer.port.voilio.domain.user.entity.User;
import java.util.Collections;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserRequest {
  private Long id;
  private String email;
  private String password;
  private String nickname;

  public User toEntity() {

    return User.builder()
        .id(id)
        .email(email)
        .password(password)
        .nickname(nickname)
        .roles(Collections.singletonList("ROLE_USER"))
        .build();
  }

  public void setUserPassword(String encodedPassword) {
    this.password = encodedPassword;
  }
}
