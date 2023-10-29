package com.techeer.port.voilio.domain.subscribe.service;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.subscribe.dto.SubscribeSimpleDto;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.exception.AlreadySubscribe;
import com.techeer.port.voilio.domain.subscribe.exception.AlreadyUnsubscribe;
import com.techeer.port.voilio.domain.subscribe.mapper.SubscribeMapper;
import com.techeer.port.voilio.domain.subscribe.repository.SubscribeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.YnType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

  private final SubscribeRepository subscribeRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final BoardRepository boardRepository;

  @Transactional
  public void subscribe(String userName, Long follow_id) {
    User user =
        userRepository
            .findUserByNicknameAndDelYn(userName, YnType.N)
            .orElseThrow(NotFoundUser::new);
    User subscribe = userRepository.findById(follow_id).orElseThrow(NotFoundUser::new);

    Optional<Subscribe> findSubscribe =
        subscribeRepository.findByFromUserAndToUser(user, subscribe);
    if (findSubscribe.isPresent()) {
      throw new AlreadySubscribe();
    }

    Subscribe newFollower = new Subscribe();
    newFollower.setFromUser(user);
    newFollower.setToUser(subscribe);
    subscribeRepository.save(newFollower);
  }

  @Transactional
  public void unsubscribe(String userName, Long follow_id) {
    User user =
        userRepository
            .findUserByNicknameAndDelYn(userName, YnType.N)
            .orElseThrow(NotFoundUser::new);
    User subscribe = userRepository.findById(follow_id).orElseThrow(NotFoundUser::new);

    Subscribe followerToDelete =
        subscribeRepository
            .findByFromUserAndToUser(user, subscribe)
            .orElseThrow(AlreadyUnsubscribe::new);
    subscribeRepository.delete(followerToDelete);
  }

  public Page<Subscribe> findSubscribesByNickname(String nickname, Pageable pageable) {
    Page<Subscribe> result = subscribeRepository.findSubscribeByNickname(nickname, pageable);
    if (result.isEmpty()) {
      throw new NotFoundUser();
    }
    return result;
  }

  public Boolean checkSubscribe(String nickname, Long subscribeId) {
    User user1 = userService.getUser(nickname);
    User user2 = userService.getUser(subscribeId);
    if (user1.equals(user2)) return true;
    //    return subscribeRepository.existsByUserNicknameAndAndSubscribeId(nickname, subscribeId);
    return null;
  }

  public List<SubscribeSimpleDto> getSubscribeUserList(Long fromUserId) {
    User user =
        userRepository.findUserByIdAndDelYn(fromUserId, YnType.N).orElseThrow(NotFoundUser::new);

    List<Subscribe> subscribeList = subscribeRepository.findByFromUserOrderByIdDesc(user);
    return SubscribeMapper.INSTANCE.toSubscribeSimpleDtos(subscribeList);
  }

  public List<BoardDto> getSubscribeUserBoardList(User user) {
    List<BoardDto> boardDtoList = new ArrayList<>();

    List<Subscribe> subscribeList = subscribeRepository.findByFromUserOrderByIdDesc(user);
    for (Subscribe subscribe : subscribeList) {
      User toUser = subscribe.getToUser();
      List<Board> boards = toUser.getBoards();
      boardDtoList.addAll(BoardMapper.INSTANCE.toDtos(boards));
    }

    return boardDtoList;
  }
}
