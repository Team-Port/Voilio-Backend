package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.follow.repository.FollowRepository;
import com.techeer.port.voilio.domain.user.dto.UserDetailDto;
import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.response.Top5LatestUserResponseDto;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.mapper.UserMapper;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.s3.util.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.techeer.port.voilio.global.common.YnType.N;
import static com.techeer.port.voilio.global.common.YnType.Y;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final S3Manager s3Manager;
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  public List<UserResponse> getUserList() {
    List<User> users = new ArrayList<User>(userRepository.findAll());
    return UserMapper.INSTANCE.toDtos(users);
  }

  public UserDetailDto getUserDto(Long userId) {
    User user = userRepository.findUserByIdAndDelYn(userId, N).orElseThrow(NotFoundUser::new);
    UserDetailDto userDetailDto = UserMapper.INSTANCE.toDetailDto(user);

    Long normalCount = boardRepository.countBoardByUserAndDivision(user, BoardDivision.NORMAL);
    Long videoCount = boardRepository.countBoardByUserAndDivision(user, BoardDivision.VIDEO);
    Long followCount = followRepository.countFollowByToUser(user);

    userDetailDto.changeNormalCount(normalCount);
    userDetailDto.changeVideoCount(videoCount);
    userDetailDto.changeFollowerCount(followCount);

    return userDetailDto;
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

  @Transactional
  public void deleteUser(Long userId) {
    User user = userRepository.findUserByIdAndDelYn(userId, N).get();
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

  public String createRandomNickname() {
    String[] adjectiveList = {"멋있는", "친절한", "개성 있는", "실력 있는", "자신감 넘치는", "귀여운"};
    String[] nounList = {"강아지", "고양이", "코알라", "까치", "뻐꾸기", "판다", "여우"};

    Random random = new Random();

    while (true) {
      int index = random.nextInt(adjectiveList.length);
      String adjective = adjectiveList[index];

      index = random.nextInt(nounList.length);
      String noun = nounList[index];

      String nickname = adjective + " " + noun;

      Optional<User> findUser = userRepository.findUserByNicknameAndDelYn(nickname, N);

      if (findUser.isEmpty()) {
        return nickname;
      }
    }
  }

  public List<Top5LatestUserResponseDto> getLatestMember() {
    List<User> userList = userRepository.findTop5ByDelYnOrderByIdDesc(N);
    List<Top5LatestUserResponseDto> top5LatestUserResponseDtos =
        UserMapper.INSTANCE.toTop5LatestUserDto(userList);
    return top5LatestUserResponseDtos;
  }

  @Transactional
  public String uploadProfileImage(String profileImageUrl, User user) {
    user.changeImageUrl(profileImageUrl);
    userRepository.save(user);

    return profileImageUrl;
  }
}
