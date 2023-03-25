package com.techeer.port.voilio.domain.board.mappter;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoardMapper {
    public BoardResponse toDto(Board board){
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category1(board.getCategory1())
                .category2(board.getCategory2())
                .video_url(board.getVideo_url())
                .thumbnail_url(board.getThumbnail_url())
                .updated_at(board.getUpdateAt())
                .created_at(board.getCreateAt())
                .build();
    }

    public List<BoardResponse> toDto(List<Board> boards){
        return boards.stream().map(this::toDto).collect(Collectors.toList());
    }
}
