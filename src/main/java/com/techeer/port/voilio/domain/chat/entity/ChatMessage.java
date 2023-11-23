package com.techeer.port.voilio.domain.chat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
public class ChatMessage {

  // 메시지 타입 : 입장, 채팅
  public enum MessageType {
    ENTER,
    TALK,
    WEBCAM,
    OFFER,
    ANSWER,
    ICE,
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private MessageType type; // 메시지 타입
  private String roomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지
}
