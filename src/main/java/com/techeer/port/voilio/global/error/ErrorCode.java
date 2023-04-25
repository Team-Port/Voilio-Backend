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
  INVALID_USER_PASSWORD(400, "U003", "비밀번호가 일치하지 않음"),
  ALREADY_EXIST_USER(400, "U004", "이미 가입된 유저 email 입니다."),
  INVAILD_USER_INFO(401, "U005", "가입되지 않은 유저거나, 이메일과 비밀번호가 일치하지 않습니다."),

  // board
  BOARD_NOT_FOUND_ERROR(400, "B001", "게시글을 찾을 수 없음"),
  // comment
  COMMENT_NOT_FOUND_EXCEPTION(400, "C001", "댓글을 찾을 수 없음"),

  CONVERT_FILE_ERROR(400, "S001", "파일 변환 실패"),
  UPLOAD_FILE_ERROR(400, "S002", "파일이 존재하지 않음"),
  ;
  private final int status;
  private final String code;
  private final String message;
}
