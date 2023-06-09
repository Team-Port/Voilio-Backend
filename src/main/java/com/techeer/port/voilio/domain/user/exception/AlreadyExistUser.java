package com.techeer.port.voilio.domain.user.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class AlreadyExistUser extends BusinessException {
  public AlreadyExistUser() {
    super(ErrorCode.ALREADY_EXIST_USER);
  }
}
