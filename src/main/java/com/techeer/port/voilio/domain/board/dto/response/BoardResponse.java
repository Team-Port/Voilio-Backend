package com.techeer.port.voilio.domain.board.dto.response;

import com.techeer.port.voilio.global.common.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
public class BoardResponse {
  @Getter
  private Long id;
  private String title;
  private String content;
  private Category category1;
  private Category category2;
  private String video_url;
  private String thumbnail_url;
  private LocalDate updated_at;
  private LocalDate created_at;
}
