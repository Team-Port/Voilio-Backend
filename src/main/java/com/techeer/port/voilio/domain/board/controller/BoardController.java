package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;
import static com.techeer.port.voilio.global.result.ResultCode.BOARD_CREATED_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.BOARD_FINDALL_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.BOARD_UPDATED_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
public class BoardController {

  @GetMapping("/{board_id}")
  public ResponseEntity<EntityModel<ResultResponse<Board>>> findBoardById(
      @PathVariable Long board_id) {
    BoardResponse board = boardService.findBoardById(board_id);
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

    @GetMapping("/list")
    public ResponseEntity<PagedModel<EntityModel<BoardResponse>>> findAllBoard(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Board> boardPage = boardService.findAllBoard(PageRequest.of(page, size));
        List<EntityModel<BoardResponse>> boardModels = boardPage.getContent().stream()
            .map(board -> EntityModel.of(
                boardMapper.toDto(board),
                linkTo(methodOn(BoardController.class).findBoardById(board.getId())).withSelfRel()))

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
            boardPage.getSize(),
            boardPage.getTotalElements(),
            boardPage.getTotalPages());
        PagedModel<EntityModel<BoardResponse>> result = PagedModel.of(
            boardModels, metadata,
            linkTo(methodOn(BoardController.class).findAllBoard(page, size)).withSelfRel());

        ResultResponse<PagedModel<EntityModel<BoardResponse>>> resultResponse = new ResultResponse<>(BOARD_FINDALL_SUCCESS, result);
        resultResponse.add(linkTo(BoardController.class).slash("list").withSelfRel());
        return ResponseEntity.ok().body(result);


    }
  @PutMapping("/update/{boardId}")
  public ResponseEntity<ResultResponse> updateBoard(
      @PathVariable Long boardId, @RequestBody BoardUpdateRequest request) {
    boardService.updateBoard(boardId, request);
    ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_UPDATED_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(BoardController.class).updateBoard(boardId, request)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}