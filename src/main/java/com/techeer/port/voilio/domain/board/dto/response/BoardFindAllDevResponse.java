package com.techeer.port.voilio.domain.board.dto.response;

import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardFindAllDevResponse {
  private BoardResponse board;
  private UserResponse user;
}
