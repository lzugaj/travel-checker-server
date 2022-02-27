package com.luv2code.travelchecker.controller.handler;

import com.luv2code.travelchecker.controller.handler.response.ApiResponse;
import com.luv2code.travelchecker.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            RuntimeException.class,
            NullPointerException.class
    })
    public ResponseEntity<ApiResponse> handleRuntimeException(final RuntimeException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = UserNotAuthenticatedException.class)
    public ResponseEntity<ApiResponse> handleUserNotAuthenticatedException(final UserNotAuthenticatedException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(final ConstraintViolationException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<ApiResponse> handleUnauthorizedException(final HttpClientErrorException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        return createResponseMessage(unauthorized, exception, httpServletRequest);
    }

    @ExceptionHandler(value = ResetPasswordTokenHasExpiredException.class)
    public ResponseEntity<ApiResponse> handleResetPasswordTokenHasExpiredException(final ResetPasswordTokenHasExpiredException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus forbidden = HttpStatus.FORBIDDEN;
        return createResponseMessage(forbidden, exception, httpServletRequest);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundRequestException(final EntityNotFoundException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return createResponseMessage(notFound, exception, httpServletRequest);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistsException(final EntityAlreadyExistsException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus conflict = HttpStatus.CONFLICT;
        return createResponseMessage(conflict, exception, httpServletRequest);
    }

    @ExceptionHandler(value = PasswordNotEnteredRightException.class)
    public ResponseEntity<ApiResponse> handlePasswordNotEnteredRightException(final PasswordNotEnteredRightException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus conflict = HttpStatus.CONFLICT;
        return createResponseMessage(conflict, exception, httpServletRequest);
    }

    @ExceptionHandler(value = PasswordNotConfirmedRightException.class)
    public ResponseEntity<ApiResponse> handlePasswordNotConfirmedRightException(final PasswordNotConfirmedRightException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus conflict = HttpStatus.CONFLICT;
        return createResponseMessage(conflict, exception, httpServletRequest);
    }

    @ExceptionHandler(value = PrepareEmailContentException.class)
    public ResponseEntity<ApiResponse> handlePrepareEmailContentException(final PrepareEmailContentException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badGateway = HttpStatus.BAD_GATEWAY;
        return createResponseMessage(badGateway, exception, httpServletRequest);
    }

    @ExceptionHandler(value = SendEmailException.class)
    public ResponseEntity<ApiResponse> handleSendEmailException(final SendEmailException exception, final HttpServletRequest httpServletRequest) {
        final HttpStatus badGateway = HttpStatus.BAD_GATEWAY;
        return createResponseMessage(badGateway, exception, httpServletRequest);
    }

    private ResponseEntity<ApiResponse> createResponseMessage(final HttpStatus httpStatus, final Exception exception, final HttpServletRequest httpServletRequest) {
        final ApiResponse apiResponse = ApiResponse.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(getMessage(exception))
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    private String getMessage(final Exception exception) {
        final String message;
        if (exception instanceof MethodArgumentNotValidException) {
            final List<String> messages = new ArrayList<>();
            for (final FieldError fieldError : ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()) {
                messages.add(fieldError.getDefaultMessage());
            }

            message = String.join(",", messages);
        } else if (exception instanceof NullPointerException) {
            message = "Something went wrong (NullPointerException). Please investigate the cause of the problem in more depth.";
        } else {
            message = exception.getMessage();
        }

        return message;
    }
}
