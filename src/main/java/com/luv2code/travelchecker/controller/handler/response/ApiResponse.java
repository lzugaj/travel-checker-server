package com.luv2code.travelchecker.controller.handler.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime localDateTime;

    private final Integer httpStatusCode;

    private final HttpStatus httpStatus;

    private final String message;

    private final String path;

    public ApiResponse(final LocalDateTime localDateTime,
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