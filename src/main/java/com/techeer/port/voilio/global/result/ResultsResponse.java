package com.techeer.port.voilio.global.result;

import lombok.Getter;

@Getter
public class ResultsResponse {

  private String code;
  private String message;
  private Object data;

  public static ResultsResponse of(ResultCode resultCode, Object data) {
    return new ResultsResponse(resultCode, data);
  }

  public static ResultsResponse of(ResultCode resultCode) {
    return new ResultsResponse(resultCode, "");
  }

  public ResultsResponse(ResultCode resultCode, Object data) {
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    this.data = data;
  }
}
