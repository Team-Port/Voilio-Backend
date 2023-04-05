package com.techeer.port.voilio.domain.user.repository;

import com.techeer.port.voilio.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :id and u.isDeleted=false ")
    Optional<User> findUserById(@Param("id") long id);

}
