package com.techeer.port.voilio.domain.board.mapper;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.dto.response.UploadFileResponse;
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
        .video_url(board.getVideoUrl())
        .thumbnail_url(board.getThumbnailUrl())
        .updated_at(board.getUpdateAt())
        .created_at(board.getCreateAt())
        .user_id(board.getUser().getId())
        .nickname(board.getUser().getNickname())
        .isPublic(board.getIsPublic())
        .build();
  }

  public List<BoardResponse> toDto(List<Board> boards) {
    return boards.stream().map(this::toDto).collect(Collectors.toList());
  }

  public UploadFileResponse toDto(String videoUrl, String thumbnailUrl) {
    return UploadFileResponse.builder().video_url(videoUrl).thumbnail_url(thumbnailUrl).build();
  }

  public UploadFileResponse toDto(String thumbnailUrl) {
    return UploadFileResponse.builder().thumbnail_url(thumbnailUrl).build();
  }
}
