package com.techeer.port.voilio.domain.subscribe.entity;

import com.techeer.port.voilio.domain.user.entity.User;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "subscribes")
public class Subscribe {

  @Id
  @Column(name = "subscribe_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_user_id")
  private User fromUser; // 팔로잉 하는 사용자

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_user_id")
  private User toUser; // 팔로잉 받는 사용자
}