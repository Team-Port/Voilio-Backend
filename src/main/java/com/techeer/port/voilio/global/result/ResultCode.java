package com.techeer.port.voilio.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

  // 도메인 별로 나눠서 관리(ex: User 도메인)
  // user
  USER_REGISTRATION_SUCCESS("U001", "200", "사용자 등록 성공"),

  BOARD_CREATED_SUCCESS("B001", "201", "게시물이 정상적으로 생성되었습니다."),

  COMMENT_CREATED_SUCCESS("C001", "301", "댓글이 정상적으로 등록되었습니다."),
  UPDATE_COMMENT_SUCCESS("C002", "302", "댓글이 정상적으로 수정되었습니다."),
  ;

  private final String code;
  private final String status;
  private final String message;
}
