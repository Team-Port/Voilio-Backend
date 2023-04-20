package com.techeer.port.voilio.domain.user.mapper;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.dto.response.UserResponse;
import com.techeer.port.voilio.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toDto(User user) {
        return UserResponse.builder().id(user.getId()).email(user.getEmail()).password(user.getPassword()).nickname(user.getNickname()).build();
    }
}
