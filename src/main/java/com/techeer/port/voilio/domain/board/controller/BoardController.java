package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.common.ResponseStatus.FETCH_BOARD_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.common.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
public class BoardController {
  private final BoardService boardService;

  @PatchMapping("/{board_id}")
  public ResponseEntity<EntityModel<ResponseFormat<?>>> deleteBoard(@PathVariable Long board_id) {
    boardService.deleteBoard(board_id);
    ResponseFormat<?> responseFormat = new ResponseFormat<>(FETCH_BOARD_SUCCESS);
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            EntityModel.of(
                responseFormat,
                linkTo(methodOn(BoardController.class).deleteBoard(board_id)).withSelfRel()));
  }

  @GetMapping("/{board_id}")
  public ResponseEntity<EntityModel<ResponseFormat<Board>>> findBoardById(
      @PathVariable Long board_id) {
    Board board = boardService.findBoardById(board_id);
    ResponseFormat<Board> responseFormat = new ResponseFormat<>(FETCH_BOARD_SUCCESS, board);
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            EntityModel.of(
                responseFormat,
                linkTo(methodOn(BoardController.class).findBoardById(board_id)).withSelfRel()));
  }
}
