package com.techeer.port.voilio.domain.follow.dto.request;

import javax.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FollowRequest {

  @NotNull private Long fromUserId;

  @NotNull private Long toUserId;
}
