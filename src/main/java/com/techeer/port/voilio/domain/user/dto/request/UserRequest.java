package com.techeer.port.voilio.domain.user.dto.request;

import com.techeer.port.voilio.domain.user.entity.Authority;
import com.techeer.port.voilio.domain.user.entity.User;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        .authority(Authority.ROLE_USER)
        .build();
  }

  public User toEntity(PasswordEncoder passwordEncoder) {
    return User.builder()
        .id(id)
        .email(email)
        .password(passwordEncoder.encode(password))
        .nickname(nickname)
        .authority(Authority.ROLE_USER)
        .build();
  }

  public void setUserPassword(String encodedPassword) {
    this.password = encodedPassword;
  }

  public UsernamePasswordAuthenticationToken toAuthentication() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
