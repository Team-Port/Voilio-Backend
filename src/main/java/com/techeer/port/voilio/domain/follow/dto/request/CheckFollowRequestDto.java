package com.techeer.port.voilio.domain.follow.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckFollowRequestDto {
  Long fromUserId;
  Long toUserId;
}
