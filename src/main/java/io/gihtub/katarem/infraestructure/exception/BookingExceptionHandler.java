package io.gihtub.katarem.infraestructure.exception;

import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingErrorResponse;
import io.gihtub.katarem.infraestructure.exception.base.DomainConflictException;
import io.gihtub.katarem.infraestructure.exception.base.DomainNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BookingExceptionHandler {

    @ExceptionHandler(DomainConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    BookingErrorResponse handleConflict(DomainConflictException ex) {
        return BookingErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(DomainNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    BookingErrorResponse handleNotFound(DomainNotFoundException ex) {
        return BookingErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    BookingErrorResponse handleInternalError(RuntimeException ex) {
        log.error(ex.getMessage());
        return BookingErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("Check the logs or call an administrator.")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BookingErrorResponse handleValidationError(MethodArgumentNotValidException ex) {
        return BookingErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message(ex.getMessage())
                .build();
    }


}
