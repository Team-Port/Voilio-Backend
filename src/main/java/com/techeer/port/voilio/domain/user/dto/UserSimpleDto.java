package com.techeer.port.voilio.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSimpleDto {

    private Long id;

    private String email;

    private String nickname;

    private String imageUrl;
}
