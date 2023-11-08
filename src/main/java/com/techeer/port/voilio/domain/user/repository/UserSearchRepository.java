package com.techeer.port.voilio.domain.user.repository;

import com.techeer.port.voilio.domain.user.entity.UserSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSearchRepository extends JpaRepository<UserSearch, Long> {

    List<UserSearch> findAllByUserIdOrderByUpdateAtDesc(Long userId);
}
