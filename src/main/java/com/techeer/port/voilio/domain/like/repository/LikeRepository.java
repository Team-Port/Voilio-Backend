package com.techeer.port.voilio.domain.like.repository;

import com.techeer.port.voilio.domain.like.entity.Like;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.LikeDivision;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Optional<Like> findByDivisionAndContentIdAndUser(
      LikeDivision division, Long contentId, User user);

  Long countByDivisionAndAndContentId(LikeDivision division, Long contentId);
}
