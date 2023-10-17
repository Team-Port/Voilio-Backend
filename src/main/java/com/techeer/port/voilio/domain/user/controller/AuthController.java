package com.techeer.port.voilio.domain.user.controller;

import static com.techeer.port.voilio.global.result.ResultCode.LOGIN_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;

import com.techeer.port.voilio.domain.user.dto.request.UserLoginRequest;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.service.AuthService;
import com.techeer.port.voilio.global.config.security.TokenDto;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.rmi.AlreadyBoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ResultResponse<Boolean>> signup(
      @RequestBody UserSignUpRequest userSignUpRequest) {
    Boolean isSignup = authService.signup(userSignUpRequest);
    ResultResponse<Boolean> resultResponse =
        new ResultResponse<>(USER_REGISTRATION_SUCCESS, isSignup);
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
