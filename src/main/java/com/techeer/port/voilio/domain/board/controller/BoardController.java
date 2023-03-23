package com.techeer.port.voilio.domain.board.controller;

import com.techeer.port.voilio.domain.board.dto.request.BoardRequest;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<ResultResponse> createBoard(@Validated @RequestBody BoardRequest request) {
        ResultResponse resultResponse = boardService.createBoard(request);
        resultResponse.add(linkTo(methodOn(BoardController.class).createBoard(request)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}