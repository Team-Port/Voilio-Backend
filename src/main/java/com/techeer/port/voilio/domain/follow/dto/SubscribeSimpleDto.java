package com.techeer.port.voilio.domain.follow.dto;

import com.techeer.port.voilio.domain.user.dto.UserSimpleDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeSimpleDto {
  private Long id;

  private UserSimpleDto toUser;
}
