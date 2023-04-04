package com.techeer.port.voilio.domain.board.entity;

import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

  @Column @NotNull private String title;

  @Column @NotNull private String content;

  @Column
  @NotNull
  @Enumerated(EnumType.STRING)
  private Category category1;

  @Column
  @NotNull
  @Enumerated(EnumType.STRING)
  private Category category2;

  @Column @NotNull private String video_url;

  @Column @NotNull private String thumbnail_url;

  @Column(columnDefinition = "boolean default true")
  @NotNull
  private boolean isPublic;

<<<<<<< HEAD
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;
=======
  //  @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "user_id")
  //  @NotNull
  //  private User user;
>>>>>>> d66ed0574e9ca9b12e4741faeb784f456daf71dc

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
      boolean isPublic) {
    this.title = title;
    this.category1 = category1;
    this.category2 = category2;
    this.content = content;
    this.video_url = video_url;
    this.thumbnail_url = thumbnail_url;
    this.isPublic = isPublic;
    //    this.user = user;
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
    this.isPublic = false;
  }
}
