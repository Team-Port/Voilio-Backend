package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.user.dto.request.UserLoginRequest;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.InvalidPassword;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.config.security.TokenDto;
import java.rmi.AlreadyBoundException;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
  private final AuthenticationManagerBuilder managerBuilder;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public UserResponse signup(UserSignUpRequest userSignUpRequest) throws AlreadyBoundException {
    if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
      throw new AlreadyBoundException();
    }

    User user = userSignUpRequest.toEntity(passwordEncoder);
    return UserResponse.of(userRepository.save(user));
  }

  public TokenDto login(UserLoginRequest userLoginRequest) {
    User user = validateAccount(userLoginRequest);

    // 로그인 성공 후, 추가 작업 - 토큰 생성
    user.updateActivatedAt(LocalDateTime.now());
    UsernamePasswordAuthenticationToken authenticationToken = userLoginRequest.toAuthentication();
    Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

    return jwtProvider.generateTokenDto(authentication);
  }

  private User validateAccount(UserLoginRequest userLoginRequest) {
    User user =
        userRepository
            .findUserByEmail(userLoginRequest.getEmail())
            .orElseThrow(NotFoundUserException::new);

    if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
      throw new InvalidPassword();
    }
    return user;
  }
}
