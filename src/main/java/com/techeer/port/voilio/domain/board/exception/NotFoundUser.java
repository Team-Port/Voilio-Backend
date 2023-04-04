package com.techeer.port.voilio.domain.board.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class NotFoundUser extends BusinessException {
  public NotFoundUser() {
    super(ErrorCode.USER_NOT_FOUND_ERROR);
  }
}
