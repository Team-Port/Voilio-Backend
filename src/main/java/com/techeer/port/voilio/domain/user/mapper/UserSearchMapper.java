package com.techeer.port.voilio.domain.user.mapper;

import com.techeer.port.voilio.domain.user.dto.UserSearchDto;
import com.techeer.port.voilio.domain.user.entity.UserSearch;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserSearchMapper {

  UserSearchMapper INSTANCE = Mappers.getMapper(UserSearchMapper.class);

  UserSearchDto toDto(UserSearch entity);

  List<UserSearchDto> toDtos(List<UserSearch> entities);
}
