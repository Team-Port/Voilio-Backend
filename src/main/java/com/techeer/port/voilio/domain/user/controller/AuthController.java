package com.techeer.port.voilio.domain.user.controller;

import static com.techeer.port.voilio.global.result.ResultCode.LOGIN_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.user.dto.request.UserRequest;
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

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ResultResponse<UserResponse>> signup(@RequestBody UserRequest userRequest) {
    UserResponse userResponse = authService.signup(userRequest);
    ResultResponse<UserResponse> resultResponse =
        new ResultResponse<>(USER_REGISTRATION_SUCCESS, userResponse);
    resultResponse.add(linkTo(methodOn(AuthController.class).signup(userRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<ResultResponse<TokenDto>> login(@RequestBody UserRequest userRequest) {
    TokenDto tokenDto = authService.login(userRequest);
    ResultResponse<TokenDto> resultResponse = new ResultResponse<>(LOGIN_SUCCESS, tokenDto);
    resultResponse.add(linkTo(methodOn(AuthController.class).signup(userRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
