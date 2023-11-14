package com.techeer.port.voilio.domain.follow.repository;

import com.techeer.port.voilio.domain.follow.entity.Follow;
import com.techeer.port.voilio.domain.follow.entity.FollowId;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

  List<Follow> findByFromUserOrderByIdDesc(User user);

  Optional<Follow> findByFromUserAndToUser(User user, User Follow);

  boolean existsByFromUserAndToUser(User user, User Follow);

  @Query("SELECT s FROM Follow s WHERE s.fromUser.nickname = :nickname")
  Page<Follow> findFollowByNickname(@Param("nickname") String nickname, Pageable pageable);

  //  Boolean existsByUserNicknameAndAndFollowId(String nickname, Long FollowId);

  Long countFollowByToUser(User fromUser);
}
