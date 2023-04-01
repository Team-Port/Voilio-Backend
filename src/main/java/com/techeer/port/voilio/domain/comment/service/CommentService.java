package com.techeer.port.voilio.domain.comment.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.comment.dto.request.CommentInfo;
import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.exception.NotFoundCommentException;
import com.techeer.port.voilio.domain.comment.repository.CommentRepository;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  private final BoardRepository boardRepository;

  public CommentInfo registerComment(CommentRequest commentRequest) {

    Comment createNewCommentEntity = CreateNewCommentEntity(commentRequest);
    Comment comment = commentRepository.save(createNewCommentEntity);

    return commentEntityToCommentInfo(comment);
  }

  public CommentInfo updateComment(CommentUpdateRequest commentUpdateRequest, Long commentId) {
    Comment foundComment = findCommentById(commentId);
    foundComment.updateComment(commentUpdateRequest);

    Comment savedComment = commentRepository.save(foundComment);
    return commentEntityToCommentInfo(savedComment);
  }

  private CommentInfo commentEntityToCommentInfo(Comment savedComment) {
    return CommentInfo.builder()
        .content(savedComment.getContent())
        .createAt(savedComment.getCreateAt())
        .updateAt(savedComment.getUpdateAt())
        .build();
  }

  private Comment CreateNewCommentEntity(CommentRequest commentRequest) {

    Board findBoard = getBoardById(commentRequest.getBoardId());
    return Comment.builder().board(findBoard).content(commentRequest.getContent()).build();
  }

  private Board getBoardById(Long boardId) {
    return boardRepository.findById(boardId).orElseThrow(NotFoundUserException::new);
  }

  private Comment findCommentById(Long id) {
    return commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);
  }
}
