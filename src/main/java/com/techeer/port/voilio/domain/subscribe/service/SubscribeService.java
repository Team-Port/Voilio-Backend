package com.techeer.port.voilio.domain.subscribe.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.repository.SubscribeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.YnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

  @Autowired private SubscribeRepository subscribeRepository;

  @Autowired private UserRepository userRepository;

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
}
