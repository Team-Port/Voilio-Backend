package com.techeer.port.voilio.domain.chat.service;

import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
import com.techeer.port.voilio.domain.chat.dto.response.GetChatRoomResponse;
import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
import com.techeer.port.voilio.domain.chat.exception.NotFoundChatRoom;
import com.techeer.port.voilio.domain.chat.mapper.ChatRoomMapper;
import com.techeer.port.voilio.domain.chat.repo.ChatRoomRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final UserService userService;
  private final ChatRoomMapper chatRoomMapper;

  public ChatRoom createChatRoom(CreateChatRoomRequest request) {
    User user1 = userService.getUser(request.getNickName());
    User user2 = userService.getUser(request.getChatUserId());
    return chatRoomRepository.save(chatRoomMapper.toEntity(user1, user2));
  }

  public List<GetChatRoomResponse> getChatRoomAll(long userId) {
    List<ChatRoom> chatRooms = chatRoomRepository.findAllByUserId(userId);
    return chatRoomMapper.toDto(chatRooms);
  }

  public UUID getChatRoom(Long userId, Long user2Id) {
    ChatRoom chatRoom =
        chatRoomRepository.findByUser1AndUser2(userId, user2Id).orElseThrow(NotFoundChatRoom::new);
    return chatRoom.getRoomUuid();
  }
}
