package com.techeer.port.voilio.domain.board.controller;

import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.common.ResponseFormat;
import com.techeer.port.voilio.global.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.techeer.port.voilio.global.common.ResponseStatus.FETCH_BOARD_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PatchMapping("/{boardId}")
    public EntityModel<ResponseFormat> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);

        ResponseFormat responseFormat = new ResponseFormat<>(FETCH_BOARD_SUCCESS);
        return EntityModel.status(HttpStatus.OK).headers(create);
    }
}
