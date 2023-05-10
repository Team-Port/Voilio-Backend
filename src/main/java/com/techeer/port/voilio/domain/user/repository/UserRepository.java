package com.techeer.port.voilio.domain.user.repository;

import com.techeer.port.voilio.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.id = :id and u.isDeleted=false ")
  Optional<User> findUserById(@Param("id") long id);

  @Query("SELECT u FROM User u WHERE u.email = :email and u.isDeleted=false ")
  Optional<User> findUserByEmail(@Param("email") String email);

  boolean existsByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.nickname = :nickname and u.isDeleted=false ")
  Optional<User> findUserByNickname(@Param("nickname") String nickname);

  @Query("SELECT b.user.id FROM Board b WHERE b.id = :boardId")
  Long findUserIdByBoardId(@Param("boardId") Long boardId);
}
