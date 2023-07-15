package com.techeer.port.voilio.domain.board.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class FailConvertFile extends BusinessException {
  public FailConvertFile() {
    super(ErrorCode.CONVERT_FILE_ERROR);
  }
}
