package com.techeer.port.voilio.domain.board.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class NoAuthority extends BusinessException {
  public NoAuthority() {
    super(ErrorCode.INVALID_AUTH_TOKEN);
  }
}
