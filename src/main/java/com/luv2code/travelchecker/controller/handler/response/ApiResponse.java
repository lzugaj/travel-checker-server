package com.luv2code.travelchecker.controller.handler.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
public class ApiResponse {

    private final String localDateTime;

    private final Integer httpStatusCode;

    private final HttpStatus httpStatus;

    private final String message;

    private final String path;

    public ApiResponse(final String localDateTime,
                       final Integer httpStatusCode,
                       final HttpStatus httpStatus,
                       final String message,
                       final String path) {
        this.localDateTime = localDateTime;
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
    }
}