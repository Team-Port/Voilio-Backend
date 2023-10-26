package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.UserProfileDto;
import com.techeer.port.voilio.domain.user.dto.response.Top5LatestUserResponseDto;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.mapper.UserMapper;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.s3.util.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
  private final S3Manager s3Manager;
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  public List<UserResponse> getUserList() {
    List<User> users = new ArrayList<User>(userRepository.findAll());
    return UserMapper.INSTANCE.toDtos(users);
  }

  public UserDto getUserDto(Long userId) {
    User user = userRepository.findUserByIdAndDelYn(userId, N).orElseThrow(NotFoundUser::new);
    return UserMapper.INSTANCE.toDto(user);
  }

  public User getUser(Long userId) {
    return userRepository.findUserByIdAndDelYn(userId, N).orElseThrow(NotFoundUser::new);
  }

  public User getUser(String nickname) {
    return userRepository.findUserByNicknameAndDelYn(nickname, N).orElseThrow(NotFoundUser::new);
  }

  public UserDto getUserDto(String nickname) {
    User user =
        userRepository.findUserByNicknameAndDelYn(nickname, N).orElseThrow(NotFoundUser::new);
    return UserMapper.INSTANCE.toDto(user);
  }

  public Long getUserByNickname(String nickname) {
    User user =
        userRepository.findUserByNicknameAndDelYn(nickname, N).orElseThrow(NotFoundUser::new);
    return user.getId();
  }

  public Long getUserIdByBoardId(Long board_id) {
    Long id = userRepository.findUserIdByBoardId(board_id);
    return id;
  }

  public void deleteUser(Long userId) {
    UserDto userDto = getUserDto(userId);
    User user = UserMapper.INSTANCE.toEntity(userDto);
    user.changeDelYn(Y);
    userRepository.save(user);
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

  public List<Top5LatestUserResponseDto> getLatestMember() {
    List<User> userList = userRepository.findTop5ByDelYnOrderByIdDesc(N);
    List<Top5LatestUserResponseDto> top5LatestUserResponseDtos =
        UserMapper.INSTANCE.toTop5LatestUserDto(userList);
    return top5LatestUserResponseDtos;
  }

  public UserProfileDto uploadProfileImage(String profileImageUrl, User user) {
    user.changeImageUrl(profileImageUrl);
    userRepository.save(user);

    return UserMapper.INSTANCE.toDto(profileImageUrl);
  }
}
