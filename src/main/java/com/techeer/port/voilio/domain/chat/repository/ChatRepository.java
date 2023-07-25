package com.techeer.port.voilio.domain.chat.repository;

import com.techeer.port.voilio.domain.chat.document.Chat;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Page<Chat> findAllByRoomId(UUID rooUuid, Pageable pageable);

    List<Chat> findAllByRoomId(UUID rooUuid);
}
