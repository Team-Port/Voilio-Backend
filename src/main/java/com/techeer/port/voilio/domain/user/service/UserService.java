package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.mapper.UserMapper;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.techeer.port.voilio.global.common.YnType.N;
import static com.techeer.port.voilio.global.common.YnType.Y;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  public List<UserResponse> getUserList() {
    List<User> users = new ArrayList<User>(userRepository.findAll());
    return UserMapper.INSTANCE.toDtos(users);
  }

  public User getUser(Long userId) {
    User user = userRepository.findUserByIdAndDelYn(userId, N).orElseThrow(NotFoundUser::new);
    return user;
  }

  public User getUser(String nickname) {
    User user = userRepository.findUserByNicknameAndDelYn(nickname, N).orElseThrow(NotFoundUser::new);
    return user;
  }

  public Long getUserByNickname(String nickname) {
    User user = userRepository.findUserByNicknameAndDelYn(nickname, N).orElseThrow(NotFoundUser::new);
    return user.getId();
  }

  public Long getUserIdByBoardId(Long board_id) {
    Long id = userRepository.findUserIdByBoardId(board_id);

    return id;
  }

  public void deleteUser(Long userId) {
    User user = getUser(userId);
    user.changeDelYn(Y);
    userRepository.save(user);
  }

  public UserResponse getMyInfoBySecurity() {
    return userRepository
        .findById(SecurityUtil.getCurrentMemberId())
        .map(UserResponse::of)
        .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
  }

  public Long getCurrentLoginUser(String authorizationHeader) {
    Long currentLoginUserNickname = null;

    if (!authorizationHeader.isEmpty()) {
      String accessToken = authorizationHeader.substring(7);

      if (!jwtProvider.validateToken(accessToken)) {
        throw new RuntimeException("유효하지 않은 토큰입니다.");
      }

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      currentLoginUserNickname = Long.valueOf(userDetails.getUsername());
    }
    return currentLoginUserNickname;
  }

  public boolean checkSleeperUser(User user) {
    return checkSleeperUser(user.getActivatedAt());
  }

  private static boolean checkSleeperUser(LocalDateTime activatedAt) {
    if (Objects.isNull(activatedAt)) {
      return false;
    }
    return activatedAt.isBefore(LocalDateTime.now().minusYears(1));
  }
}
