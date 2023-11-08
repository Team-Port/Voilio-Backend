package com.techeer.port.voilio.domain.user.controller;

import static com.techeer.port.voilio.global.result.ResultCode.CREATE_USER_SEARCH_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.GET_USER_SEARCH_SUCCESS;

import com.techeer.port.voilio.domain.user.dto.UserSearchDto;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserSearchService;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Search", description = "User Search API Document")
@RequestMapping("api/v1/user_search")
@RestController
@RequiredArgsConstructor
public class UserSearchController {

  private final UserSearchService userSearchService;

  @GetMapping("")
  @Operation(summary = "검색어 히스토리 조회", description = "로그인한 사용자의 검색어 히스토리 조회")
  public ResponseEntity<ResultResponse> findByUserId(@AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    List<UserSearchDto> userSearchDtoList = userSearchService.findByUserId(user);
    ResultResponse<UserSearchDto> resultResponse =
        new ResultResponse<>(GET_USER_SEARCH_SUCCESS, userSearchDtoList);
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/{search_keyword}")
  @Operation(summary = "검색어 저장", description = "로그인한 사용자의 검색어를 저장")
  public ResponseEntity<ResultResponse> create(
      @AuthenticationPrincipal User user,
      @PathVariable(value = "search_keyword") String searchKeyword) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    UserSearchDto userSearchDto = userSearchService.create(user, searchKeyword);
    ResultResponse<UserSearchDto> resultResponse =
        new ResultResponse<>(CREATE_USER_SEARCH_SUCCESS, userSearchDto);
    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
