package com.techeer.port.voilio.domain.board.dto.request;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
public class BoardCreateRequest extends BaseEntity {

  @NotNull private Long user_id;

  @NotBlank private String title;

  @NotBlank private String content;

  @NotNull private Category category1;

  @NotNull private Category category2;

  @URL @NotBlank private String video_url;

  @URL @NotBlank private String thumbnail_url;

  public Board toEntity(User user) {
    return Board.builder()
        .title(this.title)
        .content(this.content)
        .category1(this.category1)
        .category2(this.category2)
        .video_url(this.video_url)
        .thumbnail_url(this.thumbnail_url)
        .isPublic(true)
        .user(user)
        .build();
  }
}
