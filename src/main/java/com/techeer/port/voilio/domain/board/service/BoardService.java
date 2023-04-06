package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardMapper boardMapper;

  public void deleteBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    board.changeDeleted();
    boardRepository.save(board);
  }

  public BoardResponse findBoardById(Long boardId) {
    Board board =
        boardRepository
            .findByIdAndIsDeletedFalseAndIsPublicTrue(boardId)
            .orElseThrow(NotFoundBoard::new);
    return boardMapper.toDto(board);
  }

  @Transactional
  public void createBoard(BoardCreateRequest boardCreateRequest) {
    User user =
        userRepository.findById(boardCreateRequest.getUser_id()).orElseThrow(NotFoundUser::new);
    boardRepository.save(boardCreateRequest.toEntity(user));
  }

  public void hideBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    board.changePublic();
    boardRepository.save(board);
  }

  public List<BoardResponse> findBoardByKeyword(String keyword) {
    List<Board> boards =
        boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword);
    return boardMapper.toDto(boards);
  }

  @Transactional
  public Board updateBoard(Long boardId, BoardUpdateRequest request) {
    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    return boardRepository.save(request.toEntity(board));
  }

  public Page<Board> findAllBoard(Pageable pageable) {
    return boardRepository.findAllByIsDeletedAndIsPublicOrderByCreateAtDesc(false, true, pageable);
  }

  public Page<Board> findBoardByCategory(Category category, Pageable pageable) {
    return boardRepository.findAllByIsDeletedAndIsPublicAndCategory1OrCategory2OrderByCreateAtDesc(false, true, category, category, pageable);
  }
}
