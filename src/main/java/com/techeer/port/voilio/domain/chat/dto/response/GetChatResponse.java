package com.techeer.port.voilio.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class GetChatResponse {
  private Long userId;
  private String message;
  private LocalDateTime time;
}
