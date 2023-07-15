package com.techeer.port.voilio.domain.chat.controller;

import com.techeer.port.voilio.domain.chat.model.ChatMessage;
import com.techeer.port.voilio.domain.chat.pubsub.RedisPublisher;
import com.techeer.port.voilio.domain.chat.repo.ChatRoomRepositoryN;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final RedisPublisher redisPublisher;
  private final ChatRoomRepositoryN chatRoomRepository;

  /** websocket "/pub/chat/message"로 들어오는 메시징을 처리한다. */
  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {
    if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
      chatRoomRepository.enterChatRoom(message.getRoomId());
      message.setMessage(message.getSender() + "님이 입장하셨습니다.");
    }
    log.info(message.toString());
    // Websocket에 발행된 메시지를 redis로 발행한다(publish)
    redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
  }
}
