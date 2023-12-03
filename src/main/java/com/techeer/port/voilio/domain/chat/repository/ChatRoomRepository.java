package com.techeer.port.voilio.domain.chat.repository;

import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  Optional<ChatRoom> findByFromUserOrToUser(User fromUser, User toUser);

  Optional<ChatRoom> findByRoomName(String roomName);
}
