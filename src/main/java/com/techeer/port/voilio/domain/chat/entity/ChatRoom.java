package com.techeer.port.voilio.domain.chat.entity;

import com.techeer.port.voilio.domain.user.entity.User;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ChatRoomId.class)
public class ChatRoom {
  @Id
  @ManyToOne
  @JoinColumn(name = "user_id1")
  private User user1;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id2")
  private User user2;

  @Column @Builder.Default private UUID roomUuid = UUID.randomUUID();

  @Column private String roomName;
}
