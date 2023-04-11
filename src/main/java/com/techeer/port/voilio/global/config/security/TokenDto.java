package com.techeer.port.voilio.global.config.security;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
  private String grantType;
  private String accessToken;
  private Long tokenExpiresIn;
}
