package com.techeer.port.voilio.domain.board.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVideoDto {

  @NotNull private String videoUrl;
}
