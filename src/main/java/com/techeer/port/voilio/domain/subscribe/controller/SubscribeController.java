package com.techeer.port.voilio.domain.subscribe.controller;

import static com.techeer.port.voilio.global.result.ResultCode.BOARD_FINDALL_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.SUBSCRIBE_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.UNSUBSCRIBE_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.subscribe.dto.request.SubscribeRequest;
import com.techeer.port.voilio.domain.subscribe.dto.response.SubscribeResponse;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import com.techeer.port.voilio.domain.subscribe.mapper.SubscribeMapper;
import com.techeer.port.voilio.domain.subscribe.service.SubscribeService;
import com.techeer.port.voilio.global.common.Pagination;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscribe")
public class SubscribeController {
  @Autowired private SubscribeService subscribeService;
  @Autowired private SubscribeMapper subscribeMapper;

  @PostMapping("/follow")
  public ResponseEntity<ResultResponse> follow(
      @Valid @RequestBody SubscribeRequest subscribeRequest) {

    subscribeService.follow(subscribeRequest.getUserId(), subscribeRequest.getFollowerId());
    ResultResponse<Subscribe> resultResponse = new ResultResponse<>(SUBSCRIBE_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(SubscribeController.class).follow(subscribeRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @DeleteMapping("/unfollow")
  public ResponseEntity<ResultResponse> unfollow(
      @Valid @RequestBody SubscribeRequest subscribeRequest) {

    subscribeService.delete(subscribeRequest.getUserId(), subscribeRequest.getFollowerId());
    ResultResponse<Subscribe> resultResponse = new ResultResponse<>(UNSUBSCRIBE_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(SubscribeController.class).unfollow(subscribeRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @GetMapping("/lists/@{nickname}")
  public ResponseEntity<ResultResponse<Pagination<EntityModel<SubscribeResponse>>>> followerlists(
      @PathVariable("nickname") String nickname,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "30") int size) {
    Page<Subscribe> followers =
        subscribeService.findFollowersByNickname(nickname, PageRequest.of(page, size));
    List<EntityModel<SubscribeResponse>> followerLists =
        followers.getContent().stream()
            .map(
                subscribe ->
                    EntityModel.of(
                        subscribeMapper.toDto(subscribe),
                        linkTo(
                                methodOn(SubscribeController.class)
                                    .followerlists(nickname, page, size))
                            .withSelfRel()))
            .collect(Collectors.toList());

    Pagination<EntityModel<SubscribeResponse>> result =
        new Pagination<>(
            followerLists,
            followers.getNumber(),
            followers.getSize(),
            followers.getTotalElements(),
            followers.getTotalPages(),
            linkTo(methodOn(SubscribeController.class).followerlists(nickname, page, size))
                .withSelfRel());

    ResultResponse<Pagination<EntityModel<SubscribeResponse>>> resultResponse =
        new ResultResponse<>(BOARD_FINDALL_SUCCESS, result);
    return ResponseEntity.ok().body(resultResponse);
  }
}
