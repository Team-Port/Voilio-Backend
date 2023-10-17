package com.techeer.port.voilio.domain.user.controller;

import com.techeer.port.voilio.domain.user.dto.request.UserLoginRequest;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.service.AuthService;
import com.techeer.port.voilio.global.config.security.TokenDto;
import com.techeer.port.voilio.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.AlreadyBoundException;

import static com.techeer.port.voilio.global.result.ResultCode.LOGIN_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ResultResponse<UserResponse>> signup(
      @RequestBody UserSignUpRequest userSignUpRequest) throws AlreadyBoundException {
    UserResponse userResponse = authService.signup(userSignUpRequest);
    ResultResponse<UserResponse> resultResponse =
        new ResultResponse<>(USER_REGISTRATION_SUCCESS, userResponse);
    //    resultResponse.add(
    //        linkTo(methodOn(AuthController.class).signup(userSignUpRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<ResultResponse<TokenDto>> login(
      @RequestBody UserLoginRequest userLoginRequest) throws AlreadyBoundException {
    TokenDto tokenDto = authService.login(userLoginRequest);
    ResultResponse<TokenDto> resultResponse = new ResultResponse<>(LOGIN_SUCCESS, tokenDto);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
