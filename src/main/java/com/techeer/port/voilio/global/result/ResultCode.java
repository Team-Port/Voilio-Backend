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
  CREATE_NICKNAME_SUCCESS("U006", "200", "닉네임이 정상적으로 생성되었습니다."),

  BOARD_CREATED_SUCCESS("B001", "201", "게시물이 정상적으로 생성되었습니다."),

  BOARD_FIND_SUCCESS("B003", "200", "게시물을 정상적으로 가져왔습니다."),
  BOARD_UPDATED_SUCCESS("B002", "200", "게시물이 정상적으로 수정되었습니다."),
  BOARD_FINDALL_SUCCESS("B003", "200", "전체 게시물 입니다."),

  COMMENT_CREATED_SUCCESS("C001", "201", "댓글이 정상적으로 등록되었습니다."),
  UPDATE_COMMENT_SUCCESS("C002", "201", "댓글이 정상적으로 수정되었습니다."),
  DELETE_COMMENT_SUCCESS("C003", "200", "댓글이 정상적으로 삭제되었습니다."),
  GET_COMMENT_SUCCESS("C004", "200", "댓글이 정상적으로 출력되었습니다."),

  FILE_UPLOAD_SUCCESS("F001", "201", "파일을 정상적으로 등록했습니다."),

  FOLLOW_SUCCESS("S001", "200", "구독 하였습니다."),
  UNFOLLOW_SUCCESS("S002", "200", "구독을 취소하였습니다."),
  FOLLOW_FINDALL_SUCCESS("S003", "200", "구독자 목록입니다."),
  FOLLOW_BOARD_FINDALL_SUCCESS("S004", "200", "구독자 게시물 목록입니다."),

  API_SUCCESS_GET_ALL_CHATROOM("R001", "200", "모든 채팅방을 정상적으로 불러왔습니다."),
  API_SUCCESS_CREATE_CHATROOM("R002", "200", "채팅방이 정상적으로 등록되었습니다."),
  API_SUCCESS_GET_CHATROOM("R003", "200", "채팅방을 정상적으로 불러왔습니다."),

  LIKE_CREATED_SUCCESS("L001", "201", "좋아요가 정상적으로 등록되었습니다."),
  LIKE_DELETED_SUCCESS("L002", "200", "좋아요가 정상적으로 취소되었습니다."),
  ;

  private final String code;
  private final String status;
  private final String message;
}
