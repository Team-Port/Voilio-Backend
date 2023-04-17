package com.techeer.port.voilio.domain.user.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class InvaildUserInfo extends BusinessException {
    public InvaildUserInfo() {
        super(ErrorCode.INVAILD_USER_INFO);
    }
}
