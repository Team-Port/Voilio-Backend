package com.techeer.port.voilio.global.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

@Getter
public class ResponseFormat<T> extends RepresentationModel<ResponseFormat<T>> {

    private final int status;

    private final String message;

    private T result;

    @JsonCreator
    public ResponseFormat(ResponseStatus status) {
        this.status = status.getStatus();
        this.message = status.getMessage();
    }

    @JsonCreator
    public ResponseFormat(ResponseStatus status, T result){
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.result = result;
    }
}
