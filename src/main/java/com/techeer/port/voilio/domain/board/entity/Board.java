package com.techeer.port.voilio.domain.board.entity;

import com.techeer.port.voilio.global.common.Category;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer board_id;

  @Column private String title;

  @Column private String content;

  @Column
  @Enumerated(EnumType.STRING)
  private Category category1;

  @Column
  @Enumerated(EnumType.STRING)
  private Category category2;

  @Column private String video_url;

  @Column private String thumbnail_url;

  @Column private Boolean isPublic;

  @Builder
  public Board(
      Integer board_id,
      String title,
      String content,
      Category category1,
      Category category2,
      String video_url,
      String thumbnail_url,
      boolean isPublic) {
    this.board_id = board_id;
    this.title = title;
    this.category1 = category1;
    this.category2 = category2;
    this.content = content;
    this.video_url = video_url;
    this.thumbnail_url = thumbnail_url;
    this.isPublic = isPublic;
  }
}
