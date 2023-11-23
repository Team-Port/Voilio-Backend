package com.techeer.port.voilio.domain.chat.controller;

import com.techeer.port.voilio.domain.chat.entity.ChatMessage;
import com.techeer.port.voilio.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final SimpMessageSendingOperations template;
  private final ChatMessageService chatMessageService;

  @MessageMapping("/chat/{chatRoomId}/message")
  public void message(ChatMessage message, @DestinationVariable Long chatRoomId) {
    System.out.println(message.getMessage());
    chatMessageService.saveChatMessage(message);
    template.convertAndSend("/sub/" + chatRoomId.toString(), message);
  }

  @MessageMapping("chat/{chatRoomId}/enter")
  public void enterRoom(ChatMessage message, @DestinationVariable Long chatRoomId) {
    String sender = message.getSender();
    String enter = sender + "님이 입장하셨습니다.";

    message.setMessage(enter);
    template.convertAndSend("/sub/" + chatRoomId.toString(), message);
  }
}
