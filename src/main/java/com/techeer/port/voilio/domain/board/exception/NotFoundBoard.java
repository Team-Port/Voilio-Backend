package com.techeer.port.voilio.domain.board.exception;

import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;

public class NotFoundBoard extends BusinessException {
  public NotFoundBoard() {
    super(ErrorCode.BOARD_NOT_FOUND_ERROR);
  }
}
