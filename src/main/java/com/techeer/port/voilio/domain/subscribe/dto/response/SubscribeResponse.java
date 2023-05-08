package com.techeer.port.voilio.domain.subscribe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubscribeResponse {
  private String subscriber_nickname;
  private Long subscriber_id;
}
