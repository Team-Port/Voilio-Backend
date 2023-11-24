 package com.techeer.port.voilio.domain.chat.controller;

 import com.techeer.port.voilio.domain.chat.dto.ChatRoomDto;
 import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
 import com.techeer.port.voilio.domain.chat.service.ChatRoomService;
 import com.techeer.port.voilio.domain.user.entity.User;
 import com.techeer.port.voilio.global.error.ErrorCode;
 import com.techeer.port.voilio.global.error.exception.BusinessException;
 import lombok.RequiredArgsConstructor;
 import org.springframework.security.core.annotation.AuthenticationPrincipal;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("api/v1/chatRoom")
 public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  @PostMapping("/")
  public void createChatRoom(CreateChatRoomRequest request){
   chatRoomService.createChatRoom(request);
  }

  @GetMapping("/")
  public List<ChatRoomDto> getChatRooms(){
   return chatRoomService.getChatRooms();
  }

  @GetMapping("/id/{chatRoomId}")
  public boolean enterRoom(@PathVariable(value = "chatRoomId") Long chatRoomId, @AuthenticationPrincipal User user){
   if (user == null) {
    throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
   }
   return chatRoomService.checkUser(chatRoomId, user);
  }

  @GetMapping("/name/{chatRoomName}")
  public ChatRoomDto findChatRoom(@PathVariable(value = "chatRoomName") String chatRoomName){
   return chatRoomService.findChatRoom(chatRoomName);
  }
}