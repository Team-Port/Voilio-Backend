package com.techeer.port.voilio.domain.chat.repository;

import com.techeer.port.voilio.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {}
