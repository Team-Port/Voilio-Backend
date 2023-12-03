package com.techeer.port.voilio.domain.chat.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomRequest {

  @NotNull private Long fromUserId;

  @NotNull private Long toUserId;

  private String roomName;
}
