package com.techeer.port.voilio.domain.user.dto;

import com.techeer.port.voilio.domain.subscribe.dto.SubscribeDto;
import com.techeer.port.voilio.global.common.YnType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDetailDto {

    private Long id;
    private String email;
    private String nickname;
    private String imageUrl;
    private YnType delYn;
    private List<SubscribeDto> following;
    private Long videoCount;
    private Long normalCount;
    private Long followerCount;

    public void changeVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }

    public void changeNormalCount(Long normalCount) {
        this.normalCount = normalCount;
    }

    public void changeFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }
}
