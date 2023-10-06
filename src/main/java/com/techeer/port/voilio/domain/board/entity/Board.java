package com.techeer.port.voilio.domain.board.entity;

import static com.techeer.port.voilio.global.common.YnType.N;
import static com.techeer.port.voilio.global.common.YnType.Y;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.YnType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boards")
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;

  private String title;

  private String content;

  @Enumerated(EnumType.STRING)
  private Category category1;

  @Enumerated(EnumType.STRING)
  private Category category2;

  @Column(name = "video_url")
  private String videoUrl;

  @Column(name = "thumbnail_url")
  private String thumbnailUrl;

  private Long view;

  @Enumerated(EnumType.STRING)
  private BoardDivision division;

  @Enumerated(EnumType.STRING)
  private YnType isPublic;

  @Enumerated(EnumType.STRING)
  private YnType delYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BoardImage> boardImages = new ArrayList<>();

  public void changeDelYn(YnType delYn) {
    this.delYn = delYn;
  }

  @Builder
  public Board(
      String title,
      String content,
      Category category1,
      Category category2,
      String videoUrl,
      String thumbnail_url,
      User user,
      YnType isPublic) {
    this.title = title;
    this.content = content;
    this.category1 = category1;
    this.category2 = category2;
    this.videoUrl = videoUrl;
    this.thumbnailUrl = thumbnail_url;
    this.isPublic = isPublic;
    this.user = user;
  }

  public void setBoard(
      String title, String content, Category category1, Category category2, String thumbnailUrl) {
    this.title = title;
    this.content = content;
    this.category1 = category1;
    this.category2 = category2;
    this.thumbnailUrl = thumbnailUrl;
  }

  public void changePublic() {
    if (this.isPublic.equals(YnType.N)) {
      this.isPublic = Y;
    }
    ;
    this.isPublic = N;
  }

  public YnType getIsPublic() {
    return this.isPublic;
  }
}
