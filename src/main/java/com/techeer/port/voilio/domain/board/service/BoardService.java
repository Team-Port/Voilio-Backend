package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    public void deleteBoard(int id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.changeDeleted();
        boardRepository.save(board);
    }
}
