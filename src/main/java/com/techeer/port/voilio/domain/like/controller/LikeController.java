package com.techeer.port.voilio.domain.like.controller;

import com.techeer.port.voilio.domain.like.dto.LikeCreateRequestDto;
import com.techeer.port.voilio.domain.like.dto.LikeDto;
import com.techeer.port.voilio.domain.like.likeService.LikeService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.LikeDivision;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.techeer.port.voilio.global.result.ResultCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/likes")
@Tag(name = "Like", description = "Like API Document")
public class LikeController {

  private final LikeService likeService;

  @GetMapping("/{division}")
  @Operation(summary = "좋아요/보관한 게시물 목록 반환", description = "로그인 필요")
  public ResponseEntity<ResultsResponse> findByUserId(
      @PathVariable(value = "division") LikeDivision division,
      @AuthenticationPrincipal User user,
      @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    Page<LikeDto> likeDtoPage = likeService.findByUserId(division, user, pageable);
    return ResponseEntity.ok(ResultsResponse.of(BOARD_FIND_SUCCESS, likeDtoPage));
  }

    @PostMapping("/")
    @Operation(summary = "게시글 좋아요 / 게시글 보관 / 댓글 좋아요 및 좋아요 취소", description = "로그인 필요")
    public ResponseEntity<ResultsResponse> createLike(
            @RequestBody LikeCreateRequestDto likeCreateRequestDto,
            @AuthenticationPrincipal User user){
        if (user == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        LikeDto likeDto = likeService.manageLike(user, likeCreateRequestDto);
        if(likeDto.getFlag().equals("like")){
            return ResponseEntity.ok(ResultsResponse.of(LIKE_CREATED_SUCCESS, likeDto));
        } else {
            return ResponseEntity.ok(ResultsResponse.of(LIKE_DELETED_SUCCESS, likeDto));
        }
    }
}
