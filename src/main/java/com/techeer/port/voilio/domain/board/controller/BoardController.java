package com.techeer.port.voilio.domain.board.controller;

import com.techeer.port.voilio.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int boardId){
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
