package com.techeer.port.voilio.domain.board.dto.response;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadFileResponse {

  @NotNull private String videoUrl;
  @NotNull private String thumbnailUrl;
}
