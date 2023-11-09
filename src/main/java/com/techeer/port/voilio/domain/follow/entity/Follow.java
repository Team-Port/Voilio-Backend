package com.techeer.port.voilio.domain.follow.entity;

import com.techeer.port.voilio.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "follows")
public class Follow {

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
