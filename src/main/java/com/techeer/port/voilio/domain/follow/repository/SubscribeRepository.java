package com.techeer.port.voilio.domain.follow.repository;

import com.techeer.port.voilio.domain.follow.entity.Subscribe;
import com.techeer.port.voilio.domain.follow.entity.SubscribeId;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, SubscribeId> {

  List<Subscribe> findByFromUserOrderByIdDesc(User user);

  Optional<Subscribe> findByFromUserAndToUser(User user, User subscribe);

  boolean existsByFromUserAndToUser(User user, User subscribe);

  @Query("SELECT s FROM Subscribe s WHERE s.fromUser.nickname = :nickname")
  Page<Subscribe> findSubscribeByNickname(@Param("nickname") String nickname, Pageable pageable);

  //  Boolean existsByUserNicknameAndAndSubscribeId(String nickname, Long subscribeId);
}
