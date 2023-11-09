package com.techeer.port.voilio.domain.follow.service;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.follow.dto.FollowSimpleDto;
import com.techeer.port.voilio.domain.follow.entity.Follow;
import com.techeer.port.voilio.domain.follow.exception.AlreadyFollow;
import com.techeer.port.voilio.domain.follow.exception.AlreadyUnfollow;
import com.techeer.port.voilio.domain.follow.mapper.FollowMapper;
import com.techeer.port.voilio.domain.follow.repository.FollowRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.YnType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final BoardRepository boardRepository;

  @Transactional
  public void follow(Long fromUserId, Long toUserId) {
    User fromUser =
        userRepository.findUserByIdAndDelYn(fromUserId, YnType.N).orElseThrow(NotFoundUser::new);
    User toUser = userRepository.findById(toUserId).orElseThrow(NotFoundUser::new);

    if (followRepository.existsByFromUserAndToUser(fromUser, toUser)) {
      throw new AlreadyFollow();
    }

    Follow newFollower = new Follow();
    newFollower.setFromUser(fromUser);
    newFollower.setToUser(toUser);
    followRepository.save(newFollower);
  }

  @Transactional
  public void unFollow(Long fromUserId, Long toUserId) {
    User fromUser =
        userRepository.findUserByIdAndDelYn(fromUserId, YnType.N).orElseThrow(NotFoundUser::new);
    User toUser = userRepository.findById(toUserId).orElseThrow(NotFoundUser::new);

    Follow followerToDelete =
        followRepository
            .findByFromUserAndToUser(fromUser, toUser)
            .orElseThrow(AlreadyUnfollow::new);
    followRepository.delete(followerToDelete);
  }

  public Page<Follow> findFollowsByNickname(String nickname, Pageable pageable) {
    Page<Follow> result = followRepository.findFollowByNickname(nickname, pageable);
    if (result.isEmpty()) {
      throw new NotFoundUser();
    }
    return result;
  }

  public Boolean checkFollow(Long fromUserId, Long toUserId) {
    User fromUser = userService.getUser(fromUserId);
    User toUser = userService.getUser(toUserId);
    if (followRepository.existsByFromUserAndToUser(fromUser, toUser)) return true;
    else return false;
  }

  public List<FollowSimpleDto> getFollowUserList(Long fromUserId) {
    User user =
        userRepository.findUserByIdAndDelYn(fromUserId, YnType.N).orElseThrow(NotFoundUser::new);

    List<Follow> FollowList = followRepository.findByFromUserOrderByIdDesc(user);
    return FollowMapper.INSTANCE.toFollowSimpleDtos(FollowList);
  }

  public List<BoardDto> getFollowUserBoardList(User user) {
    List<BoardDto> boardDtoList = new ArrayList<>();

    List<Follow> FollowList = followRepository.findByFromUserOrderByIdDesc(user);
    for (Follow follow : FollowList) {
      User toUser = follow.getToUser();
      List<Board> boards = toUser.getBoards();
      boardDtoList.addAll(BoardMapper.INSTANCE.toDtos(boards));
    }

    return boardDtoList;
  }
}
