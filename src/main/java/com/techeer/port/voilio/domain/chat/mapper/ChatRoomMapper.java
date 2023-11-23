package com.techeer.port.voilio.domain.chat.mapper;

import com.techeer.port.voilio.domain.chat.dto.ChatRoomDto;
import com.techeer.port.voilio.domain.chat.dto.request.CreateChatRoomRequest;
import com.techeer.port.voilio.domain.chat.entity.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);

    ChatRoom toEntity(CreateChatRoomRequest request);

    List<ChatRoomDto> toDtos(List<ChatRoom> chatRooms);
}
