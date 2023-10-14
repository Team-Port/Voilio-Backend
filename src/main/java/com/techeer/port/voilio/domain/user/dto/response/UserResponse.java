package com.techeer.port.voilio.domain.user.dto.response;

import com.techeer.port.voilio.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
  private Long id;
  private String email;
  private String nickname;

  public static UserResponse of(User user) {
    return UserResponse.builder().email(user.getEmail()).nickname(user.getNickname()).build();
  }
}
