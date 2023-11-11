package com.techeer.port.voilio.domain.board.dto.request;

import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.YnType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardCreateRequest {

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

  @Schema(description = "동영상 URL")
  @NotNull
  private String videoUrl;

  @Schema(description = "썸네일 URL")
  @NotNull
  private String thumbnailUrl;

  @Schema(description = "공개여부")
  @NotNull
  private YnType isPublic;

  @Schema(description = "게시글 유형")
  @NotNull
  private BoardDivision division;


  private List<String> boardImageUrls;
}
