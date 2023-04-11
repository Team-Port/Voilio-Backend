package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.config.security.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  public List<User> getUserList() {
    return new ArrayList<User>(userRepository.findAll());
  }

  public User getUser(Long userId) {
    User user = userRepository.findUserById(userId).orElseThrow(NotFoundUser::new);
    return user;
  }

  public User getUser(String email) {
    User user = userRepository.findUserByEmail(email).orElseThrow(NotFoundUser::new);
    return user;
  }

  public void deleteUser(Long userId) {
    User user = getUser(userId);
    user.changeDeleted();
    userRepository.save(user);
  }

  public UserResponse getMyInfoBySecurity() {
    return userRepository
        .findById(SecurityUtil.getCurrentMemberId())
        .map(UserResponse::of)
        .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
  }
}
