package com.techeer.port.voilio.domain.comment.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.repository.CommentRepository;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  private final BoardRepository boardRepository;

  public Comment registerComment(CommentRequest commentRequest) {

    Comment createNewCommentEntity = CreateNewCommentEntity(commentRequest);
    Comment comment = commentRepository.save(createNewCommentEntity);

    return comment;
  }

  public Comment CreateNewCommentEntity(CommentRequest commentRequest) {

    Board findBoard = getBoardById(commentRequest.getBoardId());

    return Comment.builder().board(findBoard).content(commentRequest.getContent()).build();
  }

  private Board getBoardById(Long boardId) {
    return boardRepository.findById(boardId).orElseThrow(NotFoundUserException::new);
  }
}
