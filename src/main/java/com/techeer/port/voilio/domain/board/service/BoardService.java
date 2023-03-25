package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.dto.request.BoardRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.mappter.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  public Board findBoardById(Long boardId) {
    Board board =
        boardRepository
            .findByIdAndIsDeletedFalseAndIsPublicTrue(boardId)
            .orElseThrow(NotFoundBoard::new);
    return board;
  }

  public void createBoard(BoardRequest request) {
    Board createdBoard = boardRepository.save(request.toEntity());
  }

  public List<BoardResponse> findBoardByKeyword(String keyword) {
    List<Board> boards =
        boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword);
    return boardMapper.toDto(boards);
  }
}
