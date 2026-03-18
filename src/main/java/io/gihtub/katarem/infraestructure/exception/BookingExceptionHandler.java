package io.gihtub.katarem.infraestructure.exception;

import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    BookingErrorResponse handleNotFound(BookingNotFoundException ex) {
        return BookingErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
    }

}
