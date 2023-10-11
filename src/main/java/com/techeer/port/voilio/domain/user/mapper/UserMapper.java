package com.techeer.port.voilio.domain.user.mapper;

import com.techeer.port.voilio.domain.user.dto.UserDto;
import com.techeer.port.voilio.domain.user.dto.request.UserSignUpRequest;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserResponse toSimpleDto(User user);

  UserDto toDto(User user);

  User toEntity(UserDto userDto);

  User toEntity(UserSignUpRequest userSignUpRequest, PasswordEncoder passwordEncoder);

  List<UserResponse> toDtos(List<User> users);
}
