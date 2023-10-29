package com.techeer.port.voilio.domain.like.controller;

import static com.techeer.port.voilio.global.result.ResultCode.BOARD_FIND_SUCCESS;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/likes")
@Tag(name = "Like", description = "Like API Document")
public class LikeController {

  private final LikeService likeService;

  @GetMapping("/{division}")
  @Operation(summary = "로그인한 유저가 좋아요/보관한 게시물 목록")
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
}
