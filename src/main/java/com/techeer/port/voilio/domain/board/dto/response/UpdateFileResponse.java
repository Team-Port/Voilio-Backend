package com.techeer.port.voilio.domain.board.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateFileResponse {
  private String thumbnail_url;
}
