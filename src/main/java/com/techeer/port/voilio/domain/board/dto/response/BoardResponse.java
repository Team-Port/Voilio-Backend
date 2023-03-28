package com.techeer.port.voilio.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techeer.port.voilio.global.common.Category;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponse extends RepresentationModel<BoardResponse> {
  @JsonInclude(Include.NON_NULL)
  private List<BoardData> data;

  @JsonCreator
  public BoardResponse(List<BoardData> data) {
    this.data = data;
  }

  @Getter
  @Setter
  public static class BoardData {
    private final Long board_id;
    private final String title;
    private final String content;
    private final Category category1;
    private final Category category2;
    private final String thumbnail_url;

    @JsonCreator
    public BoardData(
        Long board_id,
        String title,
        String content,
        Category category1,
        Category category2,
        String thumbnail_url) {
      this.board_id = board_id;
      this.title = title;
      this.content = content;
      this.category1 = category1;
      this.category2 = category2;
      this.thumbnail_url = thumbnail_url;
    }
  }
}
