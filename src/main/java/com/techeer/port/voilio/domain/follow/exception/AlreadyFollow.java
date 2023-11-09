package com.techeer.port.voilio.domain.follow.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class AlreadyFollow extends BusinessException {
  public AlreadyFollow() {
    super(ErrorCode.ALREADY_FOLLOW_USER);
  }
}
