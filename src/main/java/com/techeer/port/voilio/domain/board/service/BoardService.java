package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.dto.request.BoardRequest;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.global.result.ResultCode;
import com.techeer.port.voilio.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public ResultResponse createBoard(BoardRequest request) {
        Board createdBoard = boardRepository.save(request.toEntity());
        return new ResultResponse(ResultCode.BOARD_CREATED_SUCCESS);
    }
}
