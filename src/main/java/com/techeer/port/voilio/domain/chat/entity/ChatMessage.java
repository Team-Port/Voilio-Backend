package com.techeer.port.voilio.domain.chat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class ChatMessage {

  // 메시지 타입 : 입장, 채팅
  public enum MessageType {
    ENTER,
    TALK,
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private MessageType type; // 메시지 타입

  private Long chatRoomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지
}