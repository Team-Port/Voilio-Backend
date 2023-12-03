package com.techeer.port.voilio.domain.chat.entity;

import com.techeer.port.voilio.domain.user.entity.User;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @IdClass(ChatRoomId.class)
public class ChatRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_user_id")
  private User fromUser;

  @ManyToOne
  @JoinColumn(name = "to_user_id")
  private User toUser;

  //  @Column @Builder.Default private UUID roomUuid = UUID.randomUUID();

  @Column private String roomName;
}
