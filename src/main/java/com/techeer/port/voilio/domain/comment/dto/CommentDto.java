package com.techeer.port.voilio.domain.comment.dto;

import com.techeer.port.voilio.global.common.YnType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDto {

    private Long id;

    private String content;

    private YnType delYn;
}
