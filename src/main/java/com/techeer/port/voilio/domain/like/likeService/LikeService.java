package com.techeer.port.voilio.domain.like.likeService;

import com.techeer.port.voilio.domain.like.dto.LikeCreateRequestDto;
import com.techeer.port.voilio.domain.like.dto.LikeDto;
import com.techeer.port.voilio.domain.like.entity.Like;
import com.techeer.port.voilio.domain.like.mapper.LikeMapper;
import com.techeer.port.voilio.domain.like.repository.LikeCustomRepository;
import com.techeer.port.voilio.domain.like.repository.LikeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.LikeDivision;
import java.util.List;
import java.util.Optional;
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

  private final LikeRepository likeRepository;
  private final LikeCustomRepository likeCustomRepository;

  public Page<LikeDto> findByUserId(LikeDivision division, User user, Pageable pageable) {
    Page<Like> likePage = likeCustomRepository.findByDivisionAndUser(division, user, pageable);
    List<LikeDto> likeDtoList = LikeMapper.INSTANCE.toDtos(likePage.toList());
    return new PageImpl<>(likeDtoList, pageable, likePage.getTotalElements());
  }

  @Transactional
  public LikeDto manageLike(User user, LikeCreateRequestDto likeCreateRequestDto) {
    Optional<Like> optionalLike =
        likeRepository.findByDivisionAndContentIdAndUser(
            likeCreateRequestDto.getDivision(), likeCreateRequestDto.getContentId(), user);
    Long count =
        likeRepository.countByDivisionAndAndContentId(
            likeCreateRequestDto.getDivision(), likeCreateRequestDto.getContentId());
    if (optionalLike.isPresent()) {
      likeRepository.delete(optionalLike.get());
      return new LikeDto(
          likeCreateRequestDto.getContentId(),
          likeCreateRequestDto.getDivision(),
          count - 1,
          "dislike");
    } else {
      Like like = LikeMapper.INSTANCE.toEntity(likeCreateRequestDto);
      like.addUser(user);
      likeRepository.save(like);
      LikeDto likeDto = LikeMapper.INSTANCE.toDto(like);
      likeDto.updateCount(count + 1);
      likeDto.updateFlag("like");
      return likeDto;
    }
  }

  public Long getLikeCount(LikeDivision likeDivision, Long contentId) {
    Long likeCount = likeRepository.countByDivisionAndAndContentId(likeDivision, contentId);
    return likeCount;
  }
}
