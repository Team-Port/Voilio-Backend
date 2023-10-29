package com.techeer.port.voilio.domain.like;


import com.techeer.port.voilio.domain.like.dto.LikeDto;
import com.techeer.port.voilio.domain.like.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LikeMapper {

    LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);

    LikeDto toDto(Like like);
    List<LikeDto> toDtos(List<Like> likes);
}
