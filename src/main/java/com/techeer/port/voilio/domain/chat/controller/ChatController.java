package com.techeer.port.voilio.domain.chat.controller;

import com.techeer.port.voilio.domain.chat.entity.ChatMessage;
import com.techeer.port.voilio.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final SimpMessageSendingOperations template;
  private final ChatMessageService chatMessageService;
  private final RabbitTemplate rabbitTemplate;

  // pub/chat/1/message => 1번 방으로 publish한 메세지 처리
  @MessageMapping("/chat/{chatRoomId}/message")
  public void message(@Payload ChatMessage message, @DestinationVariable Long chatRoomId) {
    System.out.println(message.getMessage());
//    chatMessageService.saveChatMessage(message);

    // sub/1을 subscibe한 유저에게 메세지 전송
    template.convertAndSend("/sub/" + chatRoomId.toString(), message);
//    rabbitTemplate.convertAndSend(exchageName,"chat.messages", message);
  }

  @MessageMapping("chat/{chatRoomId}/enter")
  public void enterRoom(ChatMessage message, @DestinationVariable Long chatRoomId) {
    String sender = message.getSender();
    String enter = sender + "님이 입장하셨습니다.";

    message.changeMessage(enter);
    template.convertAndSend("/sub/" + chatRoomId.toString(), message);
  }
}
