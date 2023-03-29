package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
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
  private final BoardMapper boardMapper;

  public void deleteBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    board.changeDeleted();
    boardRepository.save(board);
  }

  public void createBoard(Board board) {
    Board createdBoard = boardRepository.save(boardMapper.toEntity(board));
  }

  public Board findBoardById(Long boardId) {
    Board board =
        boardRepository
            .findByIdAndIsDeletedFalseAndIsPublicTrue(boardId)
            .orElseThrow(NotFoundBoard::new);
    return board;
  }

  public Page<Board> findAllBoard(Pageable pageable) {
    return boardRepository.findAllByIsDeletedAndIsPublic(false, true, pageable);
  }
}
