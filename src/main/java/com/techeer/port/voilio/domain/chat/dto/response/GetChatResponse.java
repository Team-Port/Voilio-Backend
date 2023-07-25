package com.techeer.port.voilio.domain.chat.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GetChatResponse {
  private Long userId;
  private String message;
  private LocalDateTime time;
}
