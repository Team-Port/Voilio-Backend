package com.techeer.port.voilio.domain.comment.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.domain.comment.dto.CommentDto;
import com.techeer.port.voilio.domain.comment.dto.request.CommentInfo;
import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.comment.dto.response.CommentResponse;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.exception.NotFoundCommentException;
import com.techeer.port.voilio.domain.comment.mapper.CommentMapper;
import com.techeer.port.voilio.domain.comment.repository.CommentRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.techeer.port.voilio.global.common.YnType;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  private final BoardRepository boardRepository;

  private final BoardService boardService;

  @Transactional
  public CommentDto createComment(CommentRequest commentRequest, User user) {
    Board board = boardRepository.findById(commentRequest.getBoardId()).orElseThrow(() -> new BusinessException(ErrorCode.BOARD_NOT_FOUND_ERROR));
    Comment comment = Comment.builder()
            .user(user)
            .board(board)
            .content(commentRequest.getContent())
            .delYn(YnType.N)
            .build();

    if(commentRequest.getParentId() != null){
      Comment parentComment = commentRepository.findByBoardIdAndId(commentRequest.getBoardId(), commentRequest.getParentId()).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION));
      comment.updateParentComment(parentComment);
    }

    commentRepository.save(comment);
    return CommentMapper.INSTANCE.toDto(comment);
  }

  @Transactional
  public CommentDto updateComment(CommentUpdateRequest commentUpdateRequest, Long commentId, User user) {
    Comment comment = commentRepository.findByIdAndUser(commentId, user).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION));
    comment.updateComment(commentUpdateRequest.getContent());

    CommentDto commentDto = CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
    return commentDto;
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

//  private Comment CreateNewCommentEntity(CommentRequest commentRequest) {
//
//    User findUser = getUserById(commentRequest.getUserId());
//    Board findBoard = getBoardById(commentRequest.getBoardId());
//
//    return Comment.builder()
//        .user(findUser)
//        .board(findBoard)
//        .content(commentRequest.getContent())
//        .build();
//  }

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
