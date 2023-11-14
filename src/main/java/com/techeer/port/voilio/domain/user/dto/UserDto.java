package com.techeer.port.voilio.domain.user.dto;

import com.techeer.port.voilio.domain.follow.dto.FollowDto;
import com.techeer.port.voilio.global.common.YnType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

  private Long id;
  private String email;
  private String nickname;
  private String imageUrl;
  //  private List<BoardDto> boards;
  private YnType delYn;
  private List<FollowDto> following;

  public void updateFollowing(List<FollowDto> following) {
    this.following = following;
  }
}
