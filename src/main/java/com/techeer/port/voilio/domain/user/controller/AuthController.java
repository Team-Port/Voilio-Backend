package com.techeer.port.voilio.domain.user.controller;

import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.request.UserLoginRequest;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.AuthService;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.techeer.port.voilio.global.result.ResultCode.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse<Boolean>> signup(
            @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        Boolean isSignup = authService.signup(userSignUpRequest);
        ResultResponse<Boolean> resultResponse = new ResultResponse<>(USER_REGISTRATION_SUCCESS, isSignup);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse<String>> login(
            @RequestBody UserLoginRequest userLoginRequest
    ) {
        String token = authService.login(userLoginRequest);
        ResultResponse<String> resultResponse = new ResultResponse<>(LOGIN_SUCCESS, token);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<ResultResponse<UserDto>> me(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        UserDto userDto = authService.me(user);
        ResultResponse<UserDto> resultResponse = new ResultResponse<>(GET_USER_SUCCESS, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }


}
