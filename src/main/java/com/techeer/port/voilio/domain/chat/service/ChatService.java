package com.techeer.port.voilio.domain.chat.service;

import com.techeer.port.voilio.domain.chat.document.Chat;
import com.techeer.port.voilio.domain.chat.dto.ChatMessage;
import com.techeer.port.voilio.domain.chat.dto.response.GetChatResponse;
import com.techeer.port.voilio.domain.chat.mapper.ChatMapper;
import com.techeer.port.voilio.domain.chat.repository.ChatRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
  private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  public void saveChat(ChatMessage request) {
    chatRepository.save(chatMapper.toEntity(request));
  }

  public void sendChatMessage(String topic, ChatMessage chatMessage) {
    ListenableFuture<SendResult<String, ChatMessage>> future =
        kafkaTemplate.send(topic, chatMessage);
  }

  public Page<GetChatResponse> getChat(UUID roomUuid, Pageable pageable) {
    Page<Chat> chats = chatRepository.findAllByRoomUuid(roomUuid, pageable);
    return chatMapper.toDto(chats);
  }
}
