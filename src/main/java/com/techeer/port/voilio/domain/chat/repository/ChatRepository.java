package com.techeer.port.voilio.domain.chat.repository;

import com.techeer.port.voilio.domain.chat.document.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Page<Chat> findAllByRoomUuid(UUID rooUuid, Pageable pageable);

    List<Chat> findAllByRoomUuid(UUID rooUuid);
}