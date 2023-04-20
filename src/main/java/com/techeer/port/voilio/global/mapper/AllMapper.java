package com.techeer.port.voilio.global.mapper;

import com.techeer.port.voilio.domain.board.dto.response.BoardFindAllDevResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AllMapper {
    private final BoardMapper boardMapper;
    private final UserMapper userMapper;

    public BoardFindAllDevResponse toDto(Board board){
        return BoardFindAllDevResponse.builder()
                .board(boardMapper.toDto(board))
                .user(userMapper.toDto(board.getUser()))
                .build();
    }

    public List<BoardFindAllDevResponse> toDto(List<Board> boards){
        return boards.stream().map(this::toDto).collect(Collectors.toList());
    }
}
