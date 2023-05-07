package com.techeer.port.voilio.domain.subscribe.mapper;

import com.techeer.port.voilio.domain.subscribe.dto.response.SubscribeResponse;
import com.techeer.port.voilio.domain.subscribe.entity.Subscribe;
import org.springframework.stereotype.Component;

@Component
public class SubscribeMapper {

  public SubscribeResponse toDto(Subscribe subscribe) {
    return SubscribeResponse.builder()
        .user_nickname(subscribe.getUser().getNickname())
        .follower_nickname(subscribe.getSubscriber().getNickname())
        .build();
  }
}
