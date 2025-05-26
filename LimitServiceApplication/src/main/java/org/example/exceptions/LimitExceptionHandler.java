package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class LimitExceptionHandler {

    @ExceptionHandler(LimitException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleLimitException(LimitException ex) {
        log.error("Limit exception occurred: {}", ex.getMessage());
        return new ErrorResponse(
                LocalDateTime.now(),
                "Limit error",
                ex.getMessage()
        );
    }
}
