package com.techeer.port.voilio.domain.comment.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.domain.comment.dto.request.CommentInfo;
import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.comment.dto.response.CommentResponse;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.exception.NotFoundCommentException;
import com.techeer.port.voilio.domain.comment.repository.CommentRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  private final BoardRepository boardRepository;

  private final BoardService boardService;

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

  public void deleteComment(Long id) {
    Comment foundComment = findCommentById(id);
    foundComment.deleteComment();
    commentRepository.save(foundComment);
  }

  public List<CommentResponse> findCommentByBoardId(Long id) {

    if (!boardRepository.existsById(id)) {
      throw new NotFoundBoard();
    }

    List<CommentResponse> commentList =
        commentRepository.findByBoardId(id).stream()
            .map(this::commentEntityToCommentResponse)
            .collect(Collectors.toList());
    return commentList;
  }

  private CommentInfo commentEntityToCommentInfo(Comment savedComment) {
    return CommentInfo.builder()
        .content(savedComment.getContent())
        .createAt(savedComment.getCreateAt())
        .updateAt(savedComment.getUpdateAt())
        .build();
  }

  private CommentResponse commentEntityToCommentResponse(Comment comment) {
    return CommentResponse.builder()
        .commentId(comment.getId())
        .content(comment.getContent())
        .nickname(comment.getUser().getNickname())
        .localDateTime(comment.getCreateAt())
        .build();
  }

  private Comment CreateNewCommentEntity(CommentRequest commentRequest) {

    User findUser = getUserById(commentRequest.getUserId());
    Board findBoard = getBoardById(commentRequest.getBoardId());

    return Comment.builder()
        .user(findUser)
        .board(findBoard)
        .content(commentRequest.getContent())
        .build();
  }

  private User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
  }

  private Board getBoardById(Long boardId) {
    return boardRepository.findById(boardId).orElseThrow(NotFoundUserException::new);
  }

  private Comment findCommentById(Long id) {
    return commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);
  }
}
