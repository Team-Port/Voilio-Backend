package com.techeer.port.voilio.domain.board.entity;

import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.URL;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boards")
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;

  @Column @NotBlank private String title;

  @Column @NotBlank private String content;

  @Column
  @NotNull
  @Enumerated(EnumType.STRING)
  private Category category1;

  @Column
  @NotNull
  @Enumerated(EnumType.STRING)
  private Category category2;

  @Column @URL @NotBlank private String video_url;

  @Column @URL @NotBlank private String thumbnail_url;

  @Column
  @NotNull
  @ColumnDefault("true")
  private boolean isPublic;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Builder
  public Board(
      String title,
      String content,
      Category category1,
      Category category2,
      String video_url,
      String thumbnail_url,
      User user,
      boolean isPublic) {
    this.title = title;
    this.content = content;
    this.category1 = category1;
    this.category2 = category2;
    this.video_url = video_url;
    this.thumbnail_url = thumbnail_url;
    this.isPublic = isPublic;
    this.user = user;
  }

  public void setBoard(
      String title, String content, Category category1, Category category2, String thumbnail_url) {
    this.title = title;
    this.content = content;
    this.category1 = category1;
    this.category2 = category2;
    this.thumbnail_url = thumbnail_url;
  }

  public void changePublic() {
    this.isPublic = !this.isPublic;
  }
}
