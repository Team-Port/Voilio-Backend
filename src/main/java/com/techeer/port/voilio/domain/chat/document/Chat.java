package com.techeer.port.voilio.domain.chat.document;

import com.techeer.port.voilio.domain.chat.dto.ChatMessage;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Document(collection = "chat")
public class Chat {
    @Id
    private String id;

    @Field
    private UUID roomId;

    @Field private Long userId;

    @Field private String message;

    @Field @CreatedDate
    private LocalDateTime createdAt;
}
