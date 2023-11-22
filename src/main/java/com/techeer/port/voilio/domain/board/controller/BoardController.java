package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.dto.BoardSimpleDto;
import com.techeer.port.voilio.domain.board.dto.BoardThumbnailDto;
import com.techeer.port.voilio.domain.board.dto.BoardVideoDto;
import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.UploadDivision;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Board", description = "Board API Document")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
@Slf4j
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/lists")
  @Operation(summary = "전체 게시물 출력", description = "전체 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findsAllBoard(
      @ParameterObject @PageableDefault(size = 20) Pageable pageable) {

    Page<BoardDto> allBoard = boardService.findAllBoard(pageable);

    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, allBoard));
  }

  @GetMapping("/{boardId}")
  @Operation(summary = "개별 게시물 출력", description = "개별 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findBoardById(
      @PathVariable Long boardId, @AuthenticationPrincipal User user) {
    BoardSimpleDto boardDto = boardService.findBoardById(boardId, user);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, boardDto));
  }

  @GetMapping("/{userId}/result")
  @Operation(summary = "유저별 게시물 출력", description = "유저아이디로 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findBoardByUserId(
      @ParameterObject @PageableDefault(size = 20) Pageable pageable,
      @PathVariable Long userId,
      @AuthenticationPrincipal User user) {

    Page<BoardDto> allBoard = boardService.findBoardByUser(user, userId, pageable);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, allBoard));
  }

  @GetMapping("/lists/category/{category}")
  @Operation(summary = "카테고리 별 게시물 출력", description = "카테고리 별 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findBoardByCategory(
      @PathVariable("category") Category category,
      @ParameterObject @PageableDefault(size = 30) Pageable pageable) {
    Page<BoardDto> boardPage = boardService.findBoardByCategory(category, pageable);

    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, boardPage));
  }

  @GetMapping("/lists/keyword/{search}")
  @Operation(summary = "키워드가 있는 게시물 출력", description = "키워드가 있는 게시물 출력 입니다.")
  public ResponseEntity<ResultsResponse> findBoardByKeyWord(
      @PathVariable String search, @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
    Page<BoardDto> boardDtos = boardService.findBoardByKeyword(pageable, search);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, boardDtos));
  }

  @GetMapping("/lists/keyword")
  @Operation(summary = "카테고리와 키워드가 있는 게시물 출력", description = "키워드, 카테고리가 있는 게시물 출력 입니다.")
  public ResponseEntity<ResultsResponse> findBoardByCategoryAndKeyWord(
      @RequestParam Category category,
      @RequestParam String search,
      @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
    Page<BoardDto> boardDtos =
        boardService.findBoardByCategoryAndKeyword(category, search, pageable);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, boardDtos));
  }

  @PatchMapping("/{boardId}/hide")
  @Operation(summary = "게시물 숨김", description = "게시물 숨기기 메서드입니다.")
  public ResponseEntity<ResultsResponse> hideBoard(
      @PathVariable Long boardId, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    BoardDto boardDto = boardService.hideBoard(boardId, user);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_UPDATED_SUCCESS, boardDto));
  }

  @PostMapping(value = "/create")
  @Operation(summary = "게시물 생성", description = "게시물 생성 메서드입니다.")
  public ResponseEntity<ResultsResponse> createBoard(
      @RequestBody BoardCreateRequest boardCreateRequest, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    boardService.createBoard(boardCreateRequest, user);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_CREATED_SUCCESS));
  }

  @PostMapping(
      value = "/video",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "비디오 생성", description = "비디오 생성 메서드입니다. videoUrl을 반환합니다")
  public ResponseEntity<ResultsResponse> uploadVideo(
      @RequestParam(value = "video", required = false) MultipartFile videoFile,
      @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    BoardVideoDto boardVideoDto = boardService.uploadVideo(videoFile);
    return ResponseEntity.ok(ResultsResponse.of(FILE_UPLOAD_SUCCESS, boardVideoDto));
  }

  @PostMapping(
      value = "/{division}",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "이미지 업로드", description = "이미지를 S3에 업로드 후, image URL 을 반환합니다")
  public ResponseEntity<ResultsResponse> uploadThumbnail(
      @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
      @PathVariable(value = "division") UploadDivision uploadDivision,
      @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    BoardThumbnailDto boardThumbnailDto = boardService.createImageUrl(imageFile, uploadDivision);

    return ResponseEntity.ok(ResultsResponse.of(FILE_UPLOAD_SUCCESS, boardThumbnailDto));
  }

  @PutMapping("/update/{boardId}")
  public ResponseEntity<ResultsResponse> updateBoard(
      @PathVariable Long boardId,
      @RequestBody BoardUpdateRequest boardUpdateRequest,
      @AuthenticationPrincipal User user) {

    if (user == null) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND_ERROR);
    }
    boardService.changeBoard(boardId, boardUpdateRequest);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_UPDATED_SUCCESS));
  }

  @PatchMapping("/delete/{boardId}")
  public ResponseEntity<ResultsResponse> deleteBoard(
      @PathVariable Long boardId, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND_ERROR);
    }

    boardService.deleteBoard(boardId);

    return ResponseEntity.ok(ResultsResponse.of(BOARD_DELETE_SUCCESS));
  }
}
