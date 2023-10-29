package com.techeer.port.voilio.domain.subscribe.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.subscribe.dto.SubscribeSimpleDto;
import com.techeer.port.voilio.domain.subscribe.dto.request.CheckSubscribeRequestDto;
import com.techeer.port.voilio.domain.subscribe.dto.request.SubscribeRequest;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.service.SubscribeService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultResponse;
import com.techeer.port.voilio.global.result.ResultsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Subscribe", description = "Subscribe API Document")
@RequestMapping("/api/v1/subscribes")
public class SubscribeController {

  private final SubscribeService subscribeService;
  private final UserService userService;

  @PostMapping("/")
  @Operation(summary = "구독", description = "특정 회원을 구독하는 메서드입니다.")
  public ResponseEntity<ResultResponse> subscribe(
      @Valid @RequestBody SubscribeRequest subscribeRequest, @AuthenticationPrincipal User user) {

    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    subscribeService.subscribe(subscribeRequest.getNickname(), subscribeRequest.getSubscribeId());
    ResultResponse<Subscribe> resultResponse = new ResultResponse<>(SUBSCRIBE_SUCCESS);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping("/unsubscribe")
  @Operation(summary = "구독 해지", description = "특정 회원을 구독을 해지하는 메서드입니다.")
  public ResponseEntity<ResultResponse> unsubscribe(
      @Valid @RequestBody SubscribeRequest subscribeRequest, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    subscribeService.unsubscribe(subscribeRequest.getNickname(), subscribeRequest.getSubscribeId());
    ResultResponse<Subscribe> resultResponse = new ResultResponse<>(UNSUBSCRIBE_SUCCESS);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  //  @GetMapping("/lists/@{nickname}")
  //  public ResponseEntity<ResultResponse<Pagination<EntityModel<SubscribeResponse>>>>
  // subscribeLists(
  //      @PathVariable("nickname") String nickname,
  //      @RequestParam(defaultValue = "0") int page,
  //      @RequestParam(defaultValue = "30") int size) {
  //    Page<Subscribe> followers =
  //        subscribeService.findSubscribesByNickname(nickname, PageRequest.of(page, size));
  //    List<EntityModel<SubscribeResponse>> followerLists =
  //        followers.getContent().stream()
  //            .map(
  //                subscribe ->
  //                    EntityModel.of(
  //                        subscribeMapper.toDto(subscribe),
  //                        linkTo(
  //                                methodOn(SubscribeController.class)
  //                                    .subscribeLists(nickname, page, size))
  //                            .withSelfRel()))
  //            .collect(Collectors.toList());

  //    Pagination<EntityModel<SubscribeResponse>> result =
  //        new Pagination<>(
  //            followerLists,
  //            followers.getNumber(),
  //            followers.getSize(),
  //            followers.getTotalElements(),
  //            followers.getTotalPages(),
  //            linkTo(methodOn(SubscribeController.class).subscribeLists(nickname, page, size))
  //                .withSelfRel());
  //
  //    ResultResponse<Pagination<EntityModel<SubscribeResponse>>> resultResponse =
  //        new ResultResponse<>(SUBSCRIBE_FINDALL_SUCCESS, result);
  //    return ResponseEntity.ok().body(resultResponse);
  //  }

  @PostMapping("/check")
  public ResponseEntity<ResultResponse> checkSubscribe(
      @Valid @RequestBody CheckSubscribeRequestDto checkSubscribeRequestDto) {
    Boolean check =
        subscribeService.checkSubscribe(
            checkSubscribeRequestDto.getNickname(), checkSubscribeRequestDto.getSubscribeId());
    ResultResponse<Boolean> resultResponse = new ResultResponse<>(GET_USER_SUCCESS, check);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/list")
  @Operation(summary = "구독 회원 목록", description = "구독한 회원의 목록을 출력하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> getSubscribeList(
      @RequestParam Long fromUserid, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    List<SubscribeSimpleDto> subscribeList = subscribeService.getSubscribeUserList(fromUserid);

    return ResponseEntity.ok(ResultsResponse.of(SUBSCRIBE_FINDALL_SUCCESS, subscribeList));
  }

  @GetMapping("/list/board")
  @Operation(summary = "구독 회원 게시글 목록", description = "구독한 회원의 게시글 목록을 출력하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> getSubscribeBoardList(@AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    List<BoardDto> boardDtoList = subscribeService.getSubscribeUserBoardList(user);
    return ResponseEntity.ok(ResultsResponse.of(SUBSCRIBE_BOARD_FINDALL_SUCCESS, boardDtoList));
  }
}
