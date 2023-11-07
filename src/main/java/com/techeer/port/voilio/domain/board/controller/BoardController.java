package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.dto.BoardThumbnailDto;
import com.techeer.port.voilio.domain.board.dto.BoardVideoDto;
import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.common.LikeDivision;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultResponse;
import com.techeer.port.voilio.global.result.ResultsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

  @GetMapping("/{boardId}")
  @Operation(summary = "개별 게시물 출력", description = "개별 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findBoardById(
      @RequestParam LikeDivision likeDivision,
      @RequestParam Long contentId,
      @PathVariable Long boardId,
      @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    BoardDto boardDto = boardService.findBoardById(boardId, user, likeDivision, contentId);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_UPDATED_SUCCESS, boardDto));
  }

  @PatchMapping("/{boardId}/view")
  @Operation(summary = "조회수 수정", description = "조회수 증가하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> addBoardViewCounts(
      @PathVariable Long boardId, @AuthenticationPrincipal User user) {

    boardService.addBoardView(boardId, user);

    return ResponseEntity.ok(ResultsResponse.of(BOARD_UPDATED_SUCCESS));
  }

  @GetMapping("/{userId}/result")
  @Operation(summary = "유저별 게시물 출력", description = "유저아이디로 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findBoardByUserId(
      @ParameterObject @PageableDefault(size = 20) Pageable pageable,
      @RequestParam LikeDivision likeDivision,
      @RequestParam Long contentId,
      @RequestParam Long userId,
      @AuthenticationPrincipal User user) {

    Page<BoardDto> allBoard =
        boardService.findBoardByUser(user, userId, likeDivision, contentId, pageable);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, allBoard));
  }

  //
  //  @PutMapping(value = "/update/{boardId}", consumes = "multipart/form-data")
  //  @Operation(summary = "게시물 수정", description = "게시물 수정 메서드입니다.")
  //  public ResponseEntity<ResultResponse> updateBoard(
  //      @PathVariable Long boardId,
  //      @RequestParam String title,
  //      @RequestParam String content,
  //      @RequestParam Category category1,
  //      @RequestParam Category category2,
  //      @RequestParam MultipartFile thumbnailFile,
  //      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
  //          String authorizationHeader) {
  //    Long currentLoginUserId = userService.getCurrentLoginUser(authorizationHeader);
  //    boolean isAuthenticated =
  //        !authorizationHeader.isEmpty()
  //            && currentLoginUserId.equals(userService.getUserIdByBoardId(boardId));
  //
  //    if (isAuthenticated) {
  //      UploadFileResponse updateFiles = boardService.updateFiles(thumbnailFile);
  //      BoardUpdateRequest boardUpdateRequest =
  //          BoardUpdateRequest.builder()
  //              .title(title)
  //              .content(content)
  //              .category1(category1)
  //              .category2(category2)
  //              .thumbnail_url(updateFiles.getThumbnailUrl())
  //              .build();
  //      boardService.updateBoard(boardId, boardUpdateRequest);
  //      ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_UPDATED_SUCCESS);
  //      resultResponse.add(
  //          linkTo(
  //                  methodOn(BoardController.class)
  //                      .updateBoard(
  //                          boardId,
  //                          title,
  //                          content,
  //                          category1,
  //                          category2,
  //                          thumbnailFile,
  //                          authorizationHeader))
  //              .withSelfRel());
  //      return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  //    } else {
  //      throw new NoAuthority();
  //    }
  //  }
  //
  //  @DeleteMapping("/{boardId}")
  //  @Operation(summary = "게시물 삭제", description = "게시물 삭제 메서드입니다.")
  //  public ResponseEntity<ResultResponse> deleteBoard(@PathVariable Long boardId) {
  //    boardService.deleteBoard(boardId);
  //    ResultResponse<?> responseFormat = new ResultResponse<>(USER_REGISTRATION_SUCCESS);
  //
  // responseFormat.add(linkTo(methodOn(BoardController.class).deleteBoard(boardId)).withSelfRel());
  //    return ResponseEntity.status(HttpStatus.OK).body(responseFormat);
  //  }

  //  @GetMapping
  //  @Operation(summary = "키워드가 있는 게시물 출력", description = "키워드를 통한 게시물 출력 메서드입니다.")
  //  public ResponseEntity<ResultResponse<List<EntityModel<BoardResponse>>>> findBoardByKeyword(
  //      @RequestParam("search") String search,
  //      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
  //          String authorizationHeader) {
  //    List<EntityModel<BoardResponse>> boards =
  //        boardService.findBoardByKeyword(search).stream()
  //            .map(
  //                board ->
  //                    EntityModel.of(
  //                        board,
  //                        linkTo(
  //                                methodOn(BoardController.class)
  //                                    .findBoardById(board.getId(), authorizationHeader))
  //                            .withSelfRel()))
  //            .collect(Collectors.toList());
  //    ResultResponse<List<EntityModel<BoardResponse>>> resultResponse =
  //        new ResultResponse<>(BOARD_FIND_SUCCESS, boards);
  //    resultResponse.add(
  //        linkTo(methodOn(BoardController.class).findBoardByKeyword(search, authorizationHeader))
  //            .withSelfRel());
  //    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  //  }

  @PatchMapping("{boardId}/hide")
  @Operation(summary = "게시물 숨김", description = "게시물 숨기기 메서드입니다.")
  public ResponseEntity<ResultResponse> hideBoard(@PathVariable Long boardId) {
    boardService.hideBoard(boardId);
    ResultResponse<?> resultResponse = new ResultResponse<>(BOARD_UPDATED_SUCCESS);
    resultResponse.add(linkTo(methodOn(BoardController.class).hideBoard(boardId)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping(value = "/create")
  @Operation(summary = "게시물 생성", description = "게시물 생성 메서드입니다.")
  public ResponseEntity<ResultsResponse> createBoard(
      @RequestBody @Valid BoardCreateRequest boardCreateRequest,
      @AuthenticationPrincipal User user) {
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
      value = "/thumbnail",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "썸네일 생성", description = "썸네일 생성 메서드입니다. thumbnailUrl을 반환합니다")
  public ResponseEntity<ResultsResponse> uploadThumbnail(
      @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile,
      @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    BoardThumbnailDto boardThumbnailDto = boardService.uploadThumbnail(thumbnailFile);

    return ResponseEntity.ok(ResultsResponse.of(FILE_UPLOAD_SUCCESS, boardThumbnailDto));
  }

  @GetMapping("/lists")
  @Operation(summary = "전체 게시물 출력", description = "전체 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultsResponse> findsAllBoard(
      @RequestParam LikeDivision likeDivision,
      @RequestParam Long contentId,
      @ParameterObject @PageableDefault(size = 20) Pageable pageable) {

    Page<BoardDto> allBoard = boardService.findAllBoard(pageable, likeDivision, contentId);

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
}
