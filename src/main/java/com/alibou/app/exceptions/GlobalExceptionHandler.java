package com.alibou.app.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.alibou.app.exceptions.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException( final BusinessException ex){

        final ErrorResponse body = ErrorResponse.builder()
                .errorCode(ex.getErrorCode().getCode())
                .errorMessage(ex.getMessage())
                .build();

        log.info("Business Exception: {}", ex.getMessage());
        log.debug(ex.getMessage());

        return ResponseEntity.status(ex.getErrorCode()
                .getStatus() !=null ? ex.getErrorCode().getStatus() : BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(final DisabledException exception){

        final ErrorResponse body = ErrorResponse.builder()
                .errorCode(ERR_USER_DISABLED.getCode())
                .errorMessage(ERR_USER_DISABLED.getDefault_message())
                .build();

        return ResponseEntity.status(UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException( final BadCredentialsException exception){

        log.debug(exception.getMessage(), exception);

        final ErrorResponse body = ErrorResponse.builder()
                .errorCode(BAD_CREDENTIALS.getCode())
                .errorMessage(BAD_CREDENTIALS.getDefault_message())
                .build();

        return ResponseEntity.status(UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(final UsernameNotFoundException exception){

        log.debug(exception.getMessage(), exception);

        final ErrorResponse response = ErrorResponse.builder()
                .errorCode(USERNAME_NOT_FOUND.getCode())
                .errorMessage(USERNAME_NOT_FOUND.getDefault_message())
                .build();

        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException exception){

        log.debug(exception.getMessage(), exception);

        final ErrorResponse response = ErrorResponse.builder()
                .errorCode("TBD")
                .errorMessage(exception.getMessage())
                .build();

        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception){

        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorCode = error.getDefaultMessage();
                    errors.add(ErrorResponse.ValidationError.builder()
                                    .field(fieldName)
                                    .errorCode(errorCode)
                                    .errorMessage(errorCode)
                                    .build());
                });

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .validationErrors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exception){

        log.error(exception.getMessage(), exception);

        final ErrorResponse response = ErrorResponse.builder()
                .errorCode(INTERNAL_EXCEPTION.getCode())
                .errorMessage(INTERNAL_EXCEPTION.getDefault_message())
                .build();

        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }
}
