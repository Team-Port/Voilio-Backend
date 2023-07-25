package com.techeer.port.voilio.domain.chat.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.chat.controller.ChatController;
import com.techeer.port.voilio.domain.chat.document.Chat;
import com.techeer.port.voilio.domain.chat.dto.ChatMessage;
import com.techeer.port.voilio.domain.chat.dto.response.GetChatResponse;
import com.techeer.port.voilio.global.result.ResultCode;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
  public Chat toEntity(ChatMessage request) {
    return Chat.builder()
        .roomId(request.getRoomId())
        .userId(request.getUserId())
        .message(request.getMessage())
        .build();
  }

  public GetChatResponse toDto(Chat chat) {
    return GetChatResponse.builder()
        .userId(chat.getUserId())
        .message(chat.getMessage())
        .time(chat.getCreatedAt())
        .build();
  }

  public Page<GetChatResponse> toDto(Page<Chat> chat) {
    return chat.map(this::toDto);
  }

  public EntityModel toModel(
      ResultCode code, Page<GetChatResponse> response, UUID roomUuid, int page) {
    EntityModel<ResultResponse<Object>> entity =
        EntityModel.of(
            new ResultResponse<>(code, response),
            linkTo(methodOn(ChatController.class).getChat(roomUuid, page)).withSelfRel());
    if (response.hasNext())
      entity.add(
          linkTo(methodOn(ChatController.class).getChat(roomUuid, page + 1)).withRel("next"));

    return entity;
  }
}
