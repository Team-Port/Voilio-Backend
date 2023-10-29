package com.techeer.port.voilio.domain.like;

import com.techeer.port.voilio.domain.like.dto.LikeDto;
import com.techeer.port.voilio.domain.like.entity.Like;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LikeMapper {

  LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);

  LikeDto toDto(Like like);

  List<LikeDto> toDtos(List<Like> likes);
}
