package com.techeer.port.voilio.domain.user.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.user.dto.request.UserRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.config.JwtToken;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API Document")
@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/join")
  @Operation(summary = "회원 생성", description = "회원 생성 메서드입니다.")
  public ResponseEntity<ResultResponse> createUser(@Valid @RequestBody UserRequest userRequest)
      throws Exception {
    userService.registerUser(userRequest);
    ResultResponse<User> resultResponse = new ResultResponse<>(USER_REGISTRATION_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(UserController.class).createUser(userRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/list")
  @Operation(summary = "회원 출력", description = "전체 회원 출력 메서드입니다.")
  public ResponseEntity<ResultResponse<List<EntityModel<User>>>> getUserList() {
    List<EntityModel<User>> users =
        userService.getUserList().stream()
            .map(
                user ->
                    EntityModel.of(
                        user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId()))
                            .withSelfRel()))
            .collect(Collectors.toList());
    ResultResponse<List<EntityModel<User>>> resultResponse =
        new ResultResponse<>(GET_ALL_USER_SUCCESS, users);
    resultResponse.add(linkTo(methodOn(UserController.class).getUserList()).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/{user_id}")
  @Operation(summary = "회원 조회", description = "지정 회원을 조회하는 메서드입니다.")
  public ResponseEntity<ResultResponse> getUserById(@PathVariable("user_id") Long userId) {
    User user = userService.getUser(userId);
    ResultResponse<User> resultResponse = new ResultResponse<>(GET_USER_SUCCESS, user);
    resultResponse.add(linkTo(methodOn(UserController.class).getUserById(userId)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @DeleteMapping("/{user_id}")
  @Operation(summary = "회원 삭제", description = "회원 삭제 메서드입니다.")
  public ResponseEntity<ResultResponse> deleteUser(@PathVariable("user_id") Long userId) {
    userService.deleteUser(userId);
    ResultResponse<?> resultResponse = new ResultResponse<>(DELETE_USER_SUCCESS);
    resultResponse.add(linkTo(methodOn(UserController.class).deleteUser(userId)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> login(@RequestBody Map<String, String> loginForm) {
    JwtToken token = userService.login(loginForm.get("email"), loginForm.get("password"));
    //    ResultResponse<JwtToken> resultResponse = new ResultResponse<>(GET_USER_SUCCESS, token);
    //    resultResponse.add(linkTo(methodOn(UserController.class).login(loginForm)).withSelfRel());
    //    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    return ResponseEntity.ok(token);
  }
}
