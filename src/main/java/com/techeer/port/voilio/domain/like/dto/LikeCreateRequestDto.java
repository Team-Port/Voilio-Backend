package com.techeer.port.voilio.domain.like.dto;


import com.techeer.port.voilio.global.common.LikeDivision;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeCreateRequestDto {

    private Long contentId;

    private LikeDivision division;
}
