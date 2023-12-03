package com.techeer.port.voilio.domain.chat.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.chat.dto.ChatRoomDto;
import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
import com.techeer.port.voilio.domain.chat.exception.NotFoundChatRoom;
import com.techeer.port.voilio.domain.chat.mapper.ChatRoomMapper;
import com.techeer.port.voilio.domain.chat.repository.ChatRoomRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final UserRepository userRepository;

  public void createChatRoom(CreateChatRoomRequest request) {
    User fromUser = userRepository.findById(request.getFromUserId()).orElseThrow(NotFoundUser::new);
    User toUser = userRepository.findById(request.getToUserId()).orElseThrow(NotFoundUser::new);

    ChatRoom chatRoom =
        ChatRoom.builder()
            .fromUser(fromUser)
            .toUser(toUser)
            .roomName(request.getRoomName())
            .build();

    chatRoomRepository.save(chatRoom);
  }

  public List<ChatRoomDto> getChatRooms() {
    List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
    return ChatRoomMapper.INSTANCE.toDtos(chatRoomList);
  }

  public boolean checkUser(Long chatRoomId, User user) {
    ChatRoom chatRoom1 = chatRoomRepository.findById(chatRoomId).orElseThrow(NotFoundChatRoom::new);
    ChatRoom chatRoom2 =
        chatRoomRepository.findByFromUserOrToUser(user, user).orElseThrow(NotFoundChatRoom::new);

    return chatRoom1.equals(chatRoom2);
  }

  public ChatRoomDto findChatRoomByName(String chatRoomName) {
    ChatRoom chatRoom =
        chatRoomRepository.findByRoomName(chatRoomName).orElseThrow(NotFoundChatRoom::new);
    return ChatRoomMapper.INSTANCE.toDto(chatRoom);
  }

  public ChatRoomDto findChatRoomById(Long chatRoomId) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(NotFoundChatRoom::new);
    return ChatRoomMapper.INSTANCE.toDto(chatRoom);
  }
}
