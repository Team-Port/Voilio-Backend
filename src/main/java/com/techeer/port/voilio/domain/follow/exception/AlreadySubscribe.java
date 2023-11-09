package com.techeer.port.voilio.domain.follow.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class AlreadySubscribe extends BusinessException {
  public AlreadySubscribe() {
    super(ErrorCode.ALREADY_SUBSCRIBE_USER);
  }
}
