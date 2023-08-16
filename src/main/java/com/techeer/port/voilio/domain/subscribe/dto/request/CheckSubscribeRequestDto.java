package com.techeer.port.voilio.domain.subscribe.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckSubscribeRequestDto {
  String nickname;
  Long subscribeId;
}
