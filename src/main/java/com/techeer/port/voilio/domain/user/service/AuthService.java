package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.user.dto.request.UserLoginRequest;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.entity.Authority;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.AlreadyExistUser;
import com.techeer.port.voilio.domain.user.exception.InvalidPassword;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import com.techeer.port.voilio.domain.user.mapper.UserMapper;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.YnType;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.config.security.TokenDto;
import java.time.LocalDateTime;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManagerBuilder managerBuilder;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;


  @Transactional
  public Boolean signup(UserSignUpRequest userSignUpRequest) {
    if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
      throw new AlreadyExistUser();
    }
    try{
      User user = UserMapper.INSTANCE.toEntity(userSignUpRequest);
      user.changePassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
      user.changeUserRole(Authority.ROLE_USER);
      user.changeDelYn(YnType.N);
      user.changeIsStopped(YnType.N);
      userRepository.save(user);
      return true;
    } catch (Exception e){
      throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
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
            .findUserByEmailAndDelYn(userLoginRequest.getEmail(), YnType.N)
            .orElseThrow(NotFoundUserException::new);

    if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
      throw new InvalidPassword();
    }
    return user;
  }
}
