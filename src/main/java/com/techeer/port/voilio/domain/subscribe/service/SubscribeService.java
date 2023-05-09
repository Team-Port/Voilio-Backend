package com.techeer.port.voilio.domain.subscribe.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.repository.SubscribeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

  @Autowired private SubscribeRepository subscribeRepository;

  @Autowired private UserRepository userRepository;

  public void follow(Long user_id, Long follow_id) {
    User user = userRepository.findById(user_id).orElseThrow(NotFoundUser::new);
    User follower = userRepository.findById(follow_id).orElseThrow(NotFoundUser::new);

    Subscribe newFollower = new Subscribe();
    newFollower.setUser(user);
    newFollower.setSubscriber(follower);
    subscribeRepository.save(newFollower);
  }

  public void delete(Long user_id, Long follow_id) {
    User user = userRepository.findById(user_id).orElseThrow(NotFoundUser::new);
    User follower = userRepository.findById(follow_id).orElseThrow(NotFoundUser::new);

    Subscribe followerToDelete = subscribeRepository.findByUserAndSubscriber(user, follower);
    subscribeRepository.delete(followerToDelete);
  }

  public Page<Subscribe> findFollowersByNickname(String nickname, Pageable pageable) {
    Page<Subscribe> result = subscribeRepository.findFollowersByNickname(nickname, pageable);
    if (result.isEmpty()) {
      throw new NotFoundUser();
    }
    return result;
  }
}
