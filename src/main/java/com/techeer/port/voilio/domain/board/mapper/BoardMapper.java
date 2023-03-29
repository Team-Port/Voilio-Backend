package com.techeer.port.voilio.domain.board.mapper;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardResponse toDto(Board board) {
        return BoardResponse.builder()
            .id(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .category1(board.getCategory1())
            .category2(board.getCategory2())
            .video_url(board.getVideo_url())
            .thumbnail_url(board.getThumbnail_url())
            .created_at(board.getCreateAt())
            .build();
    }

    public Board toEntity(Board board) {
        return Board.builder()
            .title(board.getTitle())
            .content(board.getContent())
            .category1(board.getCategory1())
            .category2(board.getCategory2())
            .video_url(board.getVideo_url())
            .thumbnail_url(board.getThumbnail_url())
            .isPublic(board.getIsPublic())
            .build();
    }
}
