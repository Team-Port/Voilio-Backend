package com.techeer.port.voilio.domain.follow.dto;

import com.techeer.port.voilio.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {

  private Long id;

  private UserDto fromUser;

  private UserDto toUser;
}
