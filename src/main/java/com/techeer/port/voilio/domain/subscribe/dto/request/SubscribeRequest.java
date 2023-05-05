package com.techeer.port.voilio.domain.subscribe.dto.request;

<<<<<<< HEAD

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SubscribeRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long followerId;
}
=======
public class SubscribeRequest {}
>>>>>>> subscribe
