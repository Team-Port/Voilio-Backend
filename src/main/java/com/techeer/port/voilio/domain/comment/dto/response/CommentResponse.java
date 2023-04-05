package com.techeer.port.voilio.domain.comment.dto.response;

import com.techeer.port.voilio.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {

  private Long commentId;
  private String nickname;
  private String content;
  private LocalDateTime localDateTime;
}
