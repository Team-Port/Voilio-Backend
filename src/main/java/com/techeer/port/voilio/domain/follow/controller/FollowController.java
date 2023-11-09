package com.techeer.port.voilio.domain.follow.controller;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.follow.dto.FollowSimpleDto;
import com.techeer.port.voilio.domain.follow.dto.request.CheckFollowRequestDto;
import com.techeer.port.voilio.domain.follow.dto.request.FollowRequest;
import com.techeer.port.voilio.domain.follow.entity.Follow;
import com.techeer.port.voilio.domain.follow.service.FollowService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultResponse;
import com.techeer.port.voilio.global.result.ResultsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.techeer.port.voilio.global.result.ResultCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Follow", description = "Follow API Document")
@RequestMapping("/api/v1/Follows")
public class FollowController {

  private final FollowService followService;
  private final UserService userService;

  @PostMapping("/")
  @Operation(summary = "팔로우", description = "fromUser가 toUser를 id를 통해 팔로우하는 메서드입니다.")
  public ResponseEntity<ResultResponse> follow(
          @Valid @RequestBody FollowRequest FollowRequest, @AuthenticationPrincipal User user) {

    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    followService.follow(FollowRequest.getFromUserId(), FollowRequest.getToUserId());
    ResultResponse<Follow> resultResponse = new ResultResponse<>(FOLLOW_SUCCESS);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping("/unFollow")
  @Operation(summary = "팔로우 취소", description = "fromUser가 toUser를 id를 통해 팔로우를 취소하는 메서드입니다.")
  public ResponseEntity<ResultResponse> unFollow(
      @Valid @RequestBody FollowRequest FollowRequest, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    followService.unFollow(FollowRequest.getFromUserId(), FollowRequest.getToUserId());
    ResultResponse<Follow> resultResponse = new ResultResponse<>(UNFOLLOW_SUCCESS);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  //  @GetMapping("/lists/@{nickname}")
  //  public ResponseEntity<ResultResponse<Pagination<EntityModel<FollowResponse>>>>
  // FollowLists(
  //      @PathVariable("nickname") String nickname,
  //      @RequestParam(defaultValue = "0") int page,
  //      @RequestParam(defaultValue = "30") int size) {
  //    Page<Follow> followers =
  //        followService.findFollowsByNickname(nickname, PageRequest.of(page, size));
  //    List<EntityModel<FollowResponse>> followerLists =
  //        followers.getContent().stream()
  //            .map(
  //                Follow ->
  //                    EntityModel.of(
  //                        FollowMapper.toDto(Follow),
  //                        linkTo(
  //                                methodOn(FollowController.class)
  //                                    .FollowLists(nickname, page, size))
  //                            .withSelfRel()))
  //            .collect(Collectors.toList());

  //    Pagination<EntityModel<FollowResponse>> result =
  //        new Pagination<>(
  //            followerLists,
  //            followers.getNumber(),
  //            followers.getSize(),
  //            followers.getTotalElements(),
  //            followers.getTotalPages(),
  //            linkTo(methodOn(FollowController.class).FollowLists(nickname, page, size))
  //                .withSelfRel());
  //
  //    ResultResponse<Pagination<EntityModel<FollowResponse>>> resultResponse =
  //        new ResultResponse<>(FOLLOW_FINDALL_SUCCESS, result);
  //    return ResponseEntity.ok().body(resultResponse);
  //  }

  @PostMapping("/check")
  @Operation(summary = "팔로우 체크", description = "fromUser의 toUser 팔로우여부를 확인하는 메서드입니다.")
  public ResponseEntity<ResultResponse> checkFollow(
      @Valid @RequestBody CheckFollowRequestDto checkFollowRequestDto) {
    Boolean check =
        followService.checkFollow(
            checkFollowRequestDto.getFromUserId(), checkFollowRequestDto.getToUserId());
    ResultResponse<Boolean> resultResponse = new ResultResponse<>(FOLLOW_CHECK_SUCCESS, check);

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/list")
  @Operation(summary = "구독 회원 목록", description = "구독한 회원의 목록을 출력하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> getFollowList(
      @RequestParam Long fromUserid, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    List<FollowSimpleDto> FollowList = followService.getFollowUserList(fromUserid);

    return ResponseEntity.ok(ResultsResponse.of(FOLLOW_FINDALL_SUCCESS, FollowList));
  }

  @GetMapping("/list/board")
  @Operation(summary = "구독 회원 게시글 목록", description = "구독한 회원의 게시글 목록을 출력하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> getFollowBoardList(@AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    List<BoardDto> boardDtoList = followService.getFollowUserBoardList(user);
    return ResponseEntity.ok(ResultsResponse.of(FOLLOW_BOARD_FINDALL_SUCCESS, boardDtoList));
  }
}
