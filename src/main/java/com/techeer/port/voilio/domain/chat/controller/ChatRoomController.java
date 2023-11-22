 package com.techeer.port.voilio.domain.chat.controller;

 import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
 import com.techeer.port.voilio.domain.chat.service.ChatRoomService;
 import lombok.RequiredArgsConstructor;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RestController;

 @RestController
 @RequiredArgsConstructor
 public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  @PostMapping ("/chat/room")
  public void createChatRomm(CreateChatRoomRequest request){
   chatRoomService.createChatRoom(request);
  }
}