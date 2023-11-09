package com.techeer.port.voilio.domain.follow.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class AlreadyUnsubscribe extends BusinessException {
  public AlreadyUnsubscribe() {
    super(ErrorCode.ALREADY_UNSUBSCRIBE_USER);
  }
}
