package com.techeer.port.voilio.domain.user.repository;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.YnType;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  //  @Query("SELECT u FROM User u WHERE u.id = :id and u.delYn = 'N' ")
  //  Optional<User> findUserById(@Param("id") long id);

  Optional<User> findUserByIdAndDelYn(long id, YnType delYn);

  //  @Query("SELECT u FROM User u WHERE u.email = :email and u.delYn = 'N' ")
  //  Optional<User> findUserByEmail(@Param("email") String email);

  Optional<User> findUserByEmailAndDelYn(String email, YnType delYn);

  boolean existsByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.nickname = :nickname and u.delYn = 'N' ")
  Optional<User> findUserByNickname(@Param("nickname") String nickname);

  Optional<User> findUserByNicknameAndDelYn(String nickname, YnType delYn);

  @Query("SELECT b.user.id FROM Board b WHERE b.id = :boardId")
  Long findUserIdByBoardId(@Param("boardId") Long boardId);

  List<User> findTop5ByDelYnOrderByIdDesc(YnType delYn);
}
