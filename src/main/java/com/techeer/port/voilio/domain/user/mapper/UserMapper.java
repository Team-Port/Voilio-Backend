package com.techeer.port.voilio.domain.user.mapper;

import com.techeer.port.voilio.domain.user.dto.UserDetailDto;
import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.UserSimpleDto;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.dto.response.Top5LatestUserResponseDto;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserResponse toSimpleDto(User user);

  UserDto toDto(User user);

  UserDetailDto toDetailDto(User user);

  UserSimpleDto toSimpleDto1(User user);

  User toEntity(UserDto userDto);

  User toEntity(UserSignUpRequest userSignUpRequest);

  List<UserResponse> toDtos(List<User> users);

  List<Top5LatestUserResponseDto> toTop5LatestUserDto(List<User> users);
}
