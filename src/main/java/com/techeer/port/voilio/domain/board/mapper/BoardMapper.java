package com.techeer.port.voilio.domain.board.mapper;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import java.util.List;
import java.util.stream.Collectors;
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
        .updated_at(board.getUpdateAt())
        .created_at(board.getCreateAt())
        .build();
  }

  public List<BoardResponse> toDto(List<Board> boards) {
    return boards.stream().map(this::toDto).collect(Collectors.toList());
  }
}

    public Board toEntity(Board board) {
        return Board.builder()
            .title(board.getTitle())
            .content(board.getContent())
            .category1(board.getCategory1())
            .category2(board.getCategory2())
            .video_url(board.getVideo_url())
            .thumbnail_url(board.getThumbnail_url())
            .build();
            .isPublic(board.getIsPublic())
    }
}