package com.techeer.port.voilio.domain.follow.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FollowRequest {

  @NotNull private String nickname;

  @NotNull private Long followId;
}
