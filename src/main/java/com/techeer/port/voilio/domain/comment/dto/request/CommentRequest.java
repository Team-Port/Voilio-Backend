package com.techeer.port.voilio.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentRequest {

  @Schema(description = "게시판 아이디")
  @NotNull
  private Long boardId;

  @Schema(description = "댓글 내용")
  @NotNull
  private String content;
}
