package com.techeer.port.voilio.domain.subscribe.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.subscribe.dto.request.CheckSubscribeRequestDto;
import com.techeer.port.voilio.domain.subscribe.dto.request.SubscribeRequest;
import com.techeer.port.voilio.domain.subscribe.dto.response.SubscribeResponse;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.mapper.SubscribeMapper;
import com.techeer.port.voilio.domain.subscribe.service.SubscribeService;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.Pagination;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscribes")
public class SubscribeController {

  private final SubscribeService subscribeService;
  private final UserService userService;

  @PostMapping("/")
  public ResponseEntity<ResultResponse> subscribe(
      @Valid @RequestBody SubscribeRequest subscribeRequest) {

    subscribeService.subscribe(subscribeRequest.getNickname(), subscribeRequest.getSubscribeId());
    ResultResponse<Subscribe> resultResponse = new ResultResponse<>(SUBSCRIBE_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(SubscribeController.class).subscribe(subscribeRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @PostMapping("/unsubscribe")
  public ResponseEntity<ResultResponse> unsubscribe(
      @Valid @RequestBody SubscribeRequest subscribeRequest) {

    subscribeService.unsubscribe(subscribeRequest.getNickname(), subscribeRequest.getSubscribeId());
    ResultResponse<Subscribe> resultResponse = new ResultResponse<>(UNSUBSCRIBE_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(SubscribeController.class).unsubscribe(subscribeRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

//  @GetMapping("/lists/@{nickname}")
//  public ResponseEntity<ResultResponse<Pagination<EntityModel<SubscribeResponse>>>> subscribeLists(
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
    resultResponse.add(
        linkTo(methodOn(SubscribeController.class).checkSubscribe(checkSubscribeRequestDto))
            .withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
