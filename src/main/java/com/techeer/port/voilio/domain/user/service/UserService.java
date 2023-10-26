package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.AlreadyExistUserByNickname;
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
import java.util.*;

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

  public String createRandomNickname() {
    String[] adjectiveList = {"멋있는", "친절한", "개성 있는", "실력 있는", "자신감 넘치는", "귀여윤"};
//    String[] adjectiveList1 = {"멋있는"};
    String[] nounList = {"강아지", "고양이", "코알라", "꺼차", "뻐꾸기", "판다", "여우"};
//    String[] nounList1 = {"강아지"};

    Random random = new Random();
    int index = random.nextInt(adjectiveList.length);
    String adjective = adjectiveList[index];

    index = random.nextInt(nounList.length);
    String noun = nounList[index];

    String nickname = adjective + " " + noun;

    Optional<User> findUser = userRepository.findUserByNicknameAndDelYn(nickname, N);

    if(findUser.isEmpty()){
      return nickname;
    }
    else throw new AlreadyExistUserByNickname();

  }

  //  public List<Top5LatestMemberResponseDto> getLatestMember() {
  //    List<User> userList = userRepository.findTop5ByIsDeletedOrderByCreateAtDesc(false);
  //    List<Top5LatestMemberResponseDto> top5LatestMemberResponseDtos = new ArrayList<>();
  //    for (User user : userList) {
  //      Top5LatestMemberResponseDto top5LatestMemberResponseDto =
  //          Top5LatestMemberResponseDto.builder()
  //              .memberId(user.getId())
  //              .nickname(user.getNickname())
  //              .build();
  //
  //      top5LatestMemberResponseDtos.add(top5LatestMemberResponseDto);
  //    }
  //
  //    return top5LatestMemberResponseDtos;
  //  }
}
