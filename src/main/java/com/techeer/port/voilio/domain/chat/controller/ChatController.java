package com.techeer.port.voilio.domain.chat.controller;

import com.techeer.port.voilio.domain.chat.model.ChatMessage;
import com.techeer.port.voilio.domain.chat.pubsub.RedisPublisher;
import com.techeer.port.voilio.domain.chat.repo.ChatRoomRepositoryN;
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

  private final RedisPublisher redisPublisher;
  private final ChatRoomRepositoryN chatRoomRepository;
  private final SimpMessageSendingOperations template;

  /** websocket "/pub/chat/message"로 들어오는 메시징을 처리한다. */
  @MessageMapping("/chat/{chatRoomId}/message")
  public void message(ChatMessage message, @DestinationVariable Long chatRoomId) {
    System.out.println(message.getMessage());
    template.convertAndSend("/sub/" + chatRoomId.toString(), message);
  }
}
