package com.techeer.port.voilio.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {주체}_{이유} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
  INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),

  // 예시
  // User 도메인
  INVALID_PASSWORD(400, "U001", "잘못된 비밀번호"),
  USER_NOT_FOUND_ERROR(400, "U002", "사용자를 찾을 수 없음"),

  //board
  BOARD_NOT_FOUND_ERROR(400,"B001","게시글을 찾을 수 없음"),
  ;

  private final int status;
  private final String code;
  private final String message;
}
