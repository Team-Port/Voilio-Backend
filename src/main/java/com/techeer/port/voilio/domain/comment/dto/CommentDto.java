package com.techeer.port.voilio.domain.comment.dto;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.global.common.YnType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentDto {

  private Long id;

  private String content;

  private YnType delYn;
}
