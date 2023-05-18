package com.techeer.port.voilio.domain.chat.controller;

import com.techeer.port.voilio.domain.chat.model.ChatRoom;
import com.techeer.port.voilio.domain.chat.repo.ChatRoomRepository;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

  private final ChatRoomRepository chatRoomRepository;
  private final JwtProvider jwtProvider;

  @GetMapping("/room")
  public String rooms(Model model) {
    return "/chat/room";
  }

  @GetMapping("/rooms")
  @ResponseBody
  public List<ChatRoom> room() {
    return chatRoomRepository.findAllRoom();
  }

  //  @PostMapping("/room")
  //  @ResponseBody
  //  public ChatRoom createRoom(@RequestParam String name) {
  //    return chatRoomRepository.createChatRoom(name);
  //  }

  @PostMapping("/room")
  @ResponseBody
  public ChatRoom createRoom(@RequestParam String name, HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new RuntimeException("Invalid token");
    }
    String accessToken = authorizationHeader.substring(7);

    if (!jwtProvider.validateToken(accessToken)) {
      throw new RuntimeException("유효하지 않은 토큰입니다.");
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return chatRoomRepository.createChatRoom(name);
  }

  //  @GetMapping("/room/enter/{roomId}")
  //  public String roomDetail(Model model, @PathVariable String roomId, HttpServletRequest request)
  // {
  //    String authorizationHeader = request.getHeader("Authorization");
  //
  //    if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
  //      throw new RuntimeException("Invalid token");
  //    }
  //    String accessToken = authorizationHeader.substring(7);
  //
  //    if(!jwtProvider.validateToken(accessToken)) {
  //      throw new RuntimeException("유효하지 않은 토큰입니다.");
  //    }
  //
  //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
  //
  //    model.addAttribute("roomId", roomId);
  //    return "/chat/roomdetail";
  //  }
  @GetMapping("/room/enter/{roomId}")
  public String roomDetail(Model model, @PathVariable String roomId, HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new RuntimeException("유효하지 않은 토큰입니다.");
    }
    String accessToken = authorizationHeader.substring(7);

    if (!jwtProvider.validateToken(accessToken)) {
      throw new RuntimeException("유효하지 않은 토큰입니다.");
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    model.addAttribute("roomId", roomId);
    model.addAttribute("accessToken", accessToken); // 추가: 토큰 전달

    return "/chat/roomdetail";
  }

  @GetMapping("/room/{roomId}")
  @ResponseBody
  public ChatRoom roomInfo(@PathVariable String roomId) {
    return chatRoomRepository.findRoomById(roomId);
  }
}
