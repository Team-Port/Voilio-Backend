package com.techeer.port.voilio.domain.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FollowResponse {
  private String follow_nickname;
  private Long follow_id;
}
