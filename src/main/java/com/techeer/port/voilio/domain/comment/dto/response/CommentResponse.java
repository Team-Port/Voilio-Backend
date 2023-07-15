package com.techeer.port.voilio.domain.comment.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {

  private Long commentId;
  private String nickname;
  private String content;
  private LocalDateTime localDateTime;
}
