package com.techeer.port.voilio.domain.user.mapper;

import com.techeer.port.voilio.domain.user.dto.UserSearchDto;
import com.techeer.port.voilio.domain.user.entity.UserSearch;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserSearchMapper {

    UserSearchMapper INSTANCE = Mappers.getMapper(UserSearchMapper.class);

    UserSearchDto toDto(UserSearch entity);
    List<UserSearchDto> toDtos(List<UserSearch> entities);
}
