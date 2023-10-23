package com.techeer.port.voilio.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Top5LatestUserResponseDto {
  private Long id;
  private String email;
  private String nickname;
  private LocalDateTime createdAt;
}
