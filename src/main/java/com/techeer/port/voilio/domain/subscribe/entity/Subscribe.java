package com.techeer.port.voilio.domain.subscribe.entity;

import com.techeer.port.voilio.domain.user.entity.User;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "subscribes")
@IdClass(SubscribeId.class)
public class Subscribe {

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "subscribe_id")
  private User subscribe;
}



