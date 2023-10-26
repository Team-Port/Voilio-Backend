package com.techeer.port.voilio.domain.user.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class AlreadyExistUserByNickname extends BusinessException {
  public AlreadyExistUserByNickname() {
    super(ErrorCode.ALREADY_EXIST_USER_BY_NICKNAME);
  }
}
