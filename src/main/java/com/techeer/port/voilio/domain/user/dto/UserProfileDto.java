package com.techeer.port.voilio.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {

  @NotNull private String imageUrl;
}
