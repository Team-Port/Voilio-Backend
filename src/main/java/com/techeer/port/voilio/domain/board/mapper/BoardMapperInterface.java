package com.techeer.port.voilio.domain.board.mapper;


import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BoardMapperInterface {

    BoardMapperInterface INSTANCE = Mappers.getMapper(BoardMapperInterface.class);

    BoardDto toDto (Board entity);

    List<BoardDto> toDtos (List<Board> entities);
}
