 package com.techeer.port.voilio.domain.chat.controller;

 import com.techeer.port.voilio.domain.chat.dto.ChatRoomDto;
 import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
 import com.techeer.port.voilio.domain.chat.service.ChatRoomService;
 import lombok.RequiredArgsConstructor;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import java.util.List;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("api/v1/chatRoom")
 public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  @PostMapping("/")
  public void createChatRomm(CreateChatRoomRequest request){
   chatRoomService.createChatRoom(request);
  }

  @GetMapping("/")
  public List<ChatRoomDto> getChatRooms(){
   return chatRoomService.getChatRooms();
  }
}