package com.techeer.port.voilio.domain.board.dto.request;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.YnType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class BoardUpdateRequest {

  @Schema(description = "게시글 제목")
  @NotBlank
  private String title;

  @Schema(description = "게시글 내용")
  @NotBlank
  private String content;

  @Schema(description = "게시글 요약글")
  private String summary;

  @Schema(description = "카테고리")
  @NotNull
  private Category category1;

  @Schema(description = "카테고리")
  @NotNull
  private Category category2;

  @Schema(description = "썸네일 URL")
  @NotNull
  private String thumbnailUrl;

  private List<String> boardImageUrls;
}
