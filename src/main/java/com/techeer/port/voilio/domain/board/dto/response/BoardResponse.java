package com.techeer.port.voilio.domain.board.dto.response;

import com.techeer.port.voilio.global.common.Category;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardResponse {
  private Long id;
  private String title;
  private String content;
  private Category category1;
  private Category category2;
  private String video_url;
  private String thumbnail_url;
  private LocalDateTime updated_at;
  private LocalDateTime created_at;
}
