package com.techeer.port.voilio.domain.chat.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

import static com.techeer.port.voilio.global.error.ErrorCode.API_ERROR_CHATROOM_NOT_FOUND;

public class NotFoundChatRoom extends BusinessException {
    public NotFoundChatRoom() {
        super(API_ERROR_CHATROOM_NOT_FOUND);
    }
}
