package com.techeer.port.voilio.domain.subscribe.mapper;

import com.techeer.port.voilio.domain.subscribe.dto.SubscribeDto;
import com.techeer.port.voilio.domain.subscribe.dto.SubscribeSimpleDto;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubscribeMapper {

  SubscribeMapper INSTANCE = Mappers.getMapper(SubscribeMapper.class);

  SubscribeDto toDto(Subscribe entity);

  List<SubscribeDto> toDtos(List<Subscribe> entities);

  List<SubscribeSimpleDto> toSubscribeSimpleDtos(List<Subscribe> entities);
}
