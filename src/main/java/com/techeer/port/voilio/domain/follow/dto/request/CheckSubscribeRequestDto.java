package com.techeer.port.voilio.domain.follow.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckSubscribeRequestDto {
  String nickname;
  Long subscribeId;
}
