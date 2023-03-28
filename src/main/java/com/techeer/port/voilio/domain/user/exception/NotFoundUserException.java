package com.techeer.port.voilio.domain.user.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
  public NotFoundUserException() {
    super(ErrorCode.USER_NOT_FOUND_ERROR);
  }
}
