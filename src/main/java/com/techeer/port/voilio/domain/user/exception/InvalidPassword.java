package com.techeer.port.voilio.domain.user.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class InvalidPassword extends BusinessException{

    public InvalidPassword() {
        super(ErrorCode.INVALID_USER_PASSWORD);
    }

}
