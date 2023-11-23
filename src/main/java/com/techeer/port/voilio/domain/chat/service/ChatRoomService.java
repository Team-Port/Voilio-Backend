 package com.techeer.port.voilio.domain.chat.service;

 import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
 import com.techeer.port.voilio.domain.chat.dto.ChatRoomDto;
 import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
 import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
 import com.techeer.port.voilio.domain.chat.mapper.ChatRoomMapper;
 import com.techeer.port.voilio.domain.chat.repository.ChatRoomRepository;
 import com.techeer.port.voilio.domain.user.entity.User;
 import com.techeer.port.voilio.domain.user.repository.UserRepository;
 import lombok.RequiredArgsConstructor;
 import org.springframework.stereotype.Service;

 import java.util.List;

 @Service
 @RequiredArgsConstructor
 public class ChatRoomService {

     private final ChatRoomRepository chatRoomRepository;
     private final UserRepository userRepository;
     public void createChatRoom(CreateChatRoomRequest request){
         User fromUser = userRepository.findById(request.getFromUserId()).orElseThrow(NotFoundUser::new);
         User toUser = userRepository.findById(request.getToUserId()).orElseThrow(NotFoundUser::new);

         ChatRoom chatRoom = ChatRoom.builder()
                 .fromUser(fromUser)
                 .toUser(toUser)
                 .roomName(request.getRoomName())
                 .build();

         chatRoomRepository.save(chatRoom);
     }

     public List<ChatRoomDto> getChatRooms(){
         List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
         return ChatRoomMapper.INSTANCE.toDtos(chatRoomList);
     }
 }
