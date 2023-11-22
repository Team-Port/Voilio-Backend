package com.techeer.port.voilio.domain.chat.dto;

import com.techeer.port.voilio.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoomDto {
    private Long id;
    private UserDto fromUser;
    private UserDto toUser;
    private UUID roomUuid;
    private String roomName;
}
