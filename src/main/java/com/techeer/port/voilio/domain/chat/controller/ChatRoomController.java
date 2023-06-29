package com.techeer.port.voilio.domain.chat.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;

import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
import com.techeer.port.voilio.domain.chat.dto.response.GetChatRoomResponse;
import com.techeer.port.voilio.domain.chat.service.ChatRoomService;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/chatRooms")
public class ChatRoomController {

  private final ChatRoomService chatRoomService;
  private final UserService userService;

  @GetMapping
  public ResponseEntity<EntityModel<ResultResponse<List<GetChatRoomResponse>>>> getRoom(
      @RequestHeader(value = "Authorization") String authorizationHeader) {
    Long currentLoginUserId = userService.getCurrentLoginUser(authorizationHeader);
    List<GetChatRoomResponse> response = chatRoomService.getChatRoomAll(currentLoginUserId);
    return ResponseEntity.ok(
        EntityModel.of(new ResultResponse<>(API_SUCCESS_GET_ALL_CHATROOM, response)));
  }

  @PostMapping
  public ResponseEntity<EntityModel<ResultResponse>> createRoom(
      @Valid @RequestBody CreateChatRoomRequest request) {
    chatRoomService.createChatRoom(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(EntityModel.of(new ResultResponse<>(API_SUCCESS_CREATE_CHATROOM)));
  }

  @GetMapping("/{subscribeId}")
  public ResponseEntity<EntityModel<ResultResponse<UUID>>> getRoomBySubscribeId(
      @RequestHeader(value = "Authorization") String authorizationHeader,
      @PathVariable Long subscribeId) {
    Long currentLoginUserId = userService.getCurrentLoginUser(authorizationHeader);
    UUID roomUuid = chatRoomService.getChatRoom(currentLoginUserId, subscribeId);
    return ResponseEntity.ok(
        EntityModel.of(new ResultResponse<>(API_SUCCESS_GET_CHATROOM, roomUuid)));
  }
}
