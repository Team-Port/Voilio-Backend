package com.techeer.port.voilio.domain.like.repository;

import com.techeer.port.voilio.domain.like.entity.Like;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.LikeDivision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeCustomRepository {

  Page<Like> findByDivisionAndUser(LikeDivision division, User user, Pageable pageable);
}
