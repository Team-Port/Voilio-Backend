package com.techeer.port.voilio.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUpdateRequest {
  @Schema(description = "수정 댓글 내용")
  @NotNull
  private String content;

}
