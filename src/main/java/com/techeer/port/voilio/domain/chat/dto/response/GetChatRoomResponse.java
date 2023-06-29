package com.techeer.port.voilio.domain.chat.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetChatRoomResponse {
  private UUID chatRoomUuid;
  private String chatRoomName;
  private String user1NickName;
  private String user2NickName;
}
