package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.result.ResultCode.BOARD_CREATED_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.dto.request.BoardRequest;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
public class BoardController {
  private final BoardService boardService;

  @GetMapping("/{board_id}")
  public ResponseEntity<EntityModel<ResultResponse<Board>>> findBoardById(
      @PathVariable Long board_id) {
    Board board = boardService.findBoardById(board_id);
    ResultResponse<Board> responseFormat = new ResultResponse<>(USER_REGISTRATION_SUCCESS, board);
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            EntityModel.of(
                responseFormat,
                linkTo(methodOn(BoardController.class).findBoardById(board_id)).withSelfRel()));
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<ResultResponse> deleteBoard(@PathVariable Long boardId) {
    boardService.deleteBoard(boardId);
    ResultResponse<?> responseFormat = new ResultResponse<>(USER_REGISTRATION_SUCCESS);
    responseFormat.add(linkTo(methodOn(BoardController.class).deleteBoard(boardId)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(responseFormat);
  }

  @PostMapping("/create")
  public ResponseEntity<ResultResponse> createBoard(@Validated @RequestBody BoardRequest request) {
    boardService.createBoard(request);
    ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_CREATED_SUCCESS);
    resultResponse.add(linkTo(methodOn(BoardController.class).createBoard(request)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping
  public ResponseEntity<ResultResponse<List<EntityModel<Board>>>> findBoardByKeyword(
      @RequestParam("search") String search) {
    List<EntityModel<Board>> boards =
        boardService.findBoardByKeyword(search).stream()
            .map(
                board ->
                    EntityModel.of(
                        board,
                        linkTo(methodOn(BoardController.class).findBoardById(board.getId()))
                            .withSelfRel()))
            .collect(Collectors.toList());

    ResultResponse<List<EntityModel<Board>>> resultResponse =
        new ResultResponse<>(USER_REGISTRATION_SUCCESS, boards);

    resultResponse.add(
        linkTo(methodOn(BoardController.class).findBoardByKeyword(search)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
