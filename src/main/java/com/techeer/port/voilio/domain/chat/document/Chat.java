package com.techeer.port.voilio.domain.chat.document;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Document(collection = "chat")
public class Chat {
  @Id private String id;

  @Field private UUID roomId;

  @Field private Long userId;

  @Field private String message;

  @Field @CreatedDate private LocalDateTime createdAt;
}
