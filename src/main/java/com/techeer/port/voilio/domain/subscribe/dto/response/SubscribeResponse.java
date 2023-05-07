package com.techeer.port.voilio.domain.subscribe.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubscribeResponse {
<<<<<<< Updated upstream
  private Long SubscribeId;
  private String user_nickname;
  private String follower_nickname;
  private LocalDateTime localDateTime;
=======
    private String user_nickname;
    private String follower_nickname;
>>>>>>> Stashed changes
}
