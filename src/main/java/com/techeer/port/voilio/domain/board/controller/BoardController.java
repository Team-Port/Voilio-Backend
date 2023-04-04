package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.result.ResultCode.BOARD_CREATED_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.BOARD_FINDALL_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.BOARD_UPDATED_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.common.Pagination;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  private final BoardMapper boardMapper;

  @PutMapping("/update/{boardId}")
  public ResponseEntity<ResultResponse> updateBoard(
      @PathVariable Long boardId, @RequestBody Board board) {
    Board updatedBoard = boardMapper.toEntity(board);
    boardService.updateBoard(boardId, updatedBoard);
    ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_UPDATED_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(BoardController.class).updateBoard(boardId, board)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PatchMapping("/{boardId}")
  public ResponseEntity<ResultResponse> deleteBoard(@PathVariable Long boardId) {
    boardService.deleteBoard(boardId);
    ResultResponse<?> responseFormat = new ResultResponse<>(USER_REGISTRATION_SUCCESS);
    responseFormat.add(linkTo(methodOn(BoardController.class).deleteBoard(boardId)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(responseFormat);
  }

  @PostMapping("/create")
  public ResponseEntity<ResultResponse> createBoard(@Validated @RequestBody Board board) {
    boardService.createBoard(board);
    ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_CREATED_SUCCESS);
    resultResponse.add(linkTo(methodOn(BoardController.class).createBoard(board)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

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

  @GetMapping("/list")
  public ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> findAllBoard(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
    Page<Board> boardPage = boardService.findAllBoard(PageRequest.of(page, size));
    List<EntityModel<BoardResponse>> boardLists =
        boardPage.getContent().stream()
            .map(
                board ->
                    EntityModel.of(
                        boardMapper.toDto(board),
                        linkTo(methodOn(BoardController.class).findBoardById(board.getId()))
                            .withSelfRel()))
            .collect(Collectors.toList());

    Pagination<EntityModel<BoardResponse>> result =
        new Pagination<>(
            boardLists,
            boardPage.getNumber(),
            boardPage.getSize(),
            boardPage.getTotalElements(),
            boardPage.getTotalPages(),
            linkTo(methodOn(BoardController.class).findAllBoard(page, size)).withSelfRel());

    ResultResponse<Pagination<EntityModel<BoardResponse>>> resultResponse =
        new ResultResponse<>(BOARD_FINDALL_SUCCESS, result);
    return ResponseEntity.ok().body(resultResponse);
  }
}
