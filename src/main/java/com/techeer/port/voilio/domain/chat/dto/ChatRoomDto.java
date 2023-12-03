package com.techeer.port.voilio.domain.chat.dto;

import com.techeer.port.voilio.domain.user.dto.UserDto;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {
  private Long id;
  private UserDto fromUser;
  private UserDto toUser;
  private UUID roomUuid;
  private String roomName;
}
