package com.techeer.port.voilio.domain.subscribe.repository;

import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

<<<<<<< Updated upstream
  Subscribe findByUserAndFollower(User user, User follower);
=======
    Subscribe findByUserAndSubscriber(User user, User subscriber);
>>>>>>> Stashed changes

  //    @Query("SELECT s.follower FROM Subscribe s WHERE s.user = :user")
  //    Page<Subscribe> findAllFollowers(Pageable pageable);

  @Query("SELECT s FROM Subscribe s WHERE s.user.nickname = :nickname")
  Page<Subscribe> findFollowersByNickname(@Param("nickname") String nickname, Pageable pageable);
}
