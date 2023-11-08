package com.techeer.port.voilio.domain.user.repository;

import com.techeer.port.voilio.domain.user.entity.UserSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSearchRepository extends JpaRepository<UserSearch, Long> {

    List<UserSearch> findAllByUserIdOrderByUpdateAtDesc(Long userId);

    Optional<UserSearch> findByUserIdAndContent(Long userId, String content);
}
