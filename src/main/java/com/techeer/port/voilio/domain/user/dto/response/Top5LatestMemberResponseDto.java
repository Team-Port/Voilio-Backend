package com.techeer.port.voilio.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Top5LatestMemberResponseDto {
    private Long memberId;
    private String nickname;

}
