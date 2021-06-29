package com.luv2code.travelchecker.controller.handler;

import com.luv2code.travelchecker.controller.handler.response.ApiResponse;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundRequestException(final EntityNotFoundException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return createResponseMessage(notFound, exception, httpServletRequest);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistsException(final EntityAlreadyExistsException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus alreadyExists = HttpStatus.BAD_REQUEST;
        return createResponseMessage(alreadyExists, exception, httpServletRequest);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(final RuntimeException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<ApiResponse> handleUnauthorizedException(final RuntimeException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus unauthorizedRequest = HttpStatus.UNAUTHORIZED;
        return createResponseMessage(unauthorizedRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity<ApiResponse> handleInternalServerException(final RuntimeException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus internalServerRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        return createResponseMessage(internalServerRequest, exception, httpServletRequest);
    }

    private ResponseEntity<ApiResponse> createResponseMessage(final HttpStatus httpStatus, final Exception exception, final HttpServletRequest httpServletRequest) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        final ApiResponse apiResponse = ApiResponse.builder()
                .localDateTime(now.format(formatter))
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}