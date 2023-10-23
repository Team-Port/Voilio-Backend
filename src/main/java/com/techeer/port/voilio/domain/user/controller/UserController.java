package com.techeer.port.voilio.domain.user.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;

import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API Document")
@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtProvider jwtProvider;

  @GetMapping("/list")
  @Operation(summary = "회원 출력", description = "전체 회원 출력 메서드입니다.")
  public ResponseEntity<ResultResponse<List<User>>> getUserList() {
    List<UserResponse> users = userService.getUserList();
    ResultResponse<List<User>> resultResponse = new ResultResponse<>(GET_ALL_USER_SUCCESS, users);
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/{user_id}")
  @Operation(summary = "회원 조회", description = "지정 회원을 조회하는 메서드입니다.")
  public ResponseEntity<ResultResponse> getUserById(@PathVariable("user_id") Long userId) {
    UserDto user = userService.getUserDto(userId);
    ResultResponse<UserDto> resultResponse = new ResultResponse<>(GET_USER_SUCCESS, user);
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/nickname/{nickname}")
  @Operation(summary = "회원 조회2", description = "지정 회원을 조회하는 메서드입니다.")
  public ResponseEntity<ResultResponse> getUserByNickname(
      @PathVariable("nickname") String nickname) {
    UserDto user = userService.getUserDto(nickname);
    ResultResponse<UserDto> resultResponse = new ResultResponse<>(GET_USER_SUCCESS, user);
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @DeleteMapping("/{user_id}")
  @Operation(summary = "회원 삭제", description = "회원 삭제 메서드입니다.")
  public ResponseEntity<ResultResponse> deleteUser(@PathVariable("user_id") Long userId) {
    userService.deleteUser(userId);
    ResultResponse<?> resultResponse = new ResultResponse<>(DELETE_USER_SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  //  @GetMapping("/showAll")
  //  @Operation(summary = "최근 가입순 회원 이름 조회")
  //  public ResponseEntity<ResultResponse> getLatestMember() {
  //    List<Top5LatestMemberResponseDto> top5LatestMemberResponseDtos =
  // userService.getLatestMember();
  //    ResultResponse<?> resultResponse =
  //        new ResultResponse<>(GET_USER_SUCCESS, top5LatestMemberResponseDtos);
  //
  //    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  //  }
}
