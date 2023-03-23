package com.techeer.port.voilio.domain.board.dto.request;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequest extends BaseEntity {

  @NotBlank private String title;

  @NotBlank private String content;

  @NotNull private Category category1;

  @NotNull private Category category2;
  @URL private String video_url;

  @URL private String thumbnail_url;

  @Column private boolean isPublic;

  public Board toEntity() {
    return Board.builder()
        .title(this.title)
        .content(this.content)
        .category1(this.category1)
        .category2(this.category2)
        .video_url(this.video_url)
        .thumbnail_url(this.thumbnail_url)
        .isPublic(this.isPublic)
        .build();
  }
}
