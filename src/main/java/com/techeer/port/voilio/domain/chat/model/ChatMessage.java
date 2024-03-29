package com.techeer.port.voilio.domain.chat.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

  private MessageType type; // 메시지 타입
  private String roomId; // 방번호
  private String sender; // 메시지 보낸사람
  private String message; // 메시지
  private Object candidate;
  private Object sdp;
}
