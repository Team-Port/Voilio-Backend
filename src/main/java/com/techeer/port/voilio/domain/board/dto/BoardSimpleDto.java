package com.techeer.port.voilio.domain.board.dto;

import com.techeer.port.voilio.domain.user.dto.UserSimpleDto;
import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.YnType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSimpleDto {
  private Long id;

  private String title;

  private String content;

  private Category category1;

  private Category category2;

  private String videoUrl;

  private String thumbnailUrl;

  private Integer view;

  private Long likeCount;

  private Boolean existLike;

  private BoardDivision division;

  private YnType isPublic;

  private YnType delYn;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  private UserSimpleDto userSimpleDto;

  private List<BoardImageDto> boardImages;
}