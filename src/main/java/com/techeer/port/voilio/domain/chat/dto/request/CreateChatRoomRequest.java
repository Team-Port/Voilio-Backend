package com.techeer.port.voilio.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomRequest {

  @Schema(description = "요청 사용자 닉네임")
  private String nickName;

  @Schema(description = "채팅을 요청하는 상대방의 유니크값")
  private long chatUserId;
}
