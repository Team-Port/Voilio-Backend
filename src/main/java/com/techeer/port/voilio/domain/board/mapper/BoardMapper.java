package com.techeer.port.voilio.domain.board.mapper;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.dto.BoardSimpleDto;
import com.techeer.port.voilio.domain.board.dto.BoardThumbnailDto;
import com.techeer.port.voilio.domain.board.dto.BoardVideoDto;
import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.response.UploadFileResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface BoardMapper {

  BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

  Board toEntity(BoardCreateRequest boardCreateRequest);

  UploadFileResponse toVideoAndThumbnail(String videoUrl, String thumbnail);

  BoardVideoDto toVideo(String videoUrl);

  BoardThumbnailDto toThumbnail(String thumbnailUrl);

  BoardDto toDto(Board board);

  BoardSimpleDto toSimpleDto(Board board);

  List<BoardDto> toDtos(List<Board> boards);

  default Page<BoardDto> toPageList(Page<Board> boardList) {
    return boardList.map(this::toDto);
  }
}
