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

import java.util.ArrayList;
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

        if (commentRequest.getParentId() != null) {
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

    @Transactional
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findByIdAndUser(id, user).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION));
        comment.deleteComment();
        commentRepository.save(comment);
    }

    public List<CommentDto> findCommentByBoardId(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new NotFoundBoard();
        }
        List<Comment> commentList = commentRepository.findAllByBoardIdAndDelYnAndParentCommentIsNull(id, YnType.N);
        List<CommentDto> commentDtoList = CommentMapper.INSTANCE.toDtos(commentList);
        return commentDtoList;
    }

}
