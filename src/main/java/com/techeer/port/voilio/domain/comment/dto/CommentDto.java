package com.techeer.port.voilio.domain.comment.dto;

import com.techeer.port.voilio.domain.user.dto.UserSimpleDto;
import com.techeer.port.voilio.global.common.YnType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

  private Long id;

  private String content;

  private YnType delYn;

  private UserSimpleDto user;

  private Boolean isLiked;

  private Long likeCount;

  private List<CommentDto> childComments = new ArrayList<>();

  private LocalDateTime createdAt;

  public void updateIsLiked(Boolean isLiked) {
    this.isLiked = isLiked;
  }

  public void updateLikeCount(Long likeCount) {
    this.likeCount = likeCount;
  }
}
