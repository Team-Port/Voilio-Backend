package com.techeer.port.voilio.domain.board.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.dto.response.UploadFileResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NoAuthority;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.Pagination;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import com.techeer.port.voilio.global.result.ResultResponse;
import com.techeer.port.voilio.s3.util.S3Manager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Board", description = "Board API Document")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
@Slf4j
public class BoardController {

  private final BoardService boardService;
  private final BoardMapper boardMapper;
  private final S3Manager s3Manager;
  private final JwtProvider jwtProvider;
  private final UserService userService;

  @GetMapping("/{board_id}")
  @Operation(summary = "개별 게시물 출력", description = "개별 게시물 출력 메서드입니다.")
  public ResponseEntity<EntityModel<ResultResponse<Board>>> findBoardById(
      @PathVariable Long board_id,
      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
          String authorizationHeader) {

    Long currentLoginUserId = userService.getCurrentLoginUser(authorizationHeader);
    boolean isAuthenticated =
        !authorizationHeader.isEmpty()
            && currentLoginUserId.equals(userService.getUserIdByBoardId(board_id));

    BoardResponse board;
    if (isAuthenticated) {
      board = boardService.findBoardByIdExceptHide(board_id);
      board.setAuth(isAuthenticated);
    } else {
      board = boardService.findBoardById(board_id);
    }
    ResultResponse<Board> responseFormat = new ResultResponse<>(BOARD_FIND_SUCCESS, board);
    Link selfLink =
        linkTo(methodOn(BoardController.class).findBoardById(board_id, authorizationHeader))
            .withSelfRel();

    return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(responseFormat, selfLink));
  }

  @PutMapping("/update/{boardId}")
  @Operation(summary = "게시물 수정", description = "게시물 수정 메서드입니다.")
  public ResponseEntity<ResultResponse> updateBoard(
      @PathVariable Long boardId,
      @RequestBody BoardUpdateRequest boardUpdateRequest,
      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
          String authorizationHeader) {
    Long currentLoginUserId = userService.getCurrentLoginUser(authorizationHeader);
    boolean isAuthenticated =
        !authorizationHeader.isEmpty()
            && currentLoginUserId.equals(userService.getUserIdByBoardId(boardId));

    if (isAuthenticated) {
      boardService.updateBoard(boardId, boardUpdateRequest);
      ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_UPDATED_SUCCESS);
      resultResponse.add(
          linkTo(
                  methodOn(BoardController.class)
                      .updateBoard(boardId, boardUpdateRequest, authorizationHeader))
              .withSelfRel());
      return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    } else {
      throw new NoAuthority();
    }
  }

  @DeleteMapping("/{boardId}")
  @Operation(summary = "게시물 삭제", description = "게시물 삭제 메서드입니다.")
  public ResponseEntity<ResultResponse> deleteBoard(@PathVariable Long boardId) {
    boardService.deleteBoard(boardId);
    ResultResponse<?> responseFormat = new ResultResponse<>(USER_REGISTRATION_SUCCESS);
    responseFormat.add(linkTo(methodOn(BoardController.class).deleteBoard(boardId)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(responseFormat);
  }

  @GetMapping
  @Operation(summary = "키워드가 있는 게시물 출력", description = "키워드를 통한 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultResponse<List<EntityModel<BoardResponse>>>> findBoardByKeyword(
      @RequestParam("search") String search,
      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
          String authorizationHeader) {
    List<EntityModel<BoardResponse>> boards =
        boardService.findBoardByKeyword(search).stream()
            .map(
                board ->
                    EntityModel.of(
                        board,
                        linkTo(
                                methodOn(BoardController.class)
                                    .findBoardById(board.getId(), authorizationHeader))
                            .withSelfRel()))
            .collect(Collectors.toList());
    ResultResponse<List<EntityModel<BoardResponse>>> resultResponse =
        new ResultResponse<>(BOARD_FIND_SUCCESS, boards);
    resultResponse.add(
        linkTo(methodOn(BoardController.class).findBoardByKeyword(search, authorizationHeader))
            .withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PatchMapping("{boardId}/hide")
  @Operation(summary = "게시물 숨김", description = "게시물 숨기기 메서드입니다.")
  public ResponseEntity<ResultResponse> hideBoard(@PathVariable Long boardId) {
    boardService.hideBoard(boardId);
    ResultResponse<?> resultResponse = new ResultResponse<>(BOARD_UPDATED_SUCCESS);
    resultResponse.add(linkTo(methodOn(BoardController.class).hideBoard(boardId)).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping(value = "/create", consumes = "multipart/form-data")
  @Operation(summary = "게시물 생성", description = "게시물 생성 메서드입니다.")
  public ResponseEntity<ResultResponse> createBoard(
      @RequestParam(value = "user_id") Long user_id,
      @RequestParam(value = "title") String title,
      @RequestParam(value = "content") String content,
      @RequestParam(value = "category1") Category category1,
      @RequestParam(value = "category2") Category category2,
      @RequestParam(value = "video") MultipartFile videoFile,
      @RequestParam(value = "thumbnail") MultipartFile thumbnailFile) {
    UploadFileResponse uploadFile = boardService.uploadFiles(videoFile, thumbnailFile);
    BoardCreateRequest boardCreateRequest =
        BoardCreateRequest.builder()
            .user_id(user_id)
            .title(title)
            .content(content)
            .category1(category1)
            .category2(category2)
            .video_url(uploadFile.getVideo_url())
            .thumbnail_url(uploadFile.getThumbnail_url())
            .build();
    boardService.createBoard(boardCreateRequest);
    ResultResponse<Board> resultResponse = new ResultResponse<>(BOARD_CREATED_SUCCESS);
    resultResponse.add(
        linkTo(
                methodOn(BoardController.class)
                    .createBoard(
                        user_id, title, content, category1, category2, videoFile, thumbnailFile))
            .withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/lists")
  @Operation(summary = "전체 게시물 출력", description = "전체 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> findAllBoard(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "30") int size,
      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
          String authorizationHeader) {
    Page<Board> boardPage = boardService.findAllBoard(PageRequest.of(page, size));
    List<EntityModel<BoardResponse>> boardLists =
        boardPage.getContent().stream()
            .map(
                board ->
                    EntityModel.of(
                        boardMapper.toDto(board),
                        linkTo(
                                methodOn(BoardController.class)
                                    .findBoardById(board.getId(), authorizationHeader))
                            .withSelfRel()))
            .collect(Collectors.toList());

    Pagination<EntityModel<BoardResponse>> result =
        new Pagination<>(
            boardLists,
            boardPage.getNumber(),
            boardPage.getSize(),
            boardPage.getTotalElements(),
            boardPage.getTotalPages(),
            linkTo(methodOn(BoardController.class).findAllBoard(page, size, authorizationHeader))
                .withSelfRel());

    ResultResponse<Pagination<EntityModel<BoardResponse>>> resultResponse =
        new ResultResponse<>(BOARD_FINDALL_SUCCESS, result);
    return ResponseEntity.ok().body(resultResponse);
  }

  @GetMapping("/lists/category")
  @Operation(summary = "카테고리 별 게시물 출력", description = "카테고리 별 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> findBoardByCategory(
      @RequestParam("category") String category,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "30") int size,
      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
          String authorizationHeader) {
    Category category1 = Category.valueOf(category.toUpperCase());
    Page<Board> boardPage = boardService.findBoardByCategory(category1, PageRequest.of(page, size));
    List<EntityModel<BoardResponse>> boardLists =
        boardPage.getContent().stream()
            .map(
                board ->
                    EntityModel.of(
                        boardMapper.toDto(board),
                        linkTo(
                                methodOn(BoardController.class)
                                    .findBoardById(board.getId(), authorizationHeader))
                            .withSelfRel()))
            .collect(Collectors.toList());

    Pagination<EntityModel<BoardResponse>> result =
        new Pagination<>(
            boardLists,
            boardPage.getNumber(),
            boardPage.getSize(),
            boardPage.getTotalElements(),
            boardPage.getTotalPages(),
            linkTo(
                    methodOn(BoardController.class)
                        .findBoardByCategory(category, page, size, authorizationHeader))
                .withSelfRel());

    ResultResponse<Pagination<EntityModel<BoardResponse>>> resultResponse =
        new ResultResponse<>(BOARD_FIND_SUCCESS, result);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/lists/@{nickname}")
  @Operation(summary = "유저 별 게시물 출력", description = "유저 닉네임을 통한 유저 별 게시물 출력 메서드입니다.")
  public ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> findBoardByUserId(
      @PathVariable("nickname") String nickname,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "30") int size,
      @RequestHeader(value = "Authorization", required = false, defaultValue = "")
          String authorizationHeader) {

    Long currentLoginUserNickname = userService.getCurrentLoginUser(authorizationHeader);

    boolean isAuthenticated =
        !authorizationHeader.isEmpty()
            && currentLoginUserNickname.equals(userService.getUserByNickname(nickname));

    Page<Board> boardPage =
        isAuthenticated
            ? boardService.findBoardByUserNicknameExceptHide(nickname, PageRequest.of(page, size))
            : boardService.findBoardByUserNickname(nickname, PageRequest.of(page, size));

    List<EntityModel<BoardResponse>> boardLists =
        boardPage.getContent().stream()
            .map(
                board -> {
                  Link selfLink =
                      isAuthenticated
                          ? linkTo(
                                  methodOn(BoardController.class)
                                      .findBoardById(board.getId(), nickname))
                              .withSelfRel()
                          : linkTo(
                                  methodOn(BoardController.class)
                                      .findBoardById(board.getId(), authorizationHeader))
                              .withSelfRel();
                  BoardResponse boardResponse = boardMapper.toDto(board);
                  boardResponse.setAuth(isAuthenticated);
                  return EntityModel.of(boardResponse, selfLink);
                })
            .collect(Collectors.toList());

    Pagination<EntityModel<BoardResponse>> result =
        new Pagination<>(
            boardLists,
            boardPage.getNumber(),
            boardPage.getSize(),
            boardPage.getTotalElements(),
            boardPage.getTotalPages(),
            linkTo(
                    methodOn(BoardController.class)
                        .findBoardByUserId(nickname, page, size, authorizationHeader))
                .withSelfRel());

    ResultResponse<Pagination<EntityModel<BoardResponse>>> resultResponse =
        new ResultResponse<>(BOARD_FINDALL_SUCCESS, result);

    return ResponseEntity.ok().body(resultResponse);
  }

  @PostMapping(value = "/files", consumes = "multipart/form-data")
  public ResponseEntity<EntityModel<ResultResponse<UploadFileResponse>>> uploadVideo(
      @RequestParam(value = "video", required = false) MultipartFile videoFile,
      @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile) {
    UploadFileResponse uploadFile = boardService.uploadFiles(videoFile, thumbnailFile);
    ResultResponse<UploadFileResponse> resultResponse =
        new ResultResponse<>(FILE_UPLOAD_SUCCESS, uploadFile);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            EntityModel.of(
                resultResponse,
                linkTo(methodOn(BoardController.class).uploadVideo(videoFile, thumbnailFile))
                    .withSelfRel()));
  }
}
