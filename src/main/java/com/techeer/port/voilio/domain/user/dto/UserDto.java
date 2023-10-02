package com.techeer.port.voilio.domain.user.dto;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.user.entity.Authority;
import com.techeer.port.voilio.global.common.YnType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

  private Long id;
  private String email;
  private String password;
  private String nickname;
  private String imageUrl;
  private LocalDateTime activatedAt;
  private YnType isStopped;
  private List<BoardDto> boards;
  private Authority authority;
  private YnType delYn;
}
