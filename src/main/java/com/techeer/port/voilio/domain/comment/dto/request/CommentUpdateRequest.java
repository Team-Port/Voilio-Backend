package com.techeer.port.voilio.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
public class CommentUpdateRequest {

  @Schema(description = "수정 댓글 내용")
  private String content;
}
