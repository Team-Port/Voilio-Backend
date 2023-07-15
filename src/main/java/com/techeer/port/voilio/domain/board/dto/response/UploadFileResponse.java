package com.techeer.port.voilio.domain.board.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadFileResponse {
  private String video_url;
  private String thumbnail_url;

  public UploadFileResponse(String video_url, String thumbnail_url) {
    this.video_url = video_url;
    this.thumbnail_url = thumbnail_url;
  }
}
