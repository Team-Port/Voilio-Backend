package com.techeer.port.voilio.domain.like.dto;

import com.techeer.port.voilio.global.common.LikeDivision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {

  private Long id;

  private Long contentId;

  private LikeDivision division;

  private Long count;

  private String flag;

  public LikeDto(Long contentId, LikeDivision division, Long count, String flag) {
    this.contentId = contentId;
    this.division = division;
    this.count = count;
    this.flag = flag;
  }

  public void updateCount(Long count) {
    this.count = count;
  }

  public void updateFlag(String flag) {
    this.flag = flag;
  }
}
