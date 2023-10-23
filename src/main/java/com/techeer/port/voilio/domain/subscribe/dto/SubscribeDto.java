package com.techeer.port.voilio.domain.subscribe.dto;


import com.techeer.port.voilio.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeDto {

    private Long id;

    private UserDto fromUser;

    private UserDto toUser;
}
