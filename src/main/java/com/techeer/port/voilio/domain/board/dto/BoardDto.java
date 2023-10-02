package com.techeer.port.voilio.domain.board.dto;

import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.YnType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
  private Long id;
  private String title;
  private String content;

  private Category category1;

  private Category category2;

  private String videoUrl;

  private String thumbnailUrl;

  private Long view;

  private BoardDivision division;

  private YnType isPublic;

  private YnType delYn;

  private List<BoardImageDto> boardImages = new ArrayList<>();
}
