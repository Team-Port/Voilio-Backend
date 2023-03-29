package com.techeer.port.voilio.domain.board.dto.request;

<<<<<<< develop:src/main/java/com/techeer/port/voilio/domain/board/dto/request/BoardCreateRequest.java
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.entity.User;
=======
>>>>>>> [#23]Fix: Mapper추가로 인한 Response에 있던 toEntity삭제:src/main/java/com/techeer/port/voilio/domain/board/dto/request/BoardRequest.java
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class BoardCreateRequest extends BaseEntity {

  @NotNull private Long user_id;

  @NotBlank private String title;

  @NotBlank private String content;

  @NotNull private Category category1;

  @NotNull private Category category2;

  @URL @NotBlank private String video_url;

<<<<<<< develop:src/main/java/com/techeer/port/voilio/domain/board/dto/request/BoardCreateRequest.java
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
=======
  @Column private boolean isPublic;
>>>>>>> [#23]Fix: Mapper추가로 인한 Response에 있던 toEntity삭제:src/main/java/com/techeer/port/voilio/domain/board/dto/request/BoardRequest.java
}
