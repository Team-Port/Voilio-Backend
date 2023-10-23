package com.techeer.port.voilio.domain.comment.mapper;

import com.techeer.port.voilio.domain.comment.dto.CommentDto;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

  CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

  CommentDto toDto(Comment comment);

  List<CommentDto> toDtos(List<Comment> comments);
}
