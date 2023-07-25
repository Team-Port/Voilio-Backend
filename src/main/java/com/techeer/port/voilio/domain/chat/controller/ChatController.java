package com.techeer.port.voilio.domain.chat.controller;

import com.techeer.port.voilio.domain.chat.dto.ChatMessage;
import com.techeer.port.voilio.domain.chat.dto.response.GetChatResponse;
import com.techeer.port.voilio.domain.chat.mapper.ChatMapper;
import com.techeer.port.voilio.domain.chat.pubsub.RedisPublisher;
import com.techeer.port.voilio.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.techeer.port.voilio.global.result.ResultCode.API_SUCCESS_CHAT_GET_ALL;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final ChatService chatService;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ChatMapper chatMapper;

  /** websocket "/pub/chat/message"로 들어오는 메시징을 처리한다. */
  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {
    if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
      message.setMessage(message.getUserId() + "님이 입장하셨습니다.");
    }
    chatService.sendChatMessage("chatTopic", message);
  }

  @KafkaListener(topics = "chatTopic", groupId = "chat-group")
  public void listen(@Payload ChatMessage request) {
    // WebSocket을 통해 브로드캐스트하기\
    chatService.saveChat(request);
//    simpMessagingTemplate.convertAndSend(
//            "/topic/" + request.getRoomUuid(), new OutputMessage<>(WEBSOCKET_SUCCESS_CHAT, request));
    simpMessagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), request);
  }

  @GetMapping
  public ResponseEntity getChat(
          @RequestParam("roomUuid") UUID roomUuid,
          @RequestParam(value = "page", defaultValue = "0") int page) {
    Page<GetChatResponse> response = chatService.getChat(roomUuid, PageRequest.of(page, 20));
    return ResponseEntity.ok(
            chatMapper.toModel(API_SUCCESS_CHAT_GET_ALL, response, roomUuid, page));
  }
}
