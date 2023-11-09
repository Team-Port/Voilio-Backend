package com.techeer.port.voilio.domain.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubscribeResponse {
  private String subscribe_nickname;
  private Long subscribe_id;
}
