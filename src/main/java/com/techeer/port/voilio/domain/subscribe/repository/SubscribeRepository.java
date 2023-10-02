package com.techeer.port.voilio.domain.subscribe.repository;

import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.entity.SubscribeId;
import com.techeer.port.voilio.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, SubscribeId> {

  Subscribe findByFromUserAndToUser(User user, User subscribe);

  @Query("SELECT s FROM Subscribe s WHERE s.fromUser.nickname = :nickname")
  Page<Subscribe> findSubscribeByNickname(@Param("nickname") String nickname, Pageable pageable);
}
