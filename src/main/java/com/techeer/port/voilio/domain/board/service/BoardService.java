package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    public void deleteBoard(int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.changeDeleted();
        boardRepository.save(board);
    }
}
