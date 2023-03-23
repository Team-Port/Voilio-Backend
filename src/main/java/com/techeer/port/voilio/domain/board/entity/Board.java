package com.techeer.port.voilio.domain.board.entity;

import com.sun.istack.NotNull;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull @Column private Long userId;

  @NotNull @Column private String title;

  @Column
  @Enumerated(EnumType.STRING)
  private Category category1;

  @Column
  @Enumerated(EnumType.STRING)
  private Category category2;

  @Column private String content;

  @Column private String video;

  @Column private String thumbnail;

  @Column(columnDefinition = "boolean default true")
  private boolean isPublic;

  public boolean getIsPublic() {
    return this.isPublic;
  }
}
