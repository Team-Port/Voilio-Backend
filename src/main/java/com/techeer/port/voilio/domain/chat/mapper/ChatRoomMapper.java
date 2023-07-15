package com.techeer.port.voilio.domain.chat.mapper;

import com.techeer.port.voilio.domain.chat.dto.response.GetChatRoomResponse;
import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {
  public ChatRoom toEntity(User user1, User user2) {
    return ChatRoom.builder()
        .user1(user1)
        .user2(user2)
        .roomName(user1.getNickname() + "," + user2.getNickname() + "의 방")
        .build();
  }

  public GetChatRoomResponse toDto(ChatRoom chatRoom) {
    return GetChatRoomResponse.builder()
        .chatRoomUuid(chatRoom.getRoomUuid())
        .chatRoomName(chatRoom.getRoomName())
        .user1NickName(chatRoom.getUser1().getNickname())
        .user2NickName(chatRoom.getUser2().getNickname())
        .build();
  }

  public List<GetChatRoomResponse> toDto(List<ChatRoom> chatRooms) {
    return chatRooms.stream().map(this::toDto).collect(Collectors.toList());
  }
}
