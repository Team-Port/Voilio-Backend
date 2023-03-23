package com.techeer.port.voilio.global.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ResultResponse<T> extends RepresentationModel<ResultResponse<T>> {

  private final String status;
  private final String message;
  private T data;

  @JsonCreator
  public ResultResponse(ResultCode resultCode) {
    this.status = resultCode.getStatus();
    this.message = resultCode.getMessage();
  }

  @JsonCreator
  public ResultResponse(ResultCode resultCode, T data) {
    this.status = resultCode.getStatus();
    this.message = resultCode.getMessage();
    this.data = data;
  }
}
