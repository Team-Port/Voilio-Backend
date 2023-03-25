package com.techeer.port.voilio.domain.board.dto.response;

import java.time.LocalDate;

public class BoardResponse {
  private String title;
  private String content;
  private String category1;
  private String category2;
  private String video_url;
  private String thumbnail_url;
  private LocalDate updated_at;
  private LocalDate created_at;
}
