package com.techeer.port.voilio.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

  // 도메인 별로 나눠서 관리(ex: User 도메인)
  // user
  USER_REGISTRATION_SUCCESS("U001", "200", "사용자가 정상적으로 등록되었습니다."),
  GET_ALL_USER_SUCCESS("U002", "200", "모든 사용자를 정상적으로 불러왔습니다."),
  GET_USER_SUCCESS("U003", "200", "지정 사용자를 정상적으로 불러왔습니다."),
  DELETE_USER_SUCCESS("U004", "200", "지정 사용자를 정상적으로 삭제했습니다."),
  LOGIN_SUCCESS("U005", "200", "정상적으로 로그인 되었습니다."),

  BOARD_CREATED_SUCCESS("B001", "201", "게시물이 정상적으로 생성되었습니다."),

  BOARD_FIND_SUCCESS("B003", "200", "게시물을 정상적으로 가져왔습니다."),
  BOARD_UPDATED_SUCCESS("B002", "200", "게시물이 정상적으로 수정되었습니다."),
  BOARD_FINDALL_SUCCESS("B003", "200", "전체 게시물 입니다."),

  COMMENT_CREATED_SUCCESS("C001", "301", "댓글이 정상적으로 등록되었습니다."),
  UPDATE_COMMENT_SUCCESS("C002", "302", "댓글이 정상적으로 수정되었습니다."),
  DELETE_COMMENT_SUCCESS("C003", "303", "댓글이 정상적으로 삭제되었습니다."),
  GET_COMMENT_SUCCESS("C004", "304", "댓글이 정상적으로 출력되었습니다."),

  FILE_UPLOAD_SUCCESS("F001", "201", "파일을 정상적으로 등록했습니다."),

  SUBSCRIBE_SUCCESS("S001", "200", "팔로우 하였습니다."),
  UNSUBSCRIBE_SUCCESS("S002", "200", "팔로우를 취소하였습니다."),
  SUBSCRIBE_FINDALL_SUCCESS("S003", "200", "팔로우 목록입니다.");

  private final String code;
  private final String status;
  private final String message;
}
