package com.techeer.port.voilio.domain.chat.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomRequest {
  private String nickName;
  private long chatUserId;
}
