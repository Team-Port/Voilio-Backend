package com.techeer.port.voilio.domain.follow.mapper;

import com.techeer.port.voilio.domain.follow.dto.FollowDto;
import com.techeer.port.voilio.domain.follow.dto.FollowSimpleDto;
import com.techeer.port.voilio.domain.follow.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FollowMapper {

  FollowMapper INSTANCE = Mappers.getMapper(FollowMapper.class);

  FollowDto toDto(Follow entity);

  List<FollowDto> toDtos(List<Follow> entities);

  List<FollowSimpleDto> toFollowSimpleDtos(List<Follow> entities);
}
