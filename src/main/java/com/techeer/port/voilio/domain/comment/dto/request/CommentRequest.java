package com.techeer.port.voilio.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
public class CommentRequest {

  @Schema(description = "게시판 아이디")
  private Long boardId;

  @Schema(description = "댓글 내용")
  private String content;

  @Schema(description = "상위 댓글 아이디")
  private Long parentId;
}
