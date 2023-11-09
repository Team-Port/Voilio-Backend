package com.techeer.port.voilio.domain.follow.mapper;

import com.techeer.port.voilio.domain.follow.dto.SubscribeDto;
import com.techeer.port.voilio.domain.follow.dto.SubscribeSimpleDto;
import com.techeer.port.voilio.domain.follow.entity.Subscribe;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubscribeMapper {

  SubscribeMapper INSTANCE = Mappers.getMapper(SubscribeMapper.class);

  SubscribeDto toDto(Subscribe entity);

  List<SubscribeDto> toDtos(List<Subscribe> entities);

  List<SubscribeSimpleDto> toSubscribeSimpleDtos(List<Subscribe> entities);
}
