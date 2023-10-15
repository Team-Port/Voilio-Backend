package com.techeer.port.voilio.domain.subscribe.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.repository.SubscribeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

  @Autowired private SubscribeRepository subscribeRepository;

  @Autowired private UserRepository userRepository;
  private final UserService userService;

  public void subscribe(String userName, Long follow_id) {
    User user =
        userRepository
            .findUserByNicknameAndDelYn(userName, YnType.N)
            .orElseThrow(NotFoundUser::new);
    User subscribe = userRepository.findById(follow_id).orElseThrow(NotFoundUser::new);

    Subscribe newFollower = new Subscribe();
    newFollower.setFromUser(user);
    newFollower.setToUser(subscribe);
    subscribeRepository.save(newFollower);
  }

  public void unsubscribe(String userName, Long follow_id) {
    User user =
        userRepository
            .findUserByNicknameAndDelYn(userName, YnType.N)
            .orElseThrow(NotFoundUser::new);
    User subscribe = userRepository.findById(follow_id).orElseThrow(NotFoundUser::new);

    Subscribe followerToDelete = subscribeRepository.findByFromUserAndToUser(user, subscribe);
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
}
