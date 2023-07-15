package com.techeer.port.voilio.domain.user.dto.request;

import com.techeer.port.voilio.domain.user.entity.Authority;
import com.techeer.port.voilio.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserSignUpRequest {
  private Long id;
  private String email;
  private String password;
  private String nickname;
  private LocalDateTime activatedAt;

  public User toEntity(PasswordEncoder passwordEncoder) {
    return User.builder()
        .id(id)
        .email(email)
        .password(passwordEncoder.encode(password))
        .nickname(nickname)
        .activatedAt(LocalDateTime.now())
        .authority(Authority.ROLE_USER)
        .build();
  }
}
