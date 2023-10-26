package com.techeer.port.voilio.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserProfileDto {

    @NotNull private String imageUrl;
}
