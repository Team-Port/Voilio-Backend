package com.techeer.port.voilio.global.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwsToken {
  private String grantType;
  private String accessToken;
  private String refreshToken;
}
