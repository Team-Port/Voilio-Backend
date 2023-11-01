package com.techeer.port.voilio.domain.board.dto.request;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class CategoryRequest {

  @Nullable private String category1;

  @Nullable private String category2;
}
