package com.techeer.port.voilio.domain.comment.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class NotFoundCommentException extends BusinessException {
  public NotFoundCommentException() {
    super(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION);
  }
}
