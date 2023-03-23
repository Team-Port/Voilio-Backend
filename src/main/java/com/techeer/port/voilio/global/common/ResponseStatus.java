package com.techeer.port.voilio.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
  // board 응답 status
  FETCH_BOARD_SUCCESS(200, "success");

  private final int status;
  private final String message;
}
