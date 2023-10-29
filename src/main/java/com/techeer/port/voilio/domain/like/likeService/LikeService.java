package com.techeer.port.voilio.domain.like.likeService;

import com.techeer.port.voilio.domain.like.LikeMapper;
import com.techeer.port.voilio.domain.like.dto.LikeDto;
import com.techeer.port.voilio.domain.like.entity.Like;
import com.techeer.port.voilio.domain.like.repository.LikeCustomRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.LikeDivision;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

  private final LikeCustomRepository likeCustomRepository;

  public Page<LikeDto> findByUserId(LikeDivision division, User user, Pageable pageable) {
    Page<Like> likePage = likeCustomRepository.findByDivisionAndUser(division, user, pageable);
    List<LikeDto> likeDtoList = LikeMapper.INSTANCE.toDtos(likePage.toList());
    return new PageImpl<>(likeDtoList, pageable, likePage.getTotalElements());
  }
}
