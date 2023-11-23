package com.techeer.port.voilio.domain.chat.service;

import com.techeer.port.voilio.domain.chat.entity.ChatMessage;
import com.techeer.port.voilio.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void saveChatMessage(ChatMessage message){
        chatMessageRepository.save(message);
    }
}
