package com.techeer.port.voilio.domain.subscribe.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findByUserAndFollower(User user, User follower);

//    @Query("SELECT s.follower FROM Subscribe s WHERE s.user = :user")
//    Page<Subscribe> findAllFollowers(Pageable pageable);

    @Query("SELECT s FROM Subscribe s WHERE s.user.nickname = :nickname")
    Page<Subscribe> findFollowersByNickname(@Param("nickname") String nickname, Pageable pageable);

}
