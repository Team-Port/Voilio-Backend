 package com.techeer.port.voilio.domain.chat.service;

 import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
 import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
 import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
 import com.techeer.port.voilio.domain.chat.repo.ChatRoomRepository;
 import com.techeer.port.voilio.domain.user.entity.User;
 import com.techeer.port.voilio.domain.user.repository.UserRepository;
 import lombok.RequiredArgsConstructor;
 import org.springframework.stereotype.Service;

 import java.util.UUID;

 @RequiredArgsConstructor
 @Service
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
 }
