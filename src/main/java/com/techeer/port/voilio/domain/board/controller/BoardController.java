package com.techeer.port.voilio.domain.board.controller;


import static com.techeer.port.voilio.global.common.ResponseStatus.FETCH_BOARD_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.common.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
public class BoardController {
  private final BoardService boardService;

  @PatchMapping("/{boardId}")
  public ResponseEntity<ResponseFormat<?>> deleteBoard(@PathVariable Long boardId) {
    boardService.deleteBoard(boardId);
    ResponseFormat<?> responseFormat = new ResponseFormat<>(FETCH_BOARD_SUCCESS);
    responseFormat.add(linkTo(methodOn(BoardController.class).deleteBoard(boardId)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(responseFormat);

  @PostMapping("/create")
  public ResponseEntity<ResultResponse> createBoard(@Validated @RequestBody BoardRequest request) {
    ResultResponse resultResponse = boardService.createBoard(request);
    resultResponse.add(linkTo(methodOn(BoardController.class).createBoard(request)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);

  }
}
