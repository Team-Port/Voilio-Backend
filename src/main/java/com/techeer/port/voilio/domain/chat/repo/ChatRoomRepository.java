package com.techeer.port.voilio.domain.chat.repo;

import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
