package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.global.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;

  public void deleteBoard(Long boardId) {
    Board board =
        boardRepository
            .findById(boardId)
            .orElseThrow(() -> new NotFoundException("Board not found"));
    board.changeDeleted();
    boardRepository.save(board);
  }

  public Board findBoardById(Long boardId) {
    Board board = boardRepository.findByIdAndIsDeletedFalseAndIsPublicTrue(boardId).orElseThrow(()-> new NotFoundException("Board not found"));
    return board;
  }
}
