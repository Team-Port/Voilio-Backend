package com.techeer.port.voilio.domain.chat.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;

import com.techeer.port.voilio.domain.chat.dto.ChatRoomDto;
import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
import com.techeer.port.voilio.domain.chat.service.ChatRoomService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChatRoom", description = "ChatRoom API Document")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chatRoom")
public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  @PostMapping("/")
  @Operation(summary = "채팅방 생성", description = "채팅방을 생성하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> createChatRoom(CreateChatRoomRequest request) {
    chatRoomService.createChatRoom(request);
    return ResponseEntity.ok(ResultsResponse.of(API_SUCCESS_CREATE_CHATROOM));
  }

  @GetMapping("/")
  @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회 메서드입니다.")
  public ResponseEntity<ResultsResponse> getChatRooms() {
    List<ChatRoomDto> chatRooms = chatRoomService.getChatRooms();
    return ResponseEntity.ok(ResultsResponse.of(API_SUCCESS_GET_ALL_CHATROOM, chatRooms));
  }

  @GetMapping("/room/{chatRoomId}")
  @Operation(summary = "채팅방 입장", description = "채팅방에 입장하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> enterRoom(
      @PathVariable(value = "chatRoomId") Long chatRoomId, @AuthenticationPrincipal User user) {
    if (user == null) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    boolean enter = chatRoomService.checkUser(chatRoomId, user);
    return ResponseEntity.ok(ResultsResponse.of(API_SUCCESS_ENTER_CHATROOM, enter));
  }

  @GetMapping("/name/{chatRoomName}")
  @Operation(summary = "채팅방 조회(이름)", description = "방 이름으로 채팅방을 조회하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> findChatRoomByName(
      @PathVariable(value = "chatRoomName") String chatRoomName) {
    ChatRoomDto chatRoomDto = chatRoomService.findChatRoomByName(chatRoomName);
    return ResponseEntity.ok(ResultsResponse.of(API_SUCCESS_GET_CHATROOM, chatRoomDto));
  }

  @GetMapping("/id/{chatRoomId}")
  @Operation(summary = "채팅방 조회(아이디)", description = "방 아이디를 통해 채팅방을 조회하는 메서드입니다.")
  public ResponseEntity<ResultsResponse> findChatRoomById(
      @PathVariable(value = "chatRoomId") Long chatRoomId) {
    ChatRoomDto chatRoomDto = chatRoomService.findChatRoomById(chatRoomId);
    return ResponseEntity.ok(ResultsResponse.of(API_SUCCESS_GET_CHATROOM, chatRoomDto));
  }
}
