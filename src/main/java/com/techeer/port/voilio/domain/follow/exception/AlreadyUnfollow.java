package com.techeer.port.voilio.domain.follow.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class AlreadyUnfollow extends BusinessException {
  public AlreadyUnfollow() {
    super(ErrorCode.ALREADY_UNFOLLOW_USER);
  }
}
