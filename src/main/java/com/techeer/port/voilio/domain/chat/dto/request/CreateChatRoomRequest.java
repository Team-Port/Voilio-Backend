package com.techeer.port.voilio.domain.chat.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CreateChatRoomRequest {

  @NotNull
  private Long fromUserId;

  @NotNull
  private Long toUserId;

  private String roomName;
}
